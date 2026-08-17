package com.sunrise.clinic.pattern;

import com.sunrise.clinic.dao.NotificationDAO;
import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.model.Notification;

public class SmsChannelAdapter implements NotificationChannel {
    private final NotificationDAO notificationDAO;

    public SmsChannelAdapter(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    @Override
    public void send(Appointment appointment, String message) {
        notificationDAO.insert(new Notification(
                appointment.getAppointmentNumber(),
                "SMS",
                appointment.getPatient().getContactNumber(),
                message));
    }
}
