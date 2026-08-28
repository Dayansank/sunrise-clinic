package com.sunrise.clinic.servlet;

import com.sunrise.clinic.model.Patient;
import com.sunrise.clinic.pattern.CancelAppointmentCommand;
import com.sunrise.clinic.pattern.ClinicCommand;
import com.sunrise.clinic.service.AppointmentService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/patient-cancel")
public class PatientCancelServlet extends HttpServlet {
    private final AppointmentService appointmentService = new AppointmentService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Patient patient = (Patient) request.getSession().getAttribute("patientUser");
        ClinicCommand command = new CancelAppointmentCommand(
                appointmentService,
                Integer.parseInt(request.getParameter("appointmentId")),
                patient.getPatientId()
        );
        command.execute();
        response.sendRedirect(request.getContextPath() + "/patient-appointments");
    }
}
