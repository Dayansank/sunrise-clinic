package com.sunrise.clinic.service;

import com.sunrise.clinic.model.Appointment;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportChartServiceTest {

    @Test
    void doughnutCountsBookedVisits() {
        Appointment booked = new Appointment();
        booked.setStatus("BOOKED");
        AppointmentService appointments = mock(AppointmentService.class);
        BillingService bills = mock(BillingService.class);
        LocalDate day = LocalDate.of(2026, 9, 1);
        when(appointments.findByDate(day)).thenReturn(List.of(booked));
        when(bills.incomeByDentist()).thenReturn(List.of());

        String json = new ReportChartService(appointments, bills).statusDoughnutJson(day);
        assertTrue(json.contains("BOOKED"));
        assertTrue(json.contains("1"));
    }
}
