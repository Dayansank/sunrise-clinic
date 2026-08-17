package com.sunrise.clinic.pattern;

import com.sunrise.clinic.service.AppointmentService;

/**
 * Proxy. I did not want to put System.out logging inside AppointmentService,
 * so this wrapper logs the cancel and then calls the real service.
 */
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
