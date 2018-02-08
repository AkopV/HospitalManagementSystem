package ua.nure.vardanian.SummaryTask4.db;

public interface Query {

    // //////////////////////////////////////////////////////////
    // SQL queries
    // //////////////////////////////////////////////////////////

    // user
    String SQL_GET_ALL_DOCTORS = "SELECT * FROM users WHERE role_id = 1";
    String SQL_GET_DOCTORS_BY_SPEC = "SELECT * FROM users WHERE specialization_id = (?)";
    String INSERT_USER = "INSERT INTO users (login, password, first_name, last_name, role_id, specialization_id, count_of_patients) VALUES (?, ?, ?, ?,  ?, ?, ?)";
    String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = (?)";
    String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id = (?)";
    String SQL_GET_ALL_ROLES = "SELECT * FROM roles";
    String SQL_GET_ALL_SPECIALIZATIONS = "SELECT * FROM specializations";
    String SQL_INCREMENT_COUNT_OF_PATIENTS = "UPDATE users SET count_of_patients=count_of_patients+1 WHERE id=?";
    String DECREMENT_COUNT_OF_PATIENTS = "UPDATE users SET count_of_patients=count_of_patients-1 WHERE id=?";

    // patient
    String SQL_GET_ALL_PATIENTS = "SELECT * FROM patients;";
    String SQL_GET_PATIENTS_BY_DOCTOR_ID = "SELECT * FROM patients WHERE doctor_id = ?";
    String INSERT_PATIENT = "INSERT INTO patients (first_name, last_name, birthday, card_id) VALUES (?,?,?,?)";
    String SQL_SET_DOCTOR_TO_PATIENT = "UPDATE patients SET doctor_id = ? WHERE id = ?";
    String SQL_UPDATE_DIAGNOSIS = "UPDATE hospital_cards SET diagnosis = ? WHERE id = ?";
    String CREATE_HOSPITAL_CARD = "INSERT INTO hospital_cards (diagnosis) VALUE (NULL)";
    String INSERT_TREATMENT = "INSERT INTO treatments (hospital_card_id, type_of_treatment_id, pills) VALUES (?, ?, ?)";
    String UPDATE_FINISH_TREATMENT = "UPDATE treatments SET done = 1 WHERE id = ?";
    String SQL_GET_HOSPITAL_CARD_BY_ID = "SELECT * FROM hospital_cards WHERE id = ?";
    String SQL_GET_TREATMENTS_BY_HOSPITAL_CARD = "SELECT * FROM treatments WHERE hospital_card_id = (?)";
    String SQL_GET_ALL_TYPES_OF_TREATMENT = "SELECT * FROM types_of_treatment;";
    String SQL_GET_PATIENT_BY_HOSPITAL_CARD_ID = "SELECT * FROM patients WHERE card_id = ?";
    String SQL_GET_DISCHARGED_PATIENTS_BY_DOCTOR_ID = "SELECT * FROM discharged_patients WHERE doctor_id = ?";
    String DELETE_PATIENT_BY_ID = "DELETE FROM patients WHERE id = ?";
    String DELETE_HOSPITAL_CARD_BY_ID = "DELETE FROM hospital_cards WHERE id = ?";
    String INSERT_DISCHARGED_PATIENT = "INSERT INTO discharged_patients (first_name, last_name, birthday, doctor_id) VALUES (?, ?, ?, ?)";
    String DELETE_TREATMENTS_BY_HOSPITAL_CARD_ID = "DELETE FROM treatments WHERE hospital_card_id = ?";
}