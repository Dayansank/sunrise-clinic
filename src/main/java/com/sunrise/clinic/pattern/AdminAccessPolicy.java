package com.sunrise.clinic.pattern;

/**
 * Admin policy. Reports, staff accounts and catalogue are allowed.
 * Walk-in register and printing bills stay with reception.
 */
public class AdminAccessPolicy implements StaffAccessPolicy {
    @Override
    public boolean canRegisterAppointments() {
        return false;
    }

    @Override
    public boolean canSearchAppointments() {
        return true;
    }

    @Override
    public boolean canCreateBills() {
        return false;
    }

    @Override
    public boolean canViewReports() {
        return true;
    }

    @Override
    public boolean canManageStaff() {
        return true;
    }
}
