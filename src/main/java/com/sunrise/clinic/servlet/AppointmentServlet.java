package com.sunrise.clinic.servlet;

import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.model.StaffUser;
import com.sunrise.clinic.pattern.StaffAccessPolicy;
import com.sunrise.clinic.service.AppointmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Walk-in form for reception. */
@WebServlet("/appointment")
public class AppointmentServlet extends HttpServlet {
    private final AppointmentService appointmentService = new AppointmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!RoleGuard.allow(request, response, StaffAccessPolicy::canRegisterAppointments)) {
            return;
        }
        loadLookups(request);
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!RoleGuard.allow(request, response, StaffAccessPolicy::canRegisterAppointments)) {
            return;
        }
        StaffUser staff = (StaffUser) request.getSession().getAttribute("staffUser");
        try {
            Appointment appointment = appointmentService.register(
                    request.getParameter("patientName"),
                    request.getParameter("address"),
                    request.getParameter("contactNumber"),
                    request.getParameter("dentistId"),
                    request.getParameter("treatmentId"),
                    request.getParameter("appointmentDate"),
                    request.getParameter("appointmentTime"),
                    staff.getUserId()
            );
            request.setAttribute("success", "Appointment saved. Number: " + appointment.getAppointmentNumber());
            request.setAttribute("savedAppointment", appointment);
        } catch (ClinicException e) {
            request.setAttribute("error", e.getMessage());
            keepForm(request);
        } catch (RuntimeException e) {
            request.setAttribute("error", "Could not save the appointment. " + safeMessage(e));
            keepForm(request);
        }
        loadLookups(request);
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    private void loadLookups(HttpServletRequest request) {
        request.setAttribute("dentists", appointmentService.listDentists());
        request.setAttribute("treatments", appointmentService.listTreatments());
    }

    private void keepForm(HttpServletRequest request) {
        request.setAttribute("patientName", request.getParameter("patientName"));
        request.setAttribute("address", request.getParameter("address"));
        request.setAttribute("contactNumber", request.getParameter("contactNumber"));
        request.setAttribute("dentistId", request.getParameter("dentistId"));
        request.setAttribute("treatmentId", request.getParameter("treatmentId"));
        request.setAttribute("appointmentDate", request.getParameter("appointmentDate"));
        request.setAttribute("appointmentTime", request.getParameter("appointmentTime"));
    }

    private String safeMessage(Exception e) {
        String message = e.getMessage();
        if (message != null && message.toLowerCase().contains("double")) {
            return message;
        }
        if (message != null && message.contains("already has an appointment")) {
            return message;
        }
        return "Please check the details and try again.";
    }
}
