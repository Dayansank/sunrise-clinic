package com.sunrise.clinic.dao;

import com.sunrise.clinic.model.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NotificationDAO {
    public void insert(Notification notification) {
        String sql = """
                INSERT INTO notifications (appointment_number, channel, recipient, message)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, notification.getAppointmentNumber());
            statement.setString(2, notification.getChannel());
            statement.setString(3, notification.getRecipient());
            statement.setString(4, notification.getMessage());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to save notification.", e);
        }
    }
}
