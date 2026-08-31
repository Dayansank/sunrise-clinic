package com.sunrise.clinic.pattern;

import com.sunrise.clinic.dao.AppointmentDAO;
import com.sunrise.clinic.dao.DentistDAO;
import com.sunrise.clinic.dao.TreatmentDAO;

/**
 * Patient self-booking. created_by is null/0 because no staff member typed this in.
 */
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
