-- sunrise clinic mysql schema + sample data
CREATE DATABASE IF NOT EXISTS sunrise_clinic
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;
USE sunrise_clinic;

DROP TRIGGER IF EXISTS trg_prevent_double_booking;
DROP PROCEDURE IF EXISTS sp_create_bill;
DROP FUNCTION IF EXISTS fn_next_appointment_no;

DROP TABLE IF EXISTS bills;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS treatments;
DROP TABLE IF EXISTS dentists;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS staff_users;
DROP TABLE IF EXISTS clinic_settings;

CREATE TABLE staff_users (
    user_id         INT PRIMARY KEY AUTO_INCREMENT,
    username        VARCHAR(50)  NOT NULL UNIQUE,
    password_hash   VARCHAR(64)  NOT NULL,
    full_name       VARCHAR(100) NOT NULL,
    role            VARCHAR(20)  NOT NULL,
    active          TINYINT(1)   NOT NULL DEFAULT 1
);

CREATE TABLE patients (
    patient_id      INT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(100) NOT NULL,
    address         VARCHAR(255) NOT NULL,
    contact_number  VARCHAR(15)  NOT NULL
);

CREATE TABLE dentists (
    dentist_id      INT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(100) NOT NULL,
    specialization  VARCHAR(100) NOT NULL
);

CREATE TABLE treatments (
    treatment_id    INT PRIMARY KEY AUTO_INCREMENT,
    type_name       VARCHAR(100) NOT NULL,
    cost            DECIMAL(10,2) NOT NULL
);

CREATE TABLE appointments (
    appointment_id      INT PRIMARY KEY AUTO_INCREMENT,
    appointment_number  VARCHAR(20) NOT NULL UNIQUE,
    patient_id          INT NOT NULL,
    dentist_id          INT NOT NULL,
    treatment_id        INT NOT NULL,
    appointment_date    DATE NOT NULL,
    appointment_time    TIME NOT NULL,
    status              VARCHAR(20) NOT NULL DEFAULT 'BOOKED',
    created_by          INT NOT NULL,
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_apt_patient  FOREIGN KEY (patient_id)   REFERENCES patients(patient_id),
    CONSTRAINT fk_apt_dentist  FOREIGN KEY (dentist_id)   REFERENCES dentists(dentist_id),
    CONSTRAINT fk_apt_treat    FOREIGN KEY (treatment_id) REFERENCES treatments(treatment_id),
    CONSTRAINT fk_apt_staff    FOREIGN KEY (created_by)   REFERENCES staff_users(user_id)
);

CREATE TABLE bills (
    bill_id             INT PRIMARY KEY AUTO_INCREMENT,
    appointment_id      INT NOT NULL UNIQUE,
    consultation_fee    DECIMAL(10,2) NOT NULL,
    treatment_cost      DECIMAL(10,2) NOT NULL,
    total_amount        DECIMAL(10,2) NOT NULL,
    billed_at           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bill_apt FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id)
);

CREATE TABLE clinic_settings (
    setting_key   VARCHAR(50) PRIMARY KEY,
    setting_value VARCHAR(100) NOT NULL
);

INSERT INTO clinic_settings (setting_key, setting_value)
VALUES ('consultation_fee', '1500.00');

-- Passwords are SHA-256 of (password + salt "sunrise")
-- admin / Admin@123    reception / Staff@123
INSERT INTO staff_users (username, password_hash, full_name, role, active) VALUES
('admin',      'fd48a5da972ce09dda0bc9b523b59106b7ac4f30f48ecc074340e93b647e821b', 'Nadeesha Perera', 'ADMIN', 1),
('reception',  '88244bde09c4ac67d1c308aa47fca1e131830a87714b8c3b327ec87b2548e569', 'Ishara Jayasuriya', 'RECEPTION', 1);

INSERT INTO dentists (name, specialization) VALUES
('Dr. Nimal Perera',    'General Dentistry'),
('Dr. Anusha Fernando', 'Orthodontics'),
('Dr. Kasun Silva',     'Oral Surgery');

INSERT INTO treatments (type_name, cost) VALUES
('Consultation', 1500.00),
('Teeth Cleaning', 5000.00),
('Filling', 8000.00),
('Extraction', 6000.00),
('Root Canal', 15000.00),
('Teeth Whitening', 12000.00),
('Braces Check-up', 4000.00);

DELIMITER $$

CREATE FUNCTION fn_next_appointment_no()
RETURNS VARCHAR(20)
NOT DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE next_n INT;
    SELECT IFNULL(MAX(CAST(SUBSTRING(appointment_number, 10) AS UNSIGNED)), 0) + 1
      INTO next_n
      FROM appointments
     WHERE appointment_number LIKE CONCAT('APT-', YEAR(CURDATE()), '-%');
    RETURN CONCAT('APT-', YEAR(CURDATE()), '-', LPAD(next_n, 4, '0'));
END$$

CREATE TRIGGER trg_prevent_double_booking
BEFORE INSERT ON appointments
FOR EACH ROW
BEGIN
    IF EXISTS (
        SELECT 1
          FROM appointments
         WHERE dentist_id = NEW.dentist_id
           AND appointment_date = NEW.appointment_date
           AND appointment_time = NEW.appointment_time
           AND status <> 'CANCELLED'
    ) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'This dentist already has an appointment at the selected date and time.';
    END IF;
END$$

CREATE PROCEDURE sp_create_bill(IN p_appointment_no VARCHAR(20), IN p_consult DECIMAL(10,2))
BEGIN
    DECLARE v_apt_id INT;
    DECLARE v_treat_cost DECIMAL(10,2);
    DECLARE v_total DECIMAL(10,2);

    SELECT a.appointment_id, t.cost
      INTO v_apt_id, v_treat_cost
      FROM appointments a
      JOIN treatments t ON t.treatment_id = a.treatment_id
     WHERE a.appointment_number = p_appointment_no;

    IF v_apt_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Appointment not found.';
    END IF;

    IF EXISTS (SELECT 1 FROM bills WHERE appointment_id = v_apt_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'A bill already exists for this appointment.';
    END IF;

    SET v_total = v_treat_cost + p_consult;

    INSERT INTO bills (appointment_id, consultation_fee, treatment_cost, total_amount)
    VALUES (v_apt_id, p_consult, v_treat_cost, v_total);

    UPDATE appointments SET status = 'COMPLETED' WHERE appointment_id = v_apt_id;
END$$

DELIMITER ;

-- Helpful report views
CREATE OR REPLACE VIEW vw_daily_appointments AS
SELECT a.appointment_number,
       p.name AS patient_name,
       d.name AS dentist_name,
       t.type_name AS treatment_type,
       a.appointment_date,
       a.appointment_time,
       a.status
  FROM appointments a
  JOIN patients p   ON p.patient_id = a.patient_id
  JOIN dentists d   ON d.dentist_id = a.dentist_id
  JOIN treatments t ON t.treatment_id = a.treatment_id;

CREATE OR REPLACE VIEW vw_income_by_dentist AS
SELECT d.name AS dentist_name,
       COUNT(b.bill_id) AS bills_count,
       IFNULL(SUM(b.total_amount), 0) AS total_income
  FROM dentists d
  LEFT JOIN appointments a ON a.dentist_id = d.dentist_id
  LEFT JOIN bills b ON b.appointment_id = a.appointment_id
 GROUP BY d.dentist_id, d.name;
