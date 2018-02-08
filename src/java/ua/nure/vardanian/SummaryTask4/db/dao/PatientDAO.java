package ua.nure.vardanian.SummaryTask4.db.dao;

import ua.nure.vardanian.SummaryTask4.db.Role;
import ua.nure.vardanian.SummaryTask4.db.Specialization;
import ua.nure.vardanian.SummaryTask4.db.TypeOfTreatment;
import ua.nure.vardanian.SummaryTask4.db.entity.HospitalCard;
import ua.nure.vardanian.SummaryTask4.db.entity.Patient;
import ua.nure.vardanian.SummaryTask4.db.entity.Treatment;

import java.util.List;

public interface PatientDAO {

    /**
     * Get all patients.
     *
     * @return list of patients.
     */
    List<Patient> getPatients();

    /**
     * Get all types of treatment.
     *
     * @return list of types of treatment.
     */
    List<TypeOfTreatment> getTypesOfTreatment();

    /**
     * Get patients by doctor id.
     *
     * @param doctorId specified doctor id.
     * @return list of patients
     */
    List<Patient> getPatientsByDoctorId(int doctorId);

    /**
     * Add patient.
     *
     * @param patient specified patient.
     */
    void addPatient(Patient patient);

    /**
     * Set doctor to patient.
     *
     * @param patientId specified patient id.
     * @param doctorId  specified doctor id.
     */
    void setDoctorToPatient(int patientId, int doctorId);

    /**
     * Update patient diagnosis.
     *
     * @param cardId    specified hospital card id.
     * @param diagnosis new diagnosis.
     */
    void updateDiagnosisInHospitalCard(int cardId, String diagnosis);

    /**
     * Create hospital card for new patient.
     *
     * @return hospital card id.
     */
    int addHospitalCard();

    /**
     * Get treatments by hospital card id.
     *
     * @param cardId specified hospital card id.
     * @return list of treatments.
     */
    List<Treatment> getTreatmentsByCardId(int cardId);

    /**
     * Add new treatment.
     *
     * @param treatment specified treatment.
     */
    void addTreatment(Treatment treatment);

    /**
     * Finish treatment.
     *
     * @param treatmentId specified treatment id.
     */
    void finishTreatment(long treatmentId);

    /**
     * Get hospital card by id.
     *
     * @param id specified id.
     * @return hospital card entity.
     */
    HospitalCard getHospitalCardById(int id);

    /**
     * Get patient by hospital card id.
     *
     * @param hospitalCardId specified hospital card id.
     * @return patient entity.
     */
    Patient getPatientByHospitalCardId(int hospitalCardId);

    /**
     * Get discharged patients by doctor id.
     *
     * @param doctorId specified doctor id.
     * @return list of patients.
     */
    List<Patient> getDischargedPatientsByDoctorId(int doctorId);

    /**
     * Complete course of treatment for specified patient.
     *
     * @param patient specified patient.
     */
    void completeTheCourseOfTreatment(Patient patient);
}