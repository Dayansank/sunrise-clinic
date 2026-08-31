package com.sunrise.clinic.service;

import com.sunrise.clinic.dao.AppointmentDAO;
import com.sunrise.clinic.dao.BillDAO;
import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.pattern.StandardBillingStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BillingServiceTest {

    @Mock
    private AppointmentDAO appointmentDAO;
    @Mock
    private BillDAO billDAO;

    @Test
    void createBillRejectsUnknownAppointment() {
        BillingService service = new BillingService(appointmentDAO, billDAO, new StandardBillingStrategy());
        when(appointmentDAO.findByNumber("APT-2026-9999")).thenReturn(null);
        assertThrows(ClinicException.class, () -> service.createBill("APT-2026-9999"));
    }
}
