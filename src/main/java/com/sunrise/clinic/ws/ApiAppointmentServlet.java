package com.sunrise.clinic.ws;

import com.sunrise.clinic.dto.ApiResponse;
import com.sunrise.clinic.dto.AppointmentRequest;
import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.model.StaffUser;
import com.sunrise.clinic.service.AppointmentService;
import com.sunrise.clinic.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/api/appointments/*")
public class ApiAppointmentServlet extends HttpServlet {
    private final AppointmentService appointmentService = new AppointmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String number = pathNumber(request);
        if (number == null || number.isBlank()) {
            JsonUtil.write(response, 400, ApiResponse.fail("Appointment number is required. Use /api/appointments/APT-2026-0001"));
            return;
        }
        try {
            Appointment appointment = appointmentService.findByNumber(number);
            JsonUtil.write(response, 200, ApiResponse.ok("Appointment found.", toMap(appointment)));
        } catch (ClinicException e) {
            JsonUtil.write(response, 404, ApiResponse.fail(e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            AppointmentRequest body = JsonUtil.read(request, AppointmentRequest.class);
            StaffUser staff = (StaffUser) request.getSession().getAttribute("staffUser");
            Appointment appointment = appointmentService.register(
                    body.getPatientName(),
                    body.getAddress(),
                    body.getContactNumber(),
                    String.valueOf(body.getDentistId()),
                    String.valueOf(body.getTreatmentId()),
                    body.getAppointmentDate(),
                    body.getAppointmentTime(),
                    staff.getUserId()
            );
            JsonUtil.write(response, 201, ApiResponse.ok("Appointment registered.", toMap(appointment)));
        } catch (ClinicException e) {
            JsonUtil.write(response, 400, ApiResponse.fail(e.getMessage()));
        } catch (Exception e) {
            JsonUtil.write(response, 400, ApiResponse.fail("Could not register appointment."));
        }
    }

    private String pathNumber(HttpServletRequest request) {
        String path = request.getPathInfo();
        if (path == null || path.equals("/")) {
            return request.getParameter("number");
        }
        return path.substring(1);
    }

    private Map<String, Object> toMap(Appointment appointment) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("appointmentNumber", appointment.getAppointmentNumber());
        map.put("patientName", appointment.getPatient().getName());
        map.put("address", appointment.getPatient().getAddress());
        map.put("contactNumber", appointment.getPatient().getContactNumber());
        map.put("dentistName", appointment.getDentist().getName());
        map.put("treatmentType", appointment.getTreatment().getTypeName());
        map.put("appointmentDate", appointment.getAppointmentDate().toString());
        map.put("appointmentTime", appointment.getAppointmentTime().toString());
        map.put("status", appointment.getStatus());
        return map;
    }
}
