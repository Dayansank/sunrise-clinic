package com.sunrise.clinic.model;

/**
 * Person is the first class in my inheritance chain.
 * Everyone in the clinic (staff and patients) has a name and a phone number.
 */
public abstract class Person {
    private String name;
    private String contactNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
