package com.sunrise.clinic.pattern.validation;

import com.sunrise.clinic.util.ValidationUtil;

public class AddressHandler extends ValidationHandler {
    @Override
    protected void check(BookingContext context) {
        if (ValidationUtil.isBlank(context.address) || context.address.trim().length() < 5) {
            fail("Address must be at least 5 characters.");
        }
    }
}
