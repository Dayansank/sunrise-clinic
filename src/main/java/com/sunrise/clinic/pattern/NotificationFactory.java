package com.sunrise.clinic.pattern;

/** Gives both the email and SMS senders. */
public interface NotificationFactory {
    NotificationChannel createEmailChannel();

    NotificationChannel createSmsChannel();
}
