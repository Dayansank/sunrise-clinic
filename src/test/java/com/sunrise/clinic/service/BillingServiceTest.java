package com.sunrise.clinic.service;

import com.sunrise.clinic.dao.AppointmentDAO;
import com.sunrise.clinic.dao.BillDAO;
import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.model.Bill;
import com.sunrise.clinic.model.Patient;
import com.sunrise.clinic.model.Treatment;
import com.sunrise.clinic.pattern.StandardBillingStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BillingServiceTest {

    @Mock
    private AppointmentDAO appointmentDAO;
    @Mock
    private BillDAO billDAO;

    @Test
    void calculateTotalAddsConsultationFee() {
        BillingService service = new BillingService(appointmentDAO, billDAO, new StandardBillingStrategy());
        assertEquals(new BigDecimal("9500.00"), service.calculateTotal(new BigDecimal("8000.00")));
    }

    @Test
    void createBillRejectsUnknownAppointment() {
        BillingService service = new BillingService(appointmentDAO, billDAO, new StandardBillingStrategy());
        when(appointmentDAO.findByNumber("APT-2026-9999")).thenReturn(null);
        assertThrows(ClinicException.class, () -> service.createBill("APT-2026-9999"));
    }

    @Test
    void createBillSavesTreatmentPlusConsultation() {
        Appointment appointment = sampleAppointment();
        when(appointmentDAO.findByNumber("APT-2026-0001")).thenReturn(appointment);
        when(billDAO.existsForAppointment(11)).thenReturn(false);

        BillingService service = new BillingService(appointmentDAO, billDAO, new StandardBillingStrategy());
        Bill bill = service.createBill("APT-2026-0001");

        assertEquals(new BigDecimal("8000.00"), bill.getTreatmentCost());
        assertEquals(new BigDecimal("1500.00"), bill.getConsultationFee());
        assertEquals(new BigDecimal("9500.00"), bill.getTotalAmount());
        verify(billDAO).insert(bill);
        verify(appointmentDAO).markCompleted(11);
    }

    private Appointment sampleAppointment() {
        Patient patient = new Patient();
        patient.setName("Kamal Perera");
        Treatment treatment = new Treatment();
        treatment.setTypeName("Filling");
        treatment.setCost(new BigDecimal("8000.00"));
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(11);
        appointment.setAppointmentNumber("APT-2026-0001");
        appointment.setPatient(patient);
        appointment.setTreatment(treatment);
        return appointment;
    }
}
