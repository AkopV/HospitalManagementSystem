package ua.nure.vardanian.SummaryTask4.db.service.impl;

import org.apache.log4j.Logger;
import ua.nure.vardanian.SummaryTask4.db.TypeOfTreatment;
import ua.nure.vardanian.SummaryTask4.db.dao.PatientDAO;
import ua.nure.vardanian.SummaryTask4.db.entity.HospitalCard;
import ua.nure.vardanian.SummaryTask4.db.entity.Patient;
import ua.nure.vardanian.SummaryTask4.db.entity.Treatment;
import ua.nure.vardanian.SummaryTask4.db.service.PatientService;

import java.util.List;

public class PatientServiceImpl implements PatientService {

    private static final Logger LOG = Logger.getLogger(PatientServiceImpl.class);

    private PatientDAO patientDAO;

    public PatientDAO getPatientDAO() {
        return patientDAO;
    }

    public void setPatientDAO(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    @Override
    public List<Patient> getPatients() {
        return patientDAO.getPatients();
    }

    @Override
    public List<TypeOfTreatment> getTypesOfTreatment() {
        return patientDAO.getTypesOfTreatment();
    }

    @Override
    public List<Patient> getPatientsByDoctorId(int doctorId) {
        return patientDAO.getPatientsByDoctorId(doctorId);
    }

    @Override
    public void addPatient(Patient patient) {
        patientDAO.addPatient(patient);
    }

    @Override
    public void setDoctorToPatient(int patientId, int doctorId) {
        patientDAO.setDoctorToPatient(patientId, doctorId);
    }

    @Override
    public void updateDiagnosisInHospitalCard(int cardId, String diagnosis) {
        patientDAO.updateDiagnosisInHospitalCard(cardId, diagnosis);
    }

    @Override
    public int addHospitalCard() {
        return patientDAO.addHospitalCard();
    }

    @Override
    public List<Treatment> getTreatmentsByCardId(int cardId) {
        return patientDAO.getTreatmentsByCardId(cardId);
    }

    @Override
    public void addTreatment(Treatment treatment) {
        patientDAO.addTreatment(treatment);
    }

    @Override
    public void finishTreatment(long treatmentId) {
        patientDAO.finishTreatment(treatmentId);
    }

    @Override
    public HospitalCard getHospitalCardById(int id) {
        return patientDAO.getHospitalCardById(id);
    }

    @Override
    public Patient getPatientByHospitalCardId(int hospitalCardId) {
        return patientDAO.getPatientByHospitalCardId(hospitalCardId);
    }

    @Override
    public List<Patient> getDischargedPatientsByDoctorId(int doctorId) {
        return patientDAO.getDischargedPatientsByDoctorId(doctorId);
    }

    @Override
    public void completeTheCourseOfTreatment(Patient patient) {
        patientDAO.completeTheCourseOfTreatment(patient);
    }
}