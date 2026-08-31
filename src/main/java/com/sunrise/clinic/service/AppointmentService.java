package com.sunrise.clinic.service;

import com.sunrise.clinic.dao.AppointmentDAO;
import com.sunrise.clinic.dao.DentistDAO;
import com.sunrise.clinic.dao.PatientDAO;
import com.sunrise.clinic.dao.TreatmentDAO;
import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.model.Dentist;
import com.sunrise.clinic.model.Patient;
import com.sunrise.clinic.model.Treatment;
import com.sunrise.clinic.pattern.AppointmentObserver;
import com.sunrise.clinic.pattern.PatientBookingProcess;
import com.sunrise.clinic.pattern.StaffBookingProcess;
import com.sunrise.clinic.util.ValidationUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Booking rules. Servlets call this, not the DAO. */
public class AppointmentService {
    private final AppointmentDAO appointmentDAO;
    private final PatientDAO patientDAO;
    private final DentistDAO dentistDAO;
    private final TreatmentDAO treatmentDAO;
    private final List<AppointmentObserver> observers = new ArrayList<>();

    public AppointmentService() {
        this(new AppointmentDAO(), new PatientDAO(), new DentistDAO(), new TreatmentDAO());
        addObserver(new NotificationService());
    }

    public AppointmentService(AppointmentDAO appointmentDAO, PatientDAO patientDAO,
                              DentistDAO dentistDAO, TreatmentDAO treatmentDAO) {
        this.appointmentDAO = appointmentDAO;
        this.patientDAO = patientDAO;
        this.dentistDAO = dentistDAO;
        this.treatmentDAO = treatmentDAO;
    }

    public void addObserver(AppointmentObserver observer) {
        observers.add(observer);
    }

    public List<Dentist> listDentists() {
        return dentistDAO.findAll();
    }

    public List<Treatment> listTreatments() {
        return treatmentDAO.findAll();
    }

    public Appointment register(String name, String address, String phone, String dentistIdText,
                                String treatmentIdText, String dateText, String timeText, int staffId) {
        Patient patient = new Patient();
        patient.setName(name == null ? "" : name.trim());
        patient.setAddress(address == null ? "" : address.trim());
        patient.setContactNumber(phone == null ? "" : phone.trim());
        patientDAO.insert(patient);
        StaffBookingProcess process = new StaffBookingProcess(appointmentDAO, dentistDAO, treatmentDAO, staffId);
        observers.forEach(process::addObserver);
        return process.book(patient, dentistIdText, treatmentIdText, dateText, timeText);
    }

    public Appointment bookForPatient(Patient patient, String dentistIdText, String treatmentIdText,
                                      String dateText, String timeText) {
        PatientBookingProcess process = new PatientBookingProcess(appointmentDAO, dentistDAO, treatmentDAO);
        observers.forEach(process::addObserver);
        return process.book(patient, dentistIdText, treatmentIdText, dateText, timeText);
    }

    public List<Appointment> findByPatient(int patientId) {
        return appointmentDAO.findByPatientId(patientId);
    }

    public Appointment findById(int appointmentId) {
        return appointmentDAO.findById(appointmentId);
    }

    public void cancelForPatient(int appointmentId, int patientId) {
        Appointment appointment = appointmentDAO.findById(appointmentId);
        if (appointment == null || appointment.getPatient().getPatientId() != patientId) {
            throw new ClinicException("Appointment not found.");
        }
        if (!com.sunrise.clinic.pattern.AppointmentState.from(appointment.getStatus()).canCancel()) {
            throw new ClinicException("This appointment cannot be cancelled in its current state.");
        }
        appointmentDAO.cancel(appointmentId, patientId);
    }

    public Appointment findByNumber(String appointmentNumber) {
        if (ValidationUtil.isBlank(appointmentNumber)) {
            throw new ClinicException("Appointment number is required.");
        }
        Appointment appointment = appointmentDAO.findByNumber(appointmentNumber.trim().toUpperCase());
        if (appointment == null) {
            throw new ClinicException("No appointment found for number " + appointmentNumber.trim() + ".");
        }
        return appointment;
    }

    public List<Appointment> findByDate(LocalDate date) {
        return appointmentDAO.findByDate(date);
    }
}
