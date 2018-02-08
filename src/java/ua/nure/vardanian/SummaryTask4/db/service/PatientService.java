package ua.nure.vardanian.SummaryTask4.db.service;

import ua.nure.vardanian.SummaryTask4.db.TypeOfTreatment;
import ua.nure.vardanian.SummaryTask4.db.entity.HospitalCard;
import ua.nure.vardanian.SummaryTask4.db.entity.Patient;
import ua.nure.vardanian.SummaryTask4.db.entity.Treatment;

import java.util.List;

/**
 * interface which is responsible for business login for get parameters
 *
 * @author Akop Vardanian
 *
 **/
public interface PatientService {

    List<Patient> getPatients();

    List<TypeOfTreatment> getTypesOfTreatment();

    List<Patient> getPatientsByDoctorId(int doctorId);

    void addPatient(Patient patient);

    void setDoctorToPatient(int patientId, int doctorId);

    void updateDiagnosisInHospitalCard(int cardId, String diagnosis);

    int addHospitalCard();

    List<Treatment> getTreatmentsByCardId(int cardId);

    void addTreatment(Treatment treatment);

    void finishTreatment(long treatmentId);

    HospitalCard getHospitalCardById(int id);

    Patient getPatientByHospitalCardId(int hospitalCardId);

    List<Patient> getDischargedPatientsByDoctorId(int doctorId);

    void completeTheCourseOfTreatment(Patient patient);
}