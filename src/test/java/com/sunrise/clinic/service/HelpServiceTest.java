package com.sunrise.clinic.service;

import com.sunrise.clinic.model.StaffUser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HelpServiceTest {

    @Test
    void receptionHelpMentionsRegisterAndBill() {
        StaffUser reception = new StaffUser();
        reception.setRole("RECEPTION");
        List<String> steps = new HelpService().steps(reception);
        String all = String.join(" ", steps).toLowerCase();
        assertTrue(all.contains("register"));
        assertTrue(all.contains("bill"));
        assertTrue(all.contains("exit"));
    }

    @Test
    void adminHelpDoesNotTellThemToRegisterWalkIn() {
        StaffUser admin = new StaffUser();
        admin.setRole("ADMIN");
        List<String> steps = new HelpService().steps(admin);
        String all = String.join(" ", steps).toLowerCase();
        assertTrue(all.contains("reception"));
        assertTrue(all.contains("exit"));
    }
}
