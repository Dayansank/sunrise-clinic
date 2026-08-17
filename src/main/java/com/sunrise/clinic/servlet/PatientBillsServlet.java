package com.sunrise.clinic.servlet;

import com.sunrise.clinic.model.Patient;
import com.sunrise.clinic.service.BillingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/patient-bills")
public class PatientBillsServlet extends HttpServlet {
    private final BillingService billingService = new BillingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Patient patient = (Patient) request.getSession().getAttribute("patientUser");
        request.setAttribute("bills", billingService.findByPatient(patient.getPatientId()));
        request.getRequestDispatcher("/patient-bills.jsp").forward(request, response);
    }
}
