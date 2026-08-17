package com.sunrise.clinic.pattern.validation;

public class BookingContext {
    public final String name;
    public final String address;
    public final String phone;
    public final String dentistId;
    public final String treatmentId;
    public final String dateText;
    public final String timeText;

    public BookingContext(String name, String address, String phone, String dentistId,
                          String treatmentId, String dateText, String timeText) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.dentistId = dentistId;
        this.treatmentId = treatmentId;
        this.dateText = dateText;
        this.timeText = timeText;
    }
}
