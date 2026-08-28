package com.sunrise.clinic.pattern;

import com.sunrise.clinic.dao.AppointmentDAO;
import com.sunrise.clinic.dao.DentistDAO;
import com.sunrise.clinic.dao.TreatmentDAO;

public class StaffBookingProcess extends BookingTemplate {
    private final int staffId;

    public StaffBookingProcess(AppointmentDAO appointmentDAO, DentistDAO dentistDAO,
                               TreatmentDAO treatmentDAO, int staffId) {
        super(appointmentDAO, dentistDAO, treatmentDAO);
        this.staffId = staffId;
    }

    @Override
    protected int createdBy() {
        return staffId;
    }

    @Override
    protected String bookedBy() {
        return "STAFF";
    }
}
