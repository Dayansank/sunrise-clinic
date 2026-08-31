package com.sunrise.clinic.model;

/**
 * Last level of the staff chain: Person → ClinicUser → StaffUser → AdminUser.
 * Only admin can manage other staff and clinic catalogue data.
 */
public class AdminUser extends StaffUser {
    public boolean canManageStaff() {
        return true;
    }
}
