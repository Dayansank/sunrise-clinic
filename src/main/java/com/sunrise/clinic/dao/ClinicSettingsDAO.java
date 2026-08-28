package com.sunrise.clinic.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClinicSettingsDAO {
    private static final String FEE_KEY = "consultation_fee";

    public BigDecimal findConsultationFee() {
        String sql = "SELECT setting_value FROM clinic_settings WHERE setting_key = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, FEE_KEY);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new BigDecimal(rs.getString("setting_value"));
                }
            }
        } catch (Exception ignored) {
            // use 1500 if settings table missing
        }
        return new BigDecimal("1500.00");
    }

    public void saveConsultationFee(BigDecimal fee) {
        String sql = """
                INSERT INTO clinic_settings (setting_key, setting_value) VALUES (?, ?)
                ON DUPLICATE KEY UPDATE setting_value = VALUES(setting_value)
                """;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, FEE_KEY);
            statement.setString(2, fee.toPlainString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to save consultation fee.", e);
        }
    }
}
