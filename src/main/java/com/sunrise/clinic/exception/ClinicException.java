package com.sunrise.clinic.exception;

/** Thrown for clinic rules like double booking or a bad login. */
public class ClinicException extends RuntimeException {
    public ClinicException(String message) {
        super(message);
    }
}
