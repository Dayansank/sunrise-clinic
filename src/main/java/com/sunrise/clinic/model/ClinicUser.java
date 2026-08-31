package com.sunrise.clinic.model;

/**
 * Next level after Person. Clinic users can be switched off with the active flag
 * (for example a deleted reception login).
 */
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
