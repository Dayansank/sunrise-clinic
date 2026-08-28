package com.sunrise.clinic.pattern;

import com.sunrise.clinic.dao.AppointmentDAO;
import com.sunrise.clinic.dao.DentistDAO;
import com.sunrise.clinic.dao.TreatmentDAO;

public class PatientBookingProcess extends BookingTemplate {
    public PatientBookingProcess(AppointmentDAO appointmentDAO, DentistDAO dentistDAO, TreatmentDAO treatmentDAO) {
        super(appointmentDAO, dentistDAO, treatmentDAO);
    }

    @Override
    protected int createdBy() {
        return 0;
    }

    @Override
    protected String bookedBy() {
        return "PATIENT";
    }
}
