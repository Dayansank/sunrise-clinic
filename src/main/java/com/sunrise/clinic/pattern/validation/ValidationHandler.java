package com.sunrise.clinic.pattern.validation;

import com.sunrise.clinic.exception.ClinicException;

/**
 * Chain of Responsibility. Name, address, phone, date and time are checked
 * one after another. If one step fails, we stop and show that error.
 */
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
