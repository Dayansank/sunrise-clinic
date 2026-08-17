package com.sunrise.clinic.dao;

import com.sunrise.clinic.model.Patient;
import com.sunrise.clinic.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PatientDAO {

    public int insert(Patient patient) {
        String sql = "INSERT INTO patients (name, address, contact_number, email, password_hash) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, patient.getName());
            statement.setString(2, patient.getAddress());
            statement.setString(3, patient.getContactNumber());
            statement.setString(4, patient.getEmail());
            statement.setString(5, null);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    patient.setPatientId(id);
                    return id;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to save patient.", e);
        }
        throw new IllegalStateException("Patient insert did not return an id.");
    }

    public int registerPortalAccount(Patient patient, String rawPassword) {
        if (findByEmail(patient.getEmail()) != null) {
            throw new IllegalStateException("An account already exists for this email.");
        }
        String sql = "INSERT INTO patients (name, address, contact_number, email, password_hash) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, patient.getName());
            statement.setString(2, patient.getAddress());
            statement.setString(3, patient.getContactNumber());
            statement.setString(4, patient.getEmail().trim().toLowerCase());
            statement.setString(5, PasswordUtil.hash(rawPassword));
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    patient.setPatientId(id);
                    return id;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to create patient account.", e);
        }
        throw new IllegalStateException("Patient register did not return an id.");
    }

    public Patient findByEmail(String email) {
        String sql = "SELECT patient_id, name, address, contact_number, email, password_hash FROM patients WHERE email = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email == null ? "" : email.trim().toLowerCase());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return map(rs, true);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load patient.", e);
        }
        return null;
    }

    public String findPasswordHash(String email) {
        String sql = "SELECT password_hash FROM patients WHERE email = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email.trim().toLowerCase());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password_hash");
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to verify patient login.", e);
        }
        return null;
    }

    public Patient findById(int patientId) {
        String sql = "SELECT patient_id, name, address, contact_number, email FROM patients WHERE patient_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return map(rs, false);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load patient.", e);
        }
        return null;
    }

    private Patient map(ResultSet rs, boolean includeEmail) throws SQLException {
        Patient patient = new Patient();
        patient.setPatientId(rs.getInt("patient_id"));
        patient.setName(rs.getString("name"));
        patient.setAddress(rs.getString("address"));
        patient.setContactNumber(rs.getString("contact_number"));
        try {
            patient.setEmail(rs.getString("email"));
        } catch (SQLException ignored) {
            patient.setEmail(null);
        }
        return patient;
    }
}
