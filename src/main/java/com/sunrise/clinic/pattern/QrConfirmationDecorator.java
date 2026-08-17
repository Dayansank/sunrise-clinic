package com.sunrise.clinic.pattern;

import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.service.QrCodeService;

public class QrConfirmationDecorator implements Confirmation {
    private final Confirmation inner;
    private final Appointment appointment;

    public QrConfirmationDecorator(Confirmation inner, Appointment appointment) {
        this.inner = inner;
        this.appointment = appointment;
    }

    @Override
    public String message() {
        return inner.message() + " Show the QR code at reception.";
    }

    @Override
    public String qrDataUri() {
        return QrCodeService.getInstance().dataUri(appointment);
    }
}
