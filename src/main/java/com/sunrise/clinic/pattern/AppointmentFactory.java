package com.sunrise.clinic.pattern;

import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.model.Dentist;
import com.sunrise.clinic.model.Patient;
import com.sunrise.clinic.model.Treatment;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Factory method. I did not want servlets to set 8 appointment fields by hand,
 * so this class builds a BOOKED appointment in one place.
 */
public final class AppointmentFactory {
    private AppointmentFactory() {
    }

    public static Appointment create(String appointmentNumber, Patient patient, Dentist dentist,
                                     Treatment treatment, LocalDate date, LocalTime time, int createdBy) {
        return create(appointmentNumber, patient, dentist, treatment, date, time, createdBy, "STAFF");
    }

    public static Appointment create(String appointmentNumber, Patient patient, Dentist dentist,
                                     Treatment treatment, LocalDate date, LocalTime time,
                                     int createdBy, String bookedBy) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentNumber(appointmentNumber);
        appointment.setPatient(patient);
        appointment.setDentist(dentist);
        appointment.setTreatment(treatment);
        appointment.setAppointmentDate(date);
        appointment.setAppointmentTime(time);
        appointment.setStatus("BOOKED");
        appointment.setCreatedBy(createdBy);
        appointment.setBookedBy(bookedBy);
        return appointment;
    }
}
