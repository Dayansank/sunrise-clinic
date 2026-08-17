package com.sunrise.clinic.servlet;

import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.Patient;
import com.sunrise.clinic.service.PatientAuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/patient-login")
public class PatientLoginServlet extends HttpServlet {
    private final PatientAuthService authService = new PatientAuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/patient-login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Patient patient = authService.login(request.getParameter("email"), request.getParameter("password"));
            request.getSession(true).setAttribute("patientUser", patient);
            response.sendRedirect(request.getContextPath() + "/patient-home");
        } catch (ClinicException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/patient-login.jsp").forward(request, response);
        }
    }
}
