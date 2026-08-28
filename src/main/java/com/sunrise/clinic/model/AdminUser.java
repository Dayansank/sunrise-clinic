package com.sunrise.clinic.model;

public class AdminUser extends StaffUser {
    public boolean canManageStaff() {
        return true;
    }
}
