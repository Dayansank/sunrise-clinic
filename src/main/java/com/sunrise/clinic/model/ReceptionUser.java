package com.sunrise.clinic.model;

/**
 * Reception desk user. Same inheritance as admin, but this role works the front desk.
 */
public class ReceptionUser extends StaffUser {
    public boolean canWorkFrontDesk() {
        return true;
    }
}
