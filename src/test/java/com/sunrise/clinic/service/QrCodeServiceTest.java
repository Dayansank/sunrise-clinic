package com.sunrise.clinic.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class QrCodeServiceTest {

    @Test
    void generatesPngBytes() {
        byte[] png = QrCodeService.getInstance().png("SUNRISE APT-2026-0001", 120);
        assertTrue(png.length > 50);
        assertTrue(png[0] == (byte) 0x89 && png[1] == 0x50);
    }
}
