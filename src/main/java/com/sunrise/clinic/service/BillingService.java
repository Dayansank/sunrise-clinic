package com.sunrise.clinic.service;

import com.sunrise.clinic.dao.AppointmentDAO;
import com.sunrise.clinic.dao.BillDAO;
import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.model.Bill;
import com.sunrise.clinic.pattern.BillingStrategy;
import com.sunrise.clinic.pattern.ConsultationFeeConfig;
import com.sunrise.clinic.pattern.StandardBillingStrategy;
import com.sunrise.clinic.util.ValidationUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class BillingService {
    private final AppointmentDAO appointmentDAO;
    private final BillDAO billDAO;
    private final BillingStrategy billingStrategy;

    public BillingService() {
        this(new AppointmentDAO(), new BillDAO(), new StandardBillingStrategy());
    }

    public BillingService(AppointmentDAO appointmentDAO, BillDAO billDAO, BillingStrategy billingStrategy) {
        this.appointmentDAO = appointmentDAO;
        this.billDAO = billDAO;
        this.billingStrategy = billingStrategy;
    }

    public BigDecimal calculateTotal(BigDecimal treatmentCost) {
        return billingStrategy.calculate(treatmentCost, ConsultationFeeConfig.getInstance().getFee());
    }

    public Bill createBill(String appointmentNumber) {
        if (ValidationUtil.isBlank(appointmentNumber)) {
            throw new ClinicException("Appointment number is required.");
        }
        Appointment appointment = appointmentDAO.findByNumber(appointmentNumber.trim().toUpperCase());
        if (appointment == null) {
            throw new ClinicException("No appointment found for number " + appointmentNumber.trim() + ".");
        }
        if (!com.sunrise.clinic.pattern.AppointmentState.from(appointment.getStatus()).canBill()
                && !billDAO.existsForAppointment(appointment.getAppointmentId())) {
            throw new ClinicException("A bill cannot be created for a " + appointment.getStatus() + " appointment.");
        }
        if (billDAO.existsForAppointment(appointment.getAppointmentId())) {
            Bill existing = billDAO.findByAppointmentNumber(appointment.getAppointmentNumber());
            existing.setAppointment(appointment);
            return existing;
        }

        BigDecimal consultation = ConsultationFeeConfig.getInstance().getFee();
        BigDecimal treatmentCost = appointment.getTreatment().getCost();
        BigDecimal total = billingStrategy.calculate(treatmentCost, consultation);

        Bill bill = new Bill();
        bill.setAppointment(appointment);
        bill.setConsultationFee(consultation);
        bill.setTreatmentCost(treatmentCost);
        bill.setTotalAmount(total);
        bill.setBilledAt(LocalDateTime.now());
        billDAO.insert(bill);
        appointmentDAO.markCompleted(appointment.getAppointmentId());
        return bill;
    }

    public Bill findBill(String appointmentNumber) {
        Appointment appointment = appointmentDAO.findByNumber(appointmentNumber.trim().toUpperCase());
        if (appointment == null) {
            throw new ClinicException("No appointment found for number " + appointmentNumber.trim() + ".");
        }
        Bill bill = billDAO.findByAppointmentNumber(appointment.getAppointmentNumber());
        if (bill == null) {
            throw new ClinicException("No bill has been created for this appointment yet.");
        }
        bill.setAppointment(appointment);
        return bill;
    }

    public List<Map<String, Object>> incomeByDentist() {
        return billDAO.incomeByDentist();
    }

    public java.util.List<Bill> findByPatient(int patientId) {
        return billDAO.findByPatientId(patientId);
    }
}
