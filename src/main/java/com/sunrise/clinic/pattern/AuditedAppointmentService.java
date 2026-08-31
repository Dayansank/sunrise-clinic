package com.sunrise.clinic.pattern;

import com.sunrise.clinic.service.AppointmentService;

/** Logs a cancel, then calls the real appointment service. */
public class AuditedAppointmentService {
    private final AppointmentService target;

    public AuditedAppointmentService(AppointmentService target) {
        this.target = target;
    }

    public void cancelForPatient(int appointmentId, int patientId) {
        System.out.println("AUDIT: cancel appointment " + appointmentId + " by patient " + patientId);
        target.cancelForPatient(appointmentId, patientId);
    }
}
