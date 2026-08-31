package com.sunrise.clinic.pattern.validation;

/**
 * Starts the validation chain in a fixed order so every booking is checked the same way.
 */
public final class BookingValidationChain {
    private BookingValidationChain() {
    }

    public static ValidationHandler create() {
        ValidationHandler name = new NameHandler();
        name.linkWith(new AddressHandler())
                .linkWith(new PhoneHandler())
                .linkWith(new DateHandler())
                .linkWith(new TimeHandler());
        return name;
    }

    public static void validate(String name, String address, String phone, String dentistId,
                                String treatmentId, String dateText, String timeText) {
        create().handle(new BookingContext(name, address, phone, dentistId, treatmentId, dateText, timeText));
    }
}
