package com.sunrise.clinic.service;

import com.sunrise.clinic.dao.PatientDAO;
import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.Patient;
import com.sunrise.clinic.util.PasswordUtil;
import com.sunrise.clinic.util.ValidationUtil;

public class PatientAuthService {
    private final PatientDAO patientDAO;

    public PatientAuthService() {
        this(new PatientDAO());
    }

    public PatientAuthService(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public Patient register(String name, String address, String phone, String email, String password) {
        if (!ValidationUtil.isValidName(name)) {
            throw new ClinicException("Full name must be between 3 and 100 characters.");
        }
        if (ValidationUtil.isBlank(address) || address.trim().length() < 5) {
            throw new ClinicException("Address must be at least 5 characters.");
        }
        if (!ValidationUtil.isValidPhone(phone)) {
            throw new ClinicException("Enter a valid contact number, for example 0771234567.");
        }
        if (!ValidationUtil.isValidEmail(email)) {
            throw new ClinicException("Enter a valid email address.");
        }
        if (!ValidationUtil.isValidPassword(password)) {
            throw new ClinicException("Password must be at least 6 characters.");
        }
        if (patientDAO.findByEmail(email) != null) {
            throw new ClinicException("An account already exists for this email.");
        }
        Patient patient = new Patient();
        patient.setName(name.trim());
        patient.setAddress(address.trim());
        patient.setContactNumber(phone.trim());
        patient.setEmail(email.trim().toLowerCase());
        patientDAO.registerPortalAccount(patient, password);
        return patient;
    }

    public Patient login(String email, String password) {
        if (ValidationUtil.isBlank(email) || ValidationUtil.isBlank(password)) {
            throw new ClinicException("Email and password are required.");
        }
        String hash = patientDAO.findPasswordHash(email);
        if (hash == null || !PasswordUtil.matches(password, hash)) {
            throw new ClinicException("Invalid email or password.");
        }
        return patientDAO.findByEmail(email);
    }
}
