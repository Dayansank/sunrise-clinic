package com.sunrise.clinic.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.pattern.QrTicketBuilder;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

/** Turns appointment text into a QR PNG. */
public class QrCodeService {
    private static final QrCodeService INSTANCE = new QrCodeService();

    private QrCodeService() {
    }

    public static QrCodeService getInstance() {
        return INSTANCE;
    }

    public byte[] png(Appointment appointment) {
        return png(QrTicketBuilder.from(appointment), 280);
    }

    public byte[] png(String text, int size) {
        try {
            BitMatrix matrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to generate QR code.", e);
        }
    }

    public String dataUri(Appointment appointment) {
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(png(appointment));
    }
}
