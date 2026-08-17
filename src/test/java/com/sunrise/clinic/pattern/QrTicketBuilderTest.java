package com.sunrise.clinic.pattern;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class QrTicketBuilderTest {

    @Test
    void buildsReadableTicketText() {
        String ticket = new QrTicketBuilder()
                .appointmentNumber("APT-2026-0001")
                .patientName("Kamal Perera")
                .dentistName("Dr. Nimal Perera")
                .treatment("Filling")
                .date("2026-08-20")
                .time("10:30")
                .build();
        assertTrue(ticket.contains("APT-2026-0001"));
        assertTrue(ticket.contains("Kamal Perera"));
        assertTrue(ticket.contains("Show this QR at reception."));
    }
}
