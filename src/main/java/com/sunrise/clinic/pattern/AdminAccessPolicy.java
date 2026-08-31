package com.sunrise.clinic.pattern;

/** Admin: reports, staff, catalogue. Not walk-in bills. */
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
