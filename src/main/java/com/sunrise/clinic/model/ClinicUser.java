package com.sunrise.clinic.model;

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
