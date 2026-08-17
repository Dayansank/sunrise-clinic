package com.sunrise.clinic.servlet;

import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.model.Patient;
import com.sunrise.clinic.model.StaffUser;
import com.sunrise.clinic.service.AppointmentService;
import com.sunrise.clinic.service.QrCodeService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/qr")
public class QrServlet extends HttpServlet {
    private final AppointmentService appointmentService = new AppointmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String number = request.getParameter("number");
        if (number == null || number.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Appointment number is required.");
            return;
        }
        Appointment appointment;
        try {
            appointment = appointmentService.findByNumber(number);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Appointment not found.");
            return;
        }

        StaffUser staff = (StaffUser) request.getSession().getAttribute("staffUser");
        Patient patient = (Patient) request.getSession().getAttribute("patientUser");
        if (staff == null && (patient == null || patient.getPatientId() != appointment.getPatient().getPatientId())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        byte[] png = QrCodeService.getInstance().png(appointment);
        response.setContentType("image/png");
        response.setContentLength(png.length);
        response.getOutputStream().write(png);
    }
}
