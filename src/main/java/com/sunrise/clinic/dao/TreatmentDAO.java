package com.sunrise.clinic.dao;

import com.sunrise.clinic.model.Treatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAO {

    public List<Treatment> findAll() {
        List<Treatment> treatments = new ArrayList<>();
        String sql = "SELECT treatment_id, type_name, cost FROM treatments ORDER BY type_name";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                treatments.add(map(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load treatments.", e);
        }
        return treatments;
    }

    public Treatment findById(int treatmentId) {
        String sql = "SELECT treatment_id, type_name, cost FROM treatments WHERE treatment_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, treatmentId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load treatment.", e);
        }
        return null;
    }

    public void insert(String typeName, java.math.BigDecimal cost) {
        String sql = "INSERT INTO treatments (type_name, cost) VALUES (?, ?)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, typeName);
            statement.setBigDecimal(2, cost);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to add treatment.", e);
        }
    }

    public void update(int treatmentId, String typeName, java.math.BigDecimal cost) {
        String sql = "UPDATE treatments SET type_name = ?, cost = ? WHERE treatment_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, typeName);
            statement.setBigDecimal(2, cost);
            statement.setInt(3, treatmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to update treatment.", e);
        }
    }

    public boolean deleteById(int treatmentId) {
        String sql = "DELETE FROM treatments WHERE treatment_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, treatmentId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to delete treatment.", e);
        }
    }

    public int countAppointments(int treatmentId) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE treatment_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, treatmentId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to check treatment appointments.", e);
        }
    }

    private Treatment map(ResultSet rs) throws SQLException {
        Treatment treatment = new Treatment();
        treatment.setTreatmentId(rs.getInt("treatment_id"));
        treatment.setTypeName(rs.getString("type_name"));
        treatment.setCost(rs.getBigDecimal("cost"));
        return treatment;
    }
}
