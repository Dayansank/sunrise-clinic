package com.sunrise.clinic.pattern.validation;

import com.sunrise.clinic.exception.ClinicException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookingValidationChainTest {

    @Test
    void rejectsShortNameAtFirstLevel() {
        assertThrows(ClinicException.class, () -> BookingValidationChain.validate(
                "Al", "12 Galle Road", "0771234567", "1", "2",
                LocalDate.now().plusDays(1).toString(), "10:30"));
    }

    @Test
    void acceptsValidBookingData() {
        LocalDate date = LocalDate.now().plusDays(1);
        if (date.getDayOfWeek().getValue() == 7) {
            date = date.plusDays(1);
        }
        LocalDate finalDate = date;
        assertDoesNotThrow(() -> BookingValidationChain.validate(
                "Kamal Perera", "12 Galle Road, Colombo", "0771234567", "1", "2",
                finalDate.toString(), "10:30"));
    }
}
