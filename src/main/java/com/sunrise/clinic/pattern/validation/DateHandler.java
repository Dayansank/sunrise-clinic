package com.sunrise.clinic.pattern.validation;

import com.sunrise.clinic.util.ValidationUtil;

import java.time.LocalDate;

public class DateHandler extends ValidationHandler {
    @Override
    protected void check(BookingContext context) {
        LocalDate date = ValidationUtil.parseDate(context.dateText);
        if (date == null) {
            fail("Appointment date is required and must be valid.");
        } else if (date.isBefore(LocalDate.now())) {
            fail("Appointment date cannot be in the past.");
        } else if (date.getDayOfWeek().getValue() == 7) {
            fail("The clinic is closed on Sundays.");
        }
        if (ValidationUtil.isBlank(context.dentistId)) {
            fail("Please select a dentist.");
        }
        if (ValidationUtil.isBlank(context.treatmentId)) {
            fail("Please select a treatment type.");
        }
    }
}
