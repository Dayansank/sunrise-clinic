package com.sunrise.clinic.pattern;

import com.sunrise.clinic.model.Appointment;

/** Called after a booking is saved (SMS/email log). */
public interface AppointmentObserver {
    void onBooked(Appointment appointment);
}
