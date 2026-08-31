package com.sunrise.clinic.model;

/** A person who can log in. active=false means the login was switched off. */
public abstract class ClinicUser extends Person {
    private boolean active = true;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract String getDisplayName();
}
