package com.sunrise.clinic.ws;

import com.sunrise.clinic.dto.ApiResponse;
import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.Bill;
import com.sunrise.clinic.service.BillingService;
import com.sunrise.clinic.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/api/bills/*")
public class ApiBillServlet extends HttpServlet {
    private final BillingService billingService = new BillingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handle(request, response, false);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handle(request, response, true);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response, boolean create) throws IOException {
        String path = request.getPathInfo();
        String number = path == null || path.equals("/") ? request.getParameter("number") : path.substring(1);
        if (number == null || number.isBlank()) {
            JsonUtil.write(response, 400, ApiResponse.fail("Appointment number is required."));
            return;
        }
        try {
            Bill bill = create ? billingService.createBill(number) : billingService.findBill(number);
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("appointmentNumber", bill.getAppointment().getAppointmentNumber());
            map.put("patientName", bill.getAppointment().getPatient().getName());
            map.put("treatmentType", bill.getAppointment().getTreatment().getTypeName());
            map.put("consultationFee", bill.getConsultationFee());
            map.put("treatmentCost", bill.getTreatmentCost());
            map.put("totalAmount", bill.getTotalAmount());
            JsonUtil.write(response, 200, ApiResponse.ok("Bill ready.", map));
        } catch (ClinicException e) {
            JsonUtil.write(response, 400, ApiResponse.fail(e.getMessage()));
        }
    }
}
