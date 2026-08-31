package com.sunrise.clinic.pattern;

import com.sunrise.clinic.dao.AppointmentDAO;
import com.sunrise.clinic.dao.DentistDAO;
import com.sunrise.clinic.dao.TreatmentDAO;
import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.model.Dentist;
import com.sunrise.clinic.model.Patient;
import com.sunrise.clinic.model.Treatment;
import com.sunrise.clinic.pattern.validation.BookingValidationChain;
import com.sunrise.clinic.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/** Shared booking steps: validate, check slot, save, notify. */
public abstract class BookingTemplate {
    protected final AppointmentDAO appointmentDAO;
    protected final DentistDAO dentistDAO;
    protected final TreatmentDAO treatmentDAO;
    private final List<AppointmentObserver> observers = new ArrayList<>();

    protected BookingTemplate(AppointmentDAO appointmentDAO, DentistDAO dentistDAO, TreatmentDAO treatmentDAO) {
        this.appointmentDAO = appointmentDAO;
        this.dentistDAO = dentistDAO;
        this.treatmentDAO = treatmentDAO;
    }

    public void addObserver(AppointmentObserver observer) {
        observers.add(observer);
    }

    public final Appointment book(Patient patient, String dentistIdText, String treatmentIdText,
                                  String dateText, String timeText) {
        // Same order every time so I don't miss a check.
        validate(patient, dentistIdText, treatmentIdText, dateText, timeText);
        Dentist dentist = dentistDAO.findById(Integer.parseInt(dentistIdText));
        Treatment treatment = treatmentDAO.findById(Integer.parseInt(treatmentIdText));
        if (dentist == null) {
            throw new ClinicException("Selected dentist was not found.");
        }
        if (treatment == null) {
            throw new ClinicException("Selected treatment was not found.");
        }
        LocalDate date = ValidationUtil.parseDate(dateText);
        LocalTime time = ValidationUtil.parseTime(timeText);
        ensureSlotFree(dentist.getDentistId(), date, time);
        Appointment appointment = persist(patient, dentist, treatment, date, time);
        notifyObservers(appointment);
        return appointment;
    }

    protected void validate(Patient patient, String dentistIdText, String treatmentIdText,
                            String dateText, String timeText) {
        BookingValidationChain.validate(
                patient.getName(), patient.getAddress(), patient.getContactNumber(),
                dentistIdText, treatmentIdText, dateText, timeText);
    }

    protected void ensureSlotFree(int dentistId, LocalDate date, LocalTime time) {
        if (appointmentDAO.isSlotTaken(dentistId, date, time)) {
            throw new ClinicException("This dentist already has an appointment at the selected date and time.");
        }
    }

    protected Appointment persist(Patient patient, Dentist dentist, Treatment treatment,
                                  LocalDate date, LocalTime time) {
        String number = appointmentDAO.nextAppointmentNumber();
        Appointment appointment = AppointmentFactory.create(
                number, patient, dentist, treatment, date, time, createdBy(), bookedBy());
        appointmentDAO.insert(appointment);
        return appointment;
    }

    protected void notifyObservers(Appointment appointment) {
        for (AppointmentObserver observer : observers) {
            observer.onBooked(appointment);
        }
    }

    protected abstract int createdBy();

    protected abstract String bookedBy();
}
