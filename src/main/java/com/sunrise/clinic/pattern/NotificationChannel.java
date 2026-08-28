package com.sunrise.clinic.pattern;

import com.sunrise.clinic.model.Appointment;

public interface NotificationChannel {
    void send(Appointment appointment, String message);
}
