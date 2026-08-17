package com.sunrise.clinic.model;

/**
 * A patient is also a clinic user, so they share name/phone from Person.
 * Extra fields: home address and email for the online portal.
 */
public class Patient extends ClinicUser {
    private int patientId;
    private String address;
    private String email;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getDisplayName() {
        return getName();
    }
}
