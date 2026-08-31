package com.sunrise.clinic.model;

/** Admin login. Can manage staff and the catalogue. */
public class AdminUser extends StaffUser {
    public boolean canManageStaff() {
        return true;
    }
}
