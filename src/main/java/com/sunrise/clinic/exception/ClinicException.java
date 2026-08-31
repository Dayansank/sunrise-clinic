package com.sunrise.clinic.exception;

/**
 * Simple exception for clinic rules (double booking, bad login, missing appointment).
 * I used a runtime exception so servlets can catch it and show a friendly message.
 */
public class ClinicException extends RuntimeException {
    public ClinicException(String message) {
        super(message);
    }
}
