package com.sunrise.clinic.pattern.validation;

import com.sunrise.clinic.util.ValidationUtil;

public class NameHandler extends ValidationHandler {
    @Override
    protected void check(BookingContext context) {
        if (!ValidationUtil.isValidName(context.name)) {
            fail("Patient name must be between 3 and 100 characters.");
        }
    }
}
