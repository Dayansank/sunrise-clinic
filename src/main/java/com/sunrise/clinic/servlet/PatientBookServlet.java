package com.sunrise.clinic.servlet;

import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.model.Patient;
import com.sunrise.clinic.service.AppointmentService;
import com.sunrise.clinic.service.SlotService;
import com.sunrise.clinic.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Patient books a slot. Live times come from /api/slots so two people cannot take the same chair.
 */
@WebServlet("/patient-book")
public class PatientBookServlet extends HttpServlet {
    private final AppointmentService appointmentService = new AppointmentService();
    private final SlotService slotService = new SlotService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        loadForm(request);
        request.getRequestDispatcher("/patient-book.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Patient patient = (Patient) request.getSession().getAttribute("patientUser");
        try {
            Appointment appointment = appointmentService.bookForPatient(
                    patient,
                    request.getParameter("dentistId"),
                    request.getParameter("treatmentId"),
                    request.getParameter("appointmentDate"),
                    request.getParameter("appointmentTime")
            );
            com.sunrise.clinic.pattern.Confirmation confirmation =
                    new com.sunrise.clinic.pattern.QrConfirmationDecorator(
                            new com.sunrise.clinic.pattern.BasicConfirmation(appointment), appointment);
            request.setAttribute("success", confirmation.message());
            request.setAttribute("savedAppointment", appointment);
            request.setAttribute("qrImage", confirmation.qrDataUri());
        } catch (ClinicException e) {
            request.setAttribute("error", e.getMessage());
        }
        loadForm(request);
        request.getRequestDispatcher("/patient-book.jsp").forward(request, response);
    }

    private void loadForm(HttpServletRequest request) {
        request.setAttribute("dentists", appointmentService.listDentists());
        request.setAttribute("treatments", appointmentService.listTreatments());
        String repeatId = request.getParameter("repeat");
        if (repeatId != null && !repeatId.isBlank()) {
            com.sunrise.clinic.model.Appointment original = appointmentService.findById(Integer.parseInt(repeatId));
            if (original != null) {
                com.sunrise.clinic.model.Appointment prototype = original.cloneForRebook();
                request.setAttribute("dentistId", String.valueOf(prototype.getDentist().getDentistId()));
                request.setAttribute("treatmentId", String.valueOf(prototype.getTreatment().getTreatmentId()));
            }
        }
        String dentistId = request.getParameter("dentistId");
        if (dentistId == null) {
            dentistId = (String) request.getAttribute("dentistId");
        }
        String dateText = request.getParameter("appointmentDate");
        LocalDate date = ValidationUtil.parseDate(dateText);
        if (dentistId != null && !dentistId.isBlank() && date != null) {
            request.setAttribute("openSlots", slotService.availableSlots(Integer.parseInt(dentistId), date));
        } else {
            request.setAttribute("openSlots", slotService.allClinicSlots());
        }
    }
}
