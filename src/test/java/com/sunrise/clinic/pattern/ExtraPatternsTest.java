package com.sunrise.clinic.pattern;

import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.model.Dentist;
import com.sunrise.clinic.model.Treatment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtraPatternsTest {

    @Test
    void stateBlocksCancelWhenCompleted() {
        assertTrue(AppointmentState.BOOKED.canCancel());
        assertTrue(AppointmentState.BOOKED.canBill());
        assertFalse(AppointmentState.COMPLETED.canCancel());
        assertFalse(AppointmentState.CANCELLED.canBill());
    }

    @Test
    void prototypeCopiesDentistAndTreatment() {
        Dentist dentist = new Dentist();
        dentist.setDentistId(2);
        Treatment treatment = new Treatment();
        treatment.setTreatmentId(3);
        Appointment original = new Appointment();
        original.setDentist(dentist);
        original.setTreatment(treatment);
        original.setAppointmentNumber("APT-2026-0001");
        Appointment copy = original.cloneForRebook();
        assertEquals(2, copy.getDentist().getDentistId());
        assertEquals(3, copy.getTreatment().getTreatmentId());
        assertEquals("BOOKED", copy.getStatus());
    }

    @Test
    void decoratorAddsQrMessage() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentNumber("APT-2026-0001");
        Confirmation confirmation = new BasicConfirmation(appointment);
        assertTrue(confirmation.message().contains("APT-2026-0001"));
        assertTrue(new QrConfirmationDecorator(confirmation, appointment).message().contains("QR"));
    }
}
