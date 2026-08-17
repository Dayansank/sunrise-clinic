package com.sunrise.clinic.pattern;

import com.sunrise.clinic.model.Appointment;

/**
 * Adapter. SMS and email are different, but the booking code just calls send().
 */
public interface NotificationChannel {
    void send(Appointment appointment, String message);
}
