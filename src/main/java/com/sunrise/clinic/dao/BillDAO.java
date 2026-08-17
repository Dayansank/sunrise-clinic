package com.sunrise.clinic.dao;

import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.model.Bill;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BillDAO {

    public boolean existsForAppointment(int appointmentId) {
        String sql = "SELECT COUNT(*) FROM bills WHERE appointment_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, appointmentId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to check existing bill.", e);
        }
    }

    public void insert(Bill bill) {
        String sql = """
                INSERT INTO bills (appointment_id, consultation_fee, treatment_cost, total_amount)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bill.getAppointment().getAppointmentId());
            statement.setBigDecimal(2, bill.getConsultationFee());
            statement.setBigDecimal(3, bill.getTreatmentCost());
            statement.setBigDecimal(4, bill.getTotalAmount());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to save bill.", e);
        }
    }

    public void createViaProcedure(String appointmentNumber, BigDecimal consultationFee) {
        String sql = "{CALL sp_create_bill(?, ?)}";
        try (Connection connection = DBConnection.getInstance().getConnection();
             CallableStatement statement = connection.prepareCall(sql)) {
            statement.setString(1, appointmentNumber);
            statement.setBigDecimal(2, consultationFee);
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public Bill findByAppointmentNumber(String appointmentNumber) {
        String sql = """
                SELECT b.bill_id, b.consultation_fee, b.treatment_cost, b.total_amount, b.billed_at
                  FROM bills b
                  JOIN appointments a ON a.appointment_id = b.appointment_id
                 WHERE a.appointment_number = ?
                """;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, appointmentNumber);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Bill bill = new Bill();
                    bill.setBillId(rs.getInt("bill_id"));
                    bill.setConsultationFee(rs.getBigDecimal("consultation_fee"));
                    bill.setTreatmentCost(rs.getBigDecimal("treatment_cost"));
                    bill.setTotalAmount(rs.getBigDecimal("total_amount"));
                    bill.setBilledAt(rs.getTimestamp("billed_at").toLocalDateTime());
                    return bill;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load bill.", e);
        }
        return null;
    }

    public List<Bill> findByPatientId(int patientId) {
        List<Bill> bills = new ArrayList<>();
        String sql = """
                SELECT b.bill_id, b.consultation_fee, b.treatment_cost, b.total_amount, b.billed_at,
                       a.appointment_number
                  FROM bills b
                  JOIN appointments a ON a.appointment_id = b.appointment_id
                 WHERE a.patient_id = ?
                 ORDER BY b.billed_at DESC
                """;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Bill bill = new Bill();
                    bill.setBillId(rs.getInt("bill_id"));
                    bill.setConsultationFee(rs.getBigDecimal("consultation_fee"));
                    bill.setTreatmentCost(rs.getBigDecimal("treatment_cost"));
                    bill.setTotalAmount(rs.getBigDecimal("total_amount"));
                    bill.setBilledAt(rs.getTimestamp("billed_at").toLocalDateTime());
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentNumber(rs.getString("appointment_number"));
                    bill.setAppointment(appointment);
                    bills.add(bill);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load patient bills.", e);
        }
        return bills;
    }

    public List<Map<String, Object>> incomeByDentist() {
        List<Map<String, Object>> rows = new ArrayList<>();
        String sql = "SELECT dentist_name, bills_count, total_income FROM vw_income_by_dentist ORDER BY total_income DESC";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("dentistName", rs.getString("dentist_name"));
                row.put("billsCount", rs.getInt("bills_count"));
                row.put("totalIncome", rs.getBigDecimal("total_income"));
                rows.add(row);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load income report.", e);
        }
        return rows;
    }
}
