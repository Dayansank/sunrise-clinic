package com.sunrise.clinic.servlet;

import com.sunrise.clinic.model.Patient;
import com.sunrise.clinic.service.AppointmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/patient-home")
public class PatientHomeServlet extends HttpServlet {
    private final AppointmentService appointmentService = new AppointmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Patient patient = (Patient) request.getSession().getAttribute("patientUser");
        request.setAttribute("appointments", appointmentService.findByPatient(patient.getPatientId()));
        request.getRequestDispatcher("/patient-home.jsp").forward(request, response);
    }
}
