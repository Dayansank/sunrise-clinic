package com.sunrise.clinic.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordUtilTest {

    @Test
    void hashesAdminPasswordToKnownValue() {
        assertEquals("fd48a5da972ce09dda0bc9b523b59106b7ac4f30f48ecc074340e93b647e821b",
                PasswordUtil.hash("Admin@123"));
    }

    @Test
    void matchesCorrectPassword() {
        assertTrue(PasswordUtil.matches("Staff@123", PasswordUtil.hash("Staff@123")));
        assertFalse(PasswordUtil.matches("wrong", PasswordUtil.hash("Staff@123")));
    }
}
