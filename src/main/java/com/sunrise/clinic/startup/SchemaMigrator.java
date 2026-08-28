package com.sunrise.clinic.startup;

import com.sunrise.clinic.dao.DBConnection;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public final class SchemaMigrator {
    private SchemaMigrator() {
    }

    public static void migrate() {
        List<String> statements = List.of(
                "ALTER TABLE patients ADD COLUMN email VARCHAR(120) NULL",
                "ALTER TABLE patients ADD COLUMN password_hash VARCHAR(64) NULL",
                "ALTER TABLE appointments ADD COLUMN booked_by VARCHAR(20) NOT NULL DEFAULT 'STAFF'",
                "ALTER TABLE appointments MODIFY created_by INT NULL",
                """
                CREATE TABLE IF NOT EXISTS notifications (
                    notification_id INT PRIMARY KEY AUTO_INCREMENT,
                    appointment_number VARCHAR(20) NOT NULL,
                    channel VARCHAR(20) NOT NULL,
                    recipient VARCHAR(120) NOT NULL,
                    message VARCHAR(500) NOT NULL,
                    sent_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                )
                """,
                """
                INSERT INTO patients (name, address, contact_number, email, password_hash)
                SELECT 'Kamal Perera', '12 Galle Road, Colombo 03', '0771234567',
                       'kamal@sunrise.lk', '93d7c0a22e8ed3ac5685217e6b2d62d3295dbed0a565e393d556cb6428ff3e4c'
                  FROM DUAL
                 WHERE NOT EXISTS (SELECT 1 FROM patients WHERE email = 'kamal@sunrise.lk')
                """
        );
        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            for (String sql : statements) {
                try {
                    statement.execute(sql);
                } catch (Exception ignored) {
                    // already there
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Could not upgrade the clinic database.", e);
        }
    }
}
