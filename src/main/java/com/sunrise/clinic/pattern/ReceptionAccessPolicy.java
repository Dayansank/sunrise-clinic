package com.sunrise.clinic.pattern;

/** Reception can book, search and bill. Not reports. */
public class ReceptionAccessPolicy implements StaffAccessPolicy {
    @Override
    public boolean canRegisterAppointments() {
        return true;
    }

    @Override
    public boolean canSearchAppointments() {
        return true;
    }

    @Override
    public boolean canCreateBills() {
        return true;
    }

    @Override
    public boolean canViewReports() {
        return false;
    }

    @Override
    public boolean canManageStaff() {
        return false;
    }
}
