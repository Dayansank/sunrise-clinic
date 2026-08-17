package com.sunrise.clinic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MultilevelInheritanceTest {

    @Test
    void adminUserIsMultilevelStaff() {
        AdminUser admin = new AdminUser();
        admin.setFullName("Nadeesha Perera");
        admin.setRole("ADMIN");
        assertInstanceOf(StaffUser.class, admin);
        assertInstanceOf(ClinicUser.class, admin);
        assertInstanceOf(Person.class, admin);
        assertEquals("Nadeesha Perera", admin.getName());
        assertTrue(admin.canManageStaff());
    }

    @Test
    void receptionUserIsMultilevelStaff() {
        ReceptionUser reception = new ReceptionUser();
        reception.setFullName("Samanthi Jayasuriya");
        reception.setRole("RECEPTION");
        assertInstanceOf(StaffUser.class, reception);
        assertInstanceOf(ClinicUser.class, reception);
        assertTrue(reception.canWorkFrontDesk());
    }

    @Test
    void patientIsClinicUser() {
        Patient patient = new Patient();
        patient.setName("Kamal Perera");
        assertInstanceOf(ClinicUser.class, patient);
        assertInstanceOf(Person.class, patient);
        assertEquals("Kamal Perera", patient.getDisplayName());
    }
}
