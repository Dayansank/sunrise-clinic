package com.sunrise.clinic.pattern;

import com.sunrise.clinic.model.Appointment;

/** Common send() for email and SMS. */
public interface NotificationChannel {
    void send(Appointment appointment, String message);
}
