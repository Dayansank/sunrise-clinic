package com.sunrise.clinic.pattern;

public interface NotificationFactory {
    NotificationChannel createEmailChannel();

    NotificationChannel createSmsChannel();
}
