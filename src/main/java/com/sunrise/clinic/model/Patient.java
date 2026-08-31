package com.sunrise.clinic.model;

/** Patient record: address and email on top of name/phone. */
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
