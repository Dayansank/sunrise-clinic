package com.sunrise.clinic.model;

public class ReceptionUser extends StaffUser {
    public boolean canWorkFrontDesk() {
        return true;
    }
}
