package com.sunrise.clinic.dao;

import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.model.Dentist;
import com.sunrise.clinic.model.Patient;
import com.sunrise.clinic.model.Treatment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    private static final String SELECT_FULL = """
            SELECT a.appointment_id, a.appointment_number, a.appointment_date, a.appointment_time,
                   a.status, a.created_by, a.booked_by,
                   p.patient_id, p.name AS patient_name, p.address, p.contact_number, p.email,
                   d.dentist_id, d.name AS dentist_name, d.specialization,
                   t.treatment_id, t.type_name, t.cost
              FROM appointments a
              JOIN patients p ON p.patient_id = a.patient_id
              JOIN dentists d ON d.dentist_id = a.dentist_id
              JOIN treatments t ON t.treatment_id = a.treatment_id
            """;

    public String nextAppointmentNumber() {
        String sql = "SELECT fn_next_appointment_no() AS next_no";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return rs.getString("next_no");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to generate appointment number.", e);
        }
        throw new IllegalStateException("Appointment number function returned no value.");
    }

    public boolean isSlotTaken(int dentistId, LocalDate date, LocalTime time) {
        String sql = """
                SELECT COUNT(*) AS taken
                  FROM appointments
                 WHERE dentist_id = ? AND appointment_date = ? AND appointment_time = ?
                   AND status <> 'CANCELLED'
                """;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, dentistId);
            statement.setDate(2, Date.valueOf(date));
            statement.setTime(3, Time.valueOf(time));
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() && rs.getInt("taken") > 0;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to check appointment slot.", e);
        }
    }

    public void insert(Appointment appointment) {
        String sql = """
                INSERT INTO appointments
                    (appointment_number, patient_id, dentist_id, treatment_id,
                     appointment_date, appointment_time, status, created_by, booked_by)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, appointment.getAppointmentNumber());
            statement.setInt(2, appointment.getPatient().getPatientId());
            statement.setInt(3, appointment.getDentist().getDentistId());
            statement.setInt(4, appointment.getTreatment().getTreatmentId());
            statement.setDate(5, Date.valueOf(appointment.getAppointmentDate()));
            statement.setTime(6, Time.valueOf(appointment.getAppointmentTime()));
            statement.setString(7, appointment.getStatus());
            if (appointment.getCreatedBy() <= 0) {
                statement.setObject(8, null);
            } else {
                statement.setInt(8, appointment.getCreatedBy());
            }
            statement.setString(9, appointment.getBookedBy() == null ? "STAFF" : appointment.getBookedBy());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public Appointment findByNumber(String appointmentNumber) {
        String sql = SELECT_FULL + " WHERE a.appointment_number = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, appointmentNumber);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to search appointment.", e);
        }
        return null;
    }

    public List<Appointment> findByDate(LocalDate date) {
        List<Appointment> list = new ArrayList<>();
        String sql = SELECT_FULL + " WHERE a.appointment_date = ? ORDER BY a.appointment_time";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(date));
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load daily appointments.", e);
        }
        return list;
    }

    public Appointment findById(int appointmentId) {
        String sql = SELECT_FULL + " WHERE a.appointment_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, appointmentId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load appointment.", e);
        }
        return null;
    }

    public List<Appointment> findByPatientId(int patientId) {
        List<Appointment> list = new ArrayList<>();
        String sql = SELECT_FULL + " WHERE a.patient_id = ? ORDER BY a.appointment_date DESC, a.appointment_time DESC";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load patient appointments.", e);
        }
        return list;
    }

    public void cancel(int appointmentId, int patientId) {
        String sql = """
                UPDATE appointments SET status = 'CANCELLED'
                 WHERE appointment_id = ? AND patient_id = ? AND status = 'BOOKED'
                """;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, appointmentId);
            statement.setInt(2, patientId);
            if (statement.executeUpdate() == 0) {
                throw new IllegalStateException("This appointment cannot be cancelled.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to cancel appointment.", e);
        }
    }

    public void markCompleted(int appointmentId) {
        String sql = "UPDATE appointments SET status = 'COMPLETED' WHERE appointment_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, appointmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to update appointment status.", e);
        }
    }

    private Appointment map(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setPatientId(rs.getInt("patient_id"));
        patient.setName(rs.getString("patient_name"));
        patient.setAddress(rs.getString("address"));
        patient.setContactNumber(rs.getString("contact_number"));
        try {
            patient.setEmail(rs.getString("email"));
        } catch (SQLException ignored) {
            patient.setEmail(null);
        }

        Dentist dentist = new Dentist();
        dentist.setDentistId(rs.getInt("dentist_id"));
        dentist.setName(rs.getString("dentist_name"));
        dentist.setSpecialization(rs.getString("specialization"));

        Treatment treatment = new Treatment();
        treatment.setTreatmentId(rs.getInt("treatment_id"));
        treatment.setTypeName(rs.getString("type_name"));
        treatment.setCost(rs.getBigDecimal("cost"));

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(rs.getInt("appointment_id"));
        appointment.setAppointmentNumber(rs.getString("appointment_number"));
        appointment.setPatient(patient);
        appointment.setDentist(dentist);
        appointment.setTreatment(treatment);
        appointment.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
        appointment.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
        appointment.setStatus(rs.getString("status"));
        appointment.setCreatedBy(rs.getInt("created_by"));
        try {
            appointment.setBookedBy(rs.getString("booked_by"));
        } catch (SQLException ignored) {
            appointment.setBookedBy("STAFF");
        }
        return appointment;
    }
}
