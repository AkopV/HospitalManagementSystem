package ua.nure.vardanian.SummaryTask4.db.dao.impl;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.ConnectionPool;
import ua.nure.vardanian.SummaryTask4.db.Fields;
import ua.nure.vardanian.SummaryTask4.db.Query;
import ua.nure.vardanian.SummaryTask4.db.TypeOfTreatment;
import ua.nure.vardanian.SummaryTask4.db.dao.PatientDAO;
import ua.nure.vardanian.SummaryTask4.db.entity.HospitalCard;
import ua.nure.vardanian.SummaryTask4.db.entity.Patient;
import ua.nure.vardanian.SummaryTask4.db.entity.Treatment;
import ua.nure.vardanian.SummaryTask4.db.exception.DBException;
import ua.nure.vardanian.SummaryTask4.db.exception.Messages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ua.nure.vardanian.SummaryTask4.db.utils.Utils.*;

public class PatientDAOImpl implements PatientDAO {

    private static final Logger LOG = Logger.getLogger(PatientDAOImpl.class);

    @Override
    public List<Patient> getPatients() {
        List<Patient> patients = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(Query.SQL_GET_ALL_PATIENTS);
            while (rs.next()) {
                patients.add(extractPatient(rs));
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_PATIENTS, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_PATIENTS, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (DBException e) {
            e.printStackTrace();
        } finally {
            close(con, stmt, rs);
        }
        return patients;
    }

    @Override
    public List<TypeOfTreatment> getTypesOfTreatment() {
        List<TypeOfTreatment> typeOfTreatments = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(Query.SQL_GET_ALL_TYPES_OF_TREATMENT);
            while (rs.next()) {
                for (TypeOfTreatment type : TypeOfTreatment.values()) {
                    if (rs.getString(Fields.TYPE_OF_TREATMENT_NAME).toUpperCase().equals(type.toString())) {
                        type.setId((long) rs.getInt(Fields.ENTITY_ID));
                        typeOfTreatments.add(type);
                        break;
                    }
                }
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_TYPE_OF_TREATMENT, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_TYPE_OF_TREATMENT, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (DBException e) {
            e.printStackTrace();
        } finally {
            close(con, stmt, rs);
        }
        return typeOfTreatments;
    }

    @Override
    public List<Patient> getPatientsByDoctorId(int doctorId) {
        List<Patient> patients = new ArrayList<>();

        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
        } catch (DBException e) {
            e.printStackTrace();
        }
        try (PreparedStatement pstmt = con.prepareStatement(Query.SQL_GET_PATIENTS_BY_DOCTOR_ID)) {
            pstmt.setInt(1, doctorId);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            while (rs.next()) {
                patients.add(extractPatient(rs));
            }

        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_PATIENTS_BY_DOCTOR_ID, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_PATIENTS_BY_DOCTOR_ID, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } finally {
            close(con);
        }
        return patients;
    }

    @Override
    public void addPatient(Patient patient) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionPool.getConnection();
            pstmt = con.prepareStatement(Query.INSERT_PATIENT);
            int k = 1;
            pstmt.setString(k++, patient.getFirstName());
            pstmt.setString(k++, patient.getLastName());
            pstmt.setDate(k++, (Date) patient.getBirthday());
            pstmt.setInt(k++, patient.getCardId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_CREATE_PATIENT, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_CREATE_PATIENT, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (DBException e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
    }

    @Override
    public void setDoctorToPatient(int patientId, int doctorId) {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement psrmt = con.prepareStatement(Query.SQL_SET_DOCTOR_TO_PATIENT);
             PreparedStatement pstmt2 = con.prepareStatement(Query.SQL_INCREMENT_COUNT_OF_PATIENTS)) {
            con.setAutoCommit(false);
            int k = 1;
            psrmt.setInt(k++, doctorId);
            psrmt.setInt(k++, patientId);
            psrmt.executeUpdate();

            int i = 1;
            pstmt2.setInt(i++, doctorId);
            pstmt2.executeUpdate();

            con.commit();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_SET_DOCTOR_TO_PATIENT, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_SET_DOCTOR_TO_PATIENT, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDiagnosisInHospitalCard(int cardId, String diagnosis) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = ConnectionPool.getConnection();
            pstm = con.prepareStatement(Query.SQL_UPDATE_DIAGNOSIS);
            int k = 1;
            pstm.setString(k++, diagnosis);
            pstm.setInt(k++, cardId);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_UPDATE_DIAGNOSIS, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_UPDATE_DIAGNOSIS, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (DBException e) {
            e.printStackTrace();
        } finally {
            close(pstm);
            close(con);
        }
    }

    @Override
    public int addHospitalCard() {
        int cardId = 0;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionPool.getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(Query.CREATE_HOSPITAL_CARD, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                cardId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_CREATE_HOSPITAL_CARD, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_CREATE_HOSPITAL_CARD, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (DBException e) {
            e.printStackTrace();
        } finally {
            close(con, stmt, rs);
        }
        return cardId;
    }

    @Override
    public List<Treatment> getTreatmentsByCardId(int cardId) {
        List<Treatment> treatments = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getConnection();
            pstm = con.prepareStatement(Query.SQL_GET_TREATMENTS_BY_HOSPITAL_CARD);
            pstm.setInt(1, cardId);
            rs = pstm.executeQuery();
            while (rs.next()) {
                treatments.add(extractTreatment(rs));
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_FIND_TREATMENTS_BY_HOSPITAL_CARD_ID, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_FIND_TREATMENTS_BY_HOSPITAL_CARD_ID, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (DBException e) {
            e.printStackTrace();
        } finally {
            close(con, pstm, rs);
        }
        return treatments;
    }

    @Override
    public void addTreatment(Treatment treatment) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionPool.getConnection();
            pstmt = con.prepareStatement(Query.INSERT_TREATMENT);
            int k = 1;
            pstmt.setInt(k++, treatment.getHospitalCardId());
            pstmt.setInt(k++, treatment.getTypeOfTreatmentId());
            pstmt.setString(k++, treatment.getPills());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_CREATE_TREATMENT, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_CREATE_TREATMENT, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (DBException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }
    }

    @Override
    public void finishTreatment(long treatmentId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionPool.getConnection();
            pstmt = con.prepareStatement(Query.UPDATE_FINISH_TREATMENT);
            pstmt.setLong(1, treatmentId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_FINISH_TREATMENT, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_FINISH_TREATMENT, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (DBException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }
    }

    @Override
    public HospitalCard getHospitalCardById(int id) {
        HospitalCard card = new HospitalCard();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getConnection();
            pstmt = con.prepareStatement(Query.SQL_GET_HOSPITAL_CARD_BY_ID);
            pstmt.setInt(1, id);
            pstmt.execute();

            rs = pstmt.getResultSet();
            if (rs.next()) {
                card = extractHospitalCard(rs);
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_FIND_HOSPITAL_CARD_BY_ID, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_FIND_HOSPITAL_CARD_BY_ID, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (DBException e) {
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return card;
    }

    @Override
    public Patient getPatientByHospitalCardId(int hospitalCardId) {
        Patient patient = new Patient();
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getConnection();
            prstmt = con.prepareStatement(Query.SQL_GET_PATIENT_BY_HOSPITAL_CARD_ID);
            prstmt.setInt(1, hospitalCardId);
            prstmt.execute();

            rs = prstmt.getResultSet();
            if (rs.next()) {
                patient = extractPatient(rs);
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_FIND_PATIENT_BY_HOSPITAL_CARD_ID, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_FIND_PATIENT_BY_HOSPITAL_CARD_ID, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (DBException e) {
            e.printStackTrace();
        } finally {
            close(con, prstmt, rs);
        }
        return patient;
    }

    @Override
    public List<Patient> getDischargedPatientsByDoctorId(int doctorId) {
        List<Patient> patients = new ArrayList<>();
        Connection con = null;
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getConnection();
            prstmt = con.prepareStatement(Query.SQL_GET_DISCHARGED_PATIENTS_BY_DOCTOR_ID);
            prstmt.setInt(1, doctorId);
            prstmt.execute();

            rs = prstmt.getResultSet();
            while (rs.next()) {
                patients.add(new Patient(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getDate("birthday"), rs.getInt("doctor_id")));

            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_FIND_PATIENTS_BY_DOCTOR_ID, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_FIND_PATIENTS_BY_DOCTOR_ID, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (DBException e) {
            e.printStackTrace();
        } finally {
            close(con, prstmt, rs);
        }
        return patients;
    }

    @Override
    public void completeTheCourseOfTreatment(Patient patient) {
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
        } catch (DBException e) {
            e.printStackTrace();
        }
        try (PreparedStatement psPatient = con.prepareStatement(Query.DELETE_PATIENT_BY_ID);
             PreparedStatement psHospitalCard = con.prepareStatement(Query.DELETE_HOSPITAL_CARD_BY_ID);
             PreparedStatement psDoctorCount = con.prepareStatement(Query.DECREMENT_COUNT_OF_PATIENTS);
             PreparedStatement psDischargedPatient = con.prepareStatement(Query.INSERT_DISCHARGED_PATIENT);
             PreparedStatement psTreatments = con.prepareStatement(Query.DELETE_TREATMENTS_BY_HOSPITAL_CARD_ID);) {

            con.setAutoCommit(false);

            LOG.trace("In DB patient: " +
                    Fields.ENTITY_ID + ": " + patient.getId() + ", " +
                    Fields.PATIENT_FIRST_NAME + ": " + patient.getFirstName() + ", " +
                    Fields.PATIENT_LAST_NAME + ": " + patient.getLastName() + ", " +
                    Fields.PATIENT_BIRTHDAY + ": " + patient.getBirthday() + ", " +
                    Fields.PATIENT_CARD_ID + ": " + patient.getCardId() + ", " +
                    Fields.PATIENT_DOCTOR_ID + ": " + patient.getDoctorId());

            // set patient id for delete
            psPatient.setLong(1, patient.getId());
            psPatient.executeUpdate();

            // set hospital card id for delete treatments
            psTreatments.setInt(1, patient.getCardId());
            psTreatments.executeUpdate();

            // set hospital card id for delete
            psHospitalCard.setInt(1, patient.getCardId());
            psHospitalCard.executeUpdate();

            // set doctor id for decrement count of patients
            psDoctorCount.setInt(1, patient.getDoctorId());
            psDoctorCount.executeUpdate();

            // set data for insert new discharged patient
            int k = 1;
            psDischargedPatient.setString(k++, patient.getFirstName());
            psDischargedPatient.setString(k++, patient.getLastName());
            psDischargedPatient.setDate(k++, (Date) patient.getBirthday());
            psDischargedPatient.setInt(k++, patient.getDoctorId());
            psDischargedPatient.executeUpdate();

            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_COMPLETE_COURSE_OF_TREATMENT, ex);
            try {
                throw new DBException(Messages.ERR_CANNOT_COMPLETE_COURSE_OF_TREATMENT, ex);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } finally {
            close(con);
        }
    }
}