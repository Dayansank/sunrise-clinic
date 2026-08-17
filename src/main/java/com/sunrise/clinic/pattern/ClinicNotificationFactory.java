package com.sunrise.clinic.pattern;

import com.sunrise.clinic.dao.NotificationDAO;

public class ClinicNotificationFactory implements NotificationFactory {
    private final NotificationDAO notificationDAO = new NotificationDAO();

    @Override
    public NotificationChannel createEmailChannel() {
        return new EmailChannelAdapter(notificationDAO);
    }

    @Override
    public NotificationChannel createSmsChannel() {
        return new SmsChannelAdapter(notificationDAO);
    }
}
