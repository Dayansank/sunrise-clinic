package com.sunrise.clinic.ws;

import com.sunrise.clinic.dto.ApiResponse;
import com.sunrise.clinic.service.SlotService;
import com.sunrise.clinic.util.JsonUtil;
import com.sunrise.clinic.util.ValidationUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/api/slots")
public class ApiSlotsServlet extends HttpServlet {
    private final SlotService slotService = new SlotService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String dentistId = request.getParameter("dentistId");
        LocalDate date = ValidationUtil.parseDate(request.getParameter("date"));
        if (dentistId == null || date == null) {
            JsonUtil.write(response, 400, ApiResponse.fail("dentistId and date are required."));
            return;
        }
        List<LocalTime> slots = slotService.availableSlots(Integer.parseInt(dentistId), date);
        JsonUtil.write(response, 200, ApiResponse.ok("Available slots.", slots));
    }
}
