package com.sunrise.clinic.dao;

import com.sunrise.clinic.model.Dentist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DentistDAO {

    public List<Dentist> findAll() {
        List<Dentist> dentists = new ArrayList<>();
        String sql = "SELECT dentist_id, name, specialization FROM dentists ORDER BY name";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Dentist dentist = new Dentist();
                dentist.setDentistId(rs.getInt("dentist_id"));
                dentist.setName(rs.getString("name"));
                dentist.setSpecialization(rs.getString("specialization"));
                dentists.add(dentist);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load dentists.", e);
        }
        return dentists;
    }

    public Dentist findById(int dentistId) {
        String sql = "SELECT dentist_id, name, specialization FROM dentists WHERE dentist_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, dentistId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Dentist dentist = new Dentist();
                    dentist.setDentistId(rs.getInt("dentist_id"));
                    dentist.setName(rs.getString("name"));
                    dentist.setSpecialization(rs.getString("specialization"));
                    return dentist;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load dentist.", e);
        }
        return null;
    }

    public void insert(String name, String specialization) {
        String sql = "INSERT INTO dentists (name, specialization) VALUES (?, ?)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, specialization);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to add dentist.", e);
        }
    }

    public void update(int dentistId, String name, String specialization) {
        String sql = "UPDATE dentists SET name = ?, specialization = ? WHERE dentist_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, specialization);
            statement.setInt(3, dentistId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to update dentist.", e);
        }
    }

    public boolean deleteById(int dentistId) {
        String sql = "DELETE FROM dentists WHERE dentist_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, dentistId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to delete dentist.", e);
        }
    }

    public int countAppointments(int dentistId) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE dentist_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, dentistId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to check dentist appointments.", e);
        }
    }
}
