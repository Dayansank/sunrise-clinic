package com.sunrise.clinic.pattern;

import com.sunrise.clinic.service.AppointmentService;

public class CancelAppointmentCommand implements ClinicCommand {
    private final AppointmentService appointmentService;
    private final int appointmentId;
    private final int patientId;

    public CancelAppointmentCommand(AppointmentService appointmentService, int appointmentId, int patientId) {
        this.appointmentService = appointmentService;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
    }

    @Override
    public void execute() {
        new AuditedAppointmentService(appointmentService).cancelForPatient(appointmentId, patientId);
    }
}
