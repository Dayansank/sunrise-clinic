package com.sunrise.clinic.model;

public class Notification {
    private String appointmentNumber;
    private String channel;
    private String recipient;
    private String message;

    public Notification(String appointmentNumber, String channel, String recipient, String message) {
        this.appointmentNumber = appointmentNumber;
        this.channel = channel;
        this.recipient = recipient;
        this.message = message;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public String getChannel() {
        return channel;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }
}
