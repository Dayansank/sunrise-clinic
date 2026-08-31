package com.sunrise.clinic.pattern;

import com.sunrise.clinic.model.Appointment;

/**
 * Observer. After a booking is saved, anything listening here can send SMS/email.
 */
public interface AppointmentObserver {
    void onBooked(Appointment appointment);
}
