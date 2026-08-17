package com.sunrise.clinic.servlet;

import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.service.AppointmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private final AppointmentService appointmentService = new AppointmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String number = request.getParameter("appointmentNumber");
        request.setAttribute("appointmentNumber", number);
        try {
            Appointment appointment = appointmentService.findByNumber(number);
            request.setAttribute("appointment", appointment);
        } catch (ClinicException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }
}
