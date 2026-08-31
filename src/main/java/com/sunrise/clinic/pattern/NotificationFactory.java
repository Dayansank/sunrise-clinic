package com.sunrise.clinic.pattern;

/**
 * Abstract Factory. One factory gives me both email and SMS channels together.
 */
public interface NotificationFactory {
    NotificationChannel createEmailChannel();

    NotificationChannel createSmsChannel();
}
