DROP DATABASE IF EXISTS db_hospital;

CREATE DATABASE db_hospital
	DEFAULT CHARACTER SET 'utf8'
    DEFAULT COLLATE 'utf8_unicode_ci';
    
    USE db_hospital;
    
--
-- TABLE: HOSPITAL_CARDS
--
CREATE TABLE IF NOT EXISTS hospital_cards (
id INT NOT NULL AUTO_INCREMENT,
diagnosis VARCHAR(200),
PRIMARY KEY (id)
);

--
-- INSERT VALUES TO TABLE HOSPITAL_CARDS
--
INSERT INTO hospital_cards (id, diagnosis) VALUES
(1, 'Laryngitis'),
(2, 'Foreign accent syndrome'),
(3, 'Scleritis'),
(4, 'broke leg');

--
-- TABLE: ROLES
--
CREATE TABLE roles (
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(45) NOT NULL UNIQUE,
PRIMARY KEY (id)
);

--
-- INSERT VALUES TO TABLE ROLES
--
INSERT INTO roles (id, name) VALUES 
(1, 'admin'),
(2, 'doctor'),
(3, 'nurse');

--
-- TABLE: SPECIALIZATIONS
--
CREATE TABLE IF NOT EXISTS specializations (
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(45) NOT NULL,
PRIMARY KEY (id)
);

--
-- INSERT VALUES TO TABLE SPECIALIZATIONS
--
INSERT INTO specializations (id, name) VALUES 
(1, 'Pediatrician'),
(2, 'Traumatologist'),
(3, 'Surgeon');

--
-- TABLE: TYPES_OF_TREATMENT
--
CREATE TABLE IF NOT EXISTS types_of_treatment (
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(45) NOT NULL,
PRIMARY KEY(id)
);

--
-- INSERT VALUES TO TABLE TYPES_OF_TREATMENT
--
INSERT INTO types_of_treatment (id, name) VALUES 
(1,'Procedure'),
(2, 'Medicine'),
(3, 'Surgery');

--
-- TABLE: TYPES_OF_TREATMENTS
--
CREATE TABLE IF NOT EXISTS treatments (
id INT NOT NULL AUTO_INCREMENT,
hospital_card_id INT NOT NULL,
type_of_treatment_id INT NOT NULL,
pills VARCHAR(45) NOT NULL,
done INT DEFAULT NULL,
PRIMARY KEY(id),
FOREIGN KEY (hospital_card_id) REFERENCES  hospital_cards(id)
  ON UPDATE CASCADE
  ON DELETE CASCADE,
    FOREIGN KEY  (type_of_treatment_id) REFERENCES types_of_treatment(id)
      ON UPDATE CASCADE
      ON DELETE CASCADE
);

--
-- INSERT VALUES TO TABLE TREATMENTS
--
INSERT INTO treatments (id, hospital_card_id,  type_of_treatment_id, pills, done) VALUES 
(1, 1, 2, 'Vitamin D', 1),
(2, 2, 2, 'Pankreatin', 0),
(3, 3, 2, 'Methotrexate', 0);

--
-- TABLE: USERS
--
CREATE TABLE IF NOT EXISTS users (
id INT NOT NULL AUTO_INCREMENT,
login VARCHAR(10) NOT NULL,
password VARCHAR(45) DEFAULT NULL,
  first_name VARCHAR(20) DEFAULT NULL,
  last_name VARCHAR(20) DEFAULT NULL,
  role_id INT DEFAULT NULL,
  specialization_id INT DEFAULT NULL,
  count_of_patients INT DEFAULT NULL,
  PRIMARY KEY (id),
  foreign key (role_id) references roles(id)
  ON UPDATE CASCADE
  ON DELETE CASCADE,
  foreign key (specialization_id) references specializations(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

--
-- INSERT VALUES TO TABLE USERS
--
INSERT INTO users (id, login, password, first_name, last_name, role_id, specialization_id, count_of_patients) VALUES
(1, 'admin', 'admin', 'Robert', 'Robertino', NULL, NULL, 0),
(2, 'doctor', 'pediatrician', 'Haus', 'Lukenski', 1,  2, 2),
(3, 'doctor2', 'traumatologist', 'Adam', 'Washington', 1, 1, 1),
(4, 'doctor3', 'surgeon', 'Melani', 'Melano', 1, 3, 0),
(5, 'nurse', 'nurse', 'Katy', 'Ivanova', 2, NULL, 0);
 
--
-- TABLE: PATIENTS
--
CREATE TABLE IF NOT EXISTS patients (
    id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(45) NOT NULL,
    last_name VARCHAR(45) NOT NULL,
    birthday DATE DEFAULT NULL,
    doctor_id INT(11) DEFAULT NULL,
    card_id INT(11) DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY  (doctor_id) REFERENCES users(id)
  ON UPDATE CASCADE
  ON DELETE CASCADE,
    FOREIGN KEY  (card_id) REFERENCES hospital_cards(id)
      ON UPDATE CASCADE
      ON DELETE CASCADE
);

--
-- INSERT VALUES TO TABLE PATIENTS
--
INSERT INTO patients (id, first_name, last_name, birthday, doctor_id, card_id) VALUES
(1, 'Sidr', 'Sidorov', '2012-03-23', 1, 1),
(2, 'Victoria', 'Victorova', '2015-04-27', 2, 2),
(3, 'Adam', 'Smit', '2000-03-14', 2, 3),
(4, 'Mett', 'Lablank', '1973-02-04', 3, 3);

--
-- TABLE: DISCHARGED_PATIENTS
--
CREATE TABLE IF NOT EXISTS discharged_patients (
    id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(45) DEFAULT NULL,
    last_name VARCHAR(45) DEFAULT NULL,
    birthday DATE DEFAULT NULL,
    doctor_id INT DEFAULT NULL,
    PRIMARY KEY (id),
    foreign key (doctor_id) references users(id)
      ON UPDATE CASCADE
      ON DELETE CASCADE
);

--
-- INSERT VALUES TO TABLE DISCHARGED_PATIENTS
--
INSERT INTO discharged_patients (id, first_name, last_name, birthday, doctor_id) VALUES
(1, 'Sidr', 'Sidorov', '1988-03-23', 1),
(2, 'Maksim', 'Maksimov', '1978-08-29', 1),
(3, 'Viktoria', 'Viktorova', '1998-02-21', 1);

select * from patients where birthday < 1995;