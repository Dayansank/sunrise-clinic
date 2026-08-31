package com.sunrise.clinic.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationUtilTest {

    @Test
    void acceptsSriLankanPhoneNumbers() {
        assertTrue(ValidationUtil.isValidPhone("0771234567"));
        assertTrue(ValidationUtil.isValidPhone("+94771234567"));
        assertFalse(ValidationUtil.isValidPhone("771234567"));
        assertFalse(ValidationUtil.isValidPhone("07712"));
    }

    @Test
    void rejectsPastDatesAndSundayAndShortNames() {
        List<String> errors = ValidationUtil.validateAppointment(
                "Al",
                "12",
                "07712",
                "",
                "",
                LocalDate.now().minusDays(1).toString(),
                "08:00"
        );
        assertFalse(errors.isEmpty());
        assertTrue(errors.stream().anyMatch(e -> e.toLowerCase().contains("name")));
        assertTrue(errors.stream().anyMatch(e -> e.toLowerCase().contains("past")
                || e.toLowerCase().contains("time")
                || e.toLowerCase().contains("dentist")));
    }

    @Test
    void acceptsValidAppointmentInput() {
        LocalDate date = LocalDate.now().plusDays(1);
        if (date.getDayOfWeek().getValue() == 7) {
            date = date.plusDays(1);
        }
        List<String> errors = ValidationUtil.validateAppointment(
                "Kamal Perera",
                "12 Galle Road, Colombo",
                "0771234567",
                "1",
                "2",
                date.toString(),
                "10:30"
        );
        assertTrue(errors.isEmpty(), errors::toString);
    }
}
