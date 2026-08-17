package com.sunrise.clinic.pattern;

import com.sunrise.clinic.dao.NotificationDAO;
import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.model.Notification;

public class EmailChannelAdapter implements NotificationChannel {
    private final NotificationDAO notificationDAO;

    public EmailChannelAdapter(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    @Override
    public void send(Appointment appointment, String message) {
        String email = appointment.getPatient().getEmail();
        if (email == null || email.isBlank()) {
            return;
        }
        notificationDAO.insert(new Notification(appointment.getAppointmentNumber(), "EMAIL", email, message));
    }
}
