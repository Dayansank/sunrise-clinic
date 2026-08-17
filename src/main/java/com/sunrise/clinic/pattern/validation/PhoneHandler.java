package com.sunrise.clinic.pattern.validation;

import com.sunrise.clinic.util.ValidationUtil;

public class PhoneHandler extends ValidationHandler {
    @Override
    protected void check(BookingContext context) {
        if (!ValidationUtil.isValidPhone(context.phone)) {
            fail("Contact number must be 10 digits starting with 0, or +94 followed by 9 digits.");
        }
    }
}
