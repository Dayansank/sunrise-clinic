package com.sunrise.clinic.pattern;

/**
 * Decorator. Basic confirmation is just text; the QR decorator wraps it and adds a code.
 */
public interface Confirmation {
    String message();

    String qrDataUri();
}
