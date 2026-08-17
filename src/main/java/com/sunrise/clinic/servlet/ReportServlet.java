package com.sunrise.clinic.servlet;

import com.sunrise.clinic.pattern.StaffAccessPolicy;
import com.sunrise.clinic.service.AppointmentService;
import com.sunrise.clinic.service.BillingService;
import com.sunrise.clinic.service.ReportChartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/reports")
public class ReportServlet extends HttpServlet {
    private final AppointmentService appointmentService = new AppointmentService();
    private final BillingService billingService = new BillingService();
    private final ReportChartService chartService = new ReportChartService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!RoleGuard.allow(request, response, StaffAccessPolicy::canViewReports)) {
            return;
        }
        LocalDate date = LocalDate.now();
        String dateText = request.getParameter("date");
        if (dateText != null && !dateText.isBlank()) {
            date = LocalDate.parse(dateText);
        }
        request.setAttribute("reportDate", date);
        request.setAttribute("dailyAppointments", appointmentService.findByDate(date));
        request.setAttribute("incomeRows", billingService.incomeByDentist());
        request.setAttribute("statusChartJson", chartService.statusDoughnutJson(date));
        request.setAttribute("incomeChartJson", chartService.incomeBarJson());
        request.setAttribute("weeklyChartJson", chartService.weeklyLineJson(date));
        request.getRequestDispatcher("/reports.jsp").forward(request, response);
    }
}
