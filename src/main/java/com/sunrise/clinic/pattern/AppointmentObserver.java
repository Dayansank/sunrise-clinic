package com.sunrise.clinic.pattern;

import com.sunrise.clinic.model.Appointment;

public interface AppointmentObserver {
    void onBooked(Appointment appointment);
}
