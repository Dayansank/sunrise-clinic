package com.sunrise.clinic.pattern;

import com.sunrise.clinic.model.Appointment;

public class QrTicketBuilder {
    private String appointmentNumber = "";
    private String patientName = "";
    private String dentistName = "";
    private String treatment = "";
    private String date = "";
    private String time = "";

    public QrTicketBuilder appointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
        return this;
    }

    public QrTicketBuilder patientName(String patientName) {
        this.patientName = patientName;
        return this;
    }

    public QrTicketBuilder dentistName(String dentistName) {
        this.dentistName = dentistName;
        return this;
    }

    public QrTicketBuilder treatment(String treatment) {
        this.treatment = treatment;
        return this;
    }

    public QrTicketBuilder date(String date) {
        this.date = date;
        return this;
    }

    public QrTicketBuilder time(String time) {
        this.time = time;
        return this;
    }

    public String build() {
        return """
                SUNRISE DENTAL CLINIC
                Appointment: %s
                Patient: %s
                Dentist: %s
                Treatment: %s
                Date: %s
                Time: %s
                Show this QR at reception.
                """.formatted(appointmentNumber, patientName, dentistName, treatment, date, time);
    }

    public static String from(Appointment appointment) {
        return new QrTicketBuilder()
                .appointmentNumber(appointment.getAppointmentNumber())
                .patientName(appointment.getPatient().getName())
                .dentistName(appointment.getDentist().getName())
                .treatment(appointment.getTreatment().getTypeName())
                .date(appointment.getAppointmentDate().toString())
                .time(appointment.getAppointmentTime().toString())
                .build();
    }
}
