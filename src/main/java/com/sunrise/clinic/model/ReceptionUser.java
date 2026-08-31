package com.sunrise.clinic.model;

/** Reception login. Walk-in desk and bills. */
public class ReceptionUser extends StaffUser {
    public boolean canWorkFrontDesk() {
        return true;
    }
}
