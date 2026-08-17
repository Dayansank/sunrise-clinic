package com.sunrise.clinic.pattern;

import com.sunrise.clinic.model.Appointment;

public class BasicConfirmation implements Confirmation {
    private final Appointment appointment;

    public BasicConfirmation(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public String message() {
        return "Appointment confirmed. Your number is " + appointment.getAppointmentNumber() + ".";
    }

    @Override
    public String qrDataUri() {
        return null;
    }
}
