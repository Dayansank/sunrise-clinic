package com.sunrise.clinic.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class ValidationUtil {
    private static final Pattern PHONE = Pattern.compile("^(0\\d{9}|\\+94\\d{9})$");
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final LocalTime OPEN = LocalTime.of(9, 0);
    private static final LocalTime CLOSE = LocalTime.of(17, 0);

    private ValidationUtil() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE.matcher(phone.trim()).matches();
    }

    public static boolean isValidName(String name) {
        return !isBlank(name) && name.trim().length() >= 3 && name.trim().length() <= 100;
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL.matcher(email.trim()).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static List<String> validateAppointment(String name, String address, String phone,
                                                   String dentistId, String treatmentId,
                                                   String dateText, String timeText) {
        List<String> errors = new ArrayList<>();
        if (!isValidName(name)) {
            errors.add("Patient name must be between 3 and 100 characters.");
        }
        if (isBlank(address) || address.trim().length() < 5) {
            errors.add("Address must be at least 5 characters.");
        }
        if (!isValidPhone(phone)) {
            errors.add("Contact number must be 10 digits starting with 0, or +94 followed by 9 digits.");
        }
        if (isBlank(dentistId)) {
            errors.add("Please select a dentist.");
        }
        if (isBlank(treatmentId)) {
            errors.add("Please select a treatment type.");
        }
        LocalDate date = parseDate(dateText);
        if (date == null) {
            errors.add("Appointment date is required and must be valid.");
        } else if (date.isBefore(LocalDate.now())) {
            errors.add("Appointment date cannot be in the past.");
        } else if (date.getDayOfWeek().getValue() == 7) {
            errors.add("The clinic is closed on Sundays.");
        }
        LocalTime time = parseTime(timeText);
        if (time == null) {
            errors.add("Appointment time is required and must be valid.");
        } else if (time.isBefore(OPEN) || !time.isBefore(CLOSE)) {
            errors.add("Appointment time must be between 09:00 and 16:30 (clinic closes at 17:00).");
        }
        return errors;
    }

    public static LocalDate parseDate(String text) {
        try {
            return isBlank(text) ? null : LocalDate.parse(text.trim());
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static LocalTime parseTime(String text) {
        try {
            return isBlank(text) ? null : LocalTime.parse(text.trim());
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
