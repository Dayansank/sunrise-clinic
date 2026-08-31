package com.sunrise.clinic.pattern.validation;

import com.sunrise.clinic.exception.ClinicException;

/** Name, phone, date and time checked one after another. */
public abstract class ValidationHandler {
    private ValidationHandler next;

    public ValidationHandler linkWith(ValidationHandler next) {
        this.next = next;
        return next;
    }

    public void handle(BookingContext context) {
        check(context);
        if (next != null) {
            next.handle(context);
        }
    }

    protected abstract void check(BookingContext context);

    protected void fail(String message) {
        throw new ClinicException(message);
    }
}
