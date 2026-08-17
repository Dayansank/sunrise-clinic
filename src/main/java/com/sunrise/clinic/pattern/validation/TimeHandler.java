package com.sunrise.clinic.pattern.validation;

import com.sunrise.clinic.util.ValidationUtil;

import java.time.LocalTime;

public class TimeHandler extends ValidationHandler {
    @Override
    protected void check(BookingContext context) {
        LocalTime time = ValidationUtil.parseTime(context.timeText);
        if (time == null) {
            fail("Appointment time is required and must be valid.");
        } else if (time.isBefore(LocalTime.of(9, 0)) || !time.isBefore(LocalTime.of(17, 0))) {
            fail("Appointment time must be between 09:00 and 16:30 (clinic closes at 17:00).");
        }
    }
}
