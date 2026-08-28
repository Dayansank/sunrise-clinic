package com.sunrise.clinic.service;

import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.pattern.AppointmentObserver;
import com.sunrise.clinic.pattern.ClinicNotificationFactory;
import com.sunrise.clinic.pattern.NotificationChannel;
import com.sunrise.clinic.pattern.NotificationFactory;

public class NotificationService implements AppointmentObserver {
    private final NotificationChannel emailChannel;
    private final NotificationChannel smsChannel;

    public NotificationService() {
        this(new ClinicNotificationFactory());
    }

    public NotificationService(NotificationFactory factory) {
        this.emailChannel = factory.createEmailChannel();
        this.smsChannel = factory.createSmsChannel();
    }

    @Override
    public void onBooked(Appointment appointment) {
        String body = "Your Sunrise Dental Clinic appointment " + appointment.getAppointmentNumber()
                + " is booked for " + appointment.getAppointmentDate() + " at "
                + appointment.getAppointmentTime() + " with " + appointment.getDentist().getName() + ".";
        emailChannel.send(appointment, body);
        smsChannel.send(appointment, body);
    }
}
