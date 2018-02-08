package ua.nure.vardanian.SummaryTask4.db.utils;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.Fields;
import ua.nure.vardanian.SummaryTask4.db.entity.HospitalCard;
import ua.nure.vardanian.SummaryTask4.db.entity.Patient;
import ua.nure.vardanian.SummaryTask4.db.entity.Treatment;
import ua.nure.vardanian.SummaryTask4.db.entity.User;
import ua.nure.vardanian.SummaryTask4.db.exception.Messages;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class);

    /**
     * Extracts a user from the result set.
     *
     * @param rs Result set from which a user will be extracted.
     * @return User object
     */
    public static User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(Fields.ENTITY_ID));
        user.setLogin(rs.getString(Fields.USER_LOGIN));
        user.setPassword(rs.getString(Fields.USER_PASSWORD));
        user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
        user.setLastName(rs.getString(Fields.USER_LAST_NAME));
        user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
        user.setSpecializationId(rs.getInt(Fields.USER_SPECIALIZATION_ID));
        user.setCountOfPatients(rs.getInt(Fields.USER_COUNT_OF_PATIENTS));
        return user;
    }

    /**
     * Extracts a patient from the result set.
     *
     * @param rs Result set from which a user will be extracted.
     * @return Patient object
     */
    public static Patient extractPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getLong(Fields.ENTITY_ID));
        patient.setFirstName(rs.getString(Fields.PATIENT_FIRST_NAME));
        patient.setLastName(rs.getString(Fields.PATIENT_LAST_NAME));
        patient.setBirthday(rs.getDate(Fields.PATIENT_BIRTHDAY));
        patient.setDoctorId(rs.getInt(Fields.PATIENT_DOCTOR_ID));
        patient.setCardId(rs.getInt(Fields.PATIENT_CARD_ID));

        return patient;
    }

    /**
     * Extracts a treatment from the result set.
     *
     * @param rs Result set from which a user will be extracted.
     * @return Treatment object
     */
    public static Treatment extractTreatment(ResultSet rs) throws SQLException {
        Treatment treatment = new Treatment();
        treatment.setId(rs.getLong(Fields.ENTITY_ID));
        treatment.setTypeOfTreatmentId(rs.getInt(Fields.TYPE_OF_TREATMENT_ID));
        treatment.setHospitalCardId(rs.getInt(Fields.TREATMENT_HOSPITAL_CARD_ID));
        treatment.setPills(rs.getNString(Fields.TREATMENT_PILLS));
        treatment.setDone(rs.getBoolean(Fields.TREATMENT_DONE));

        return treatment;
    }

    /**
     * Extracts a hospital card from the result set.
     *
     * @param rs Result set from which a user will be extracted.
     * @return HospitalCard object
     */
    public static HospitalCard extractHospitalCard(ResultSet rs) throws SQLException {
        HospitalCard card = new HospitalCard();
        card.setId(rs.getLong(Fields.ENTITY_ID));
        card.setDiagnosis(rs.getString(Fields.HOSPITAL_CARD_DIAGNOSIS));

        return card;
    }
    // //////////////////////////////////////////////////////////
    // DB util methods
    // //////////////////////////////////////////////////////////

    /**
     * Closes a connection.
     *
     * @param con Connection to be closed.
     */
    public static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

    /**
     * Closes a statement object.
     */
    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }

    /**
     * Closes a result set object.
     */
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_RESULT_SET, ex);
            }
        }
    }

    /**
     * Closes resources.
     */
    public static void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    /**
     * Rollbacks a connection.
     *
     * @param con Connection to be rollbacked.
     */
    public static void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error("Cannot rollback transaction", ex);
            }
        }
    }
}