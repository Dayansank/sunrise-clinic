package com.sunrise.clinic.pattern;

import com.sunrise.clinic.model.AdminUser;
import com.sunrise.clinic.model.ReceptionUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StaffAccessPolicyTest {

    @Test
    void receptionHandlesDeskNotReports() {
        ReceptionUser reception = new ReceptionUser();
        reception.setRole("RECEPTION");
        StaffAccessPolicy access = StaffAccessPolicy.forUser(reception);
        assertTrue(access.canRegisterAppointments());
        assertTrue(access.canCreateBills());
        assertTrue(access.canSearchAppointments());
        assertFalse(access.canViewReports());
        assertFalse(access.canManageStaff());
    }

    @Test
    void adminHandlesReportsNotDesk() {
        AdminUser admin = new AdminUser();
        admin.setRole("ADMIN");
        StaffAccessPolicy access = StaffAccessPolicy.forUser(admin);
        assertTrue(access.canViewReports());
        assertTrue(access.canSearchAppointments());
        assertTrue(access.canManageStaff());
        assertFalse(access.canRegisterAppointments());
        assertFalse(access.canCreateBills());
    }
}
