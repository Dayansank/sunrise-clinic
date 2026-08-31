package com.sunrise.clinic.pattern;

/** Booking confirmation text. QR version adds the code. */
public interface Confirmation {
    String message();

    String qrDataUri();
}
