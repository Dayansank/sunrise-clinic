package com.sunrise.clinic.servlet;

import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.Bill;
import com.sunrise.clinic.pattern.ConsultationFeeConfig;
import com.sunrise.clinic.pattern.StaffAccessPolicy;
import com.sunrise.clinic.service.BillingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/bill")
public class BillServlet extends HttpServlet {
    private final BillingService billingService = new BillingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!RoleGuard.allow(request, response, StaffAccessPolicy::canCreateBills)) {
            return;
        }
        request.setAttribute("consultationFee", ConsultationFeeConfig.getInstance().getFee());
        request.getRequestDispatcher("/bill.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!RoleGuard.allow(request, response, StaffAccessPolicy::canCreateBills)) {
            return;
        }
        request.setAttribute("consultationFee", ConsultationFeeConfig.getInstance().getFee());
        String number = request.getParameter("appointmentNumber");
        request.setAttribute("appointmentNumber", number);
        try {
            Bill bill = billingService.createBill(number);
            request.setAttribute("bill", bill);
            request.setAttribute("success", "Bill calculated for " + bill.getAppointment().getAppointmentNumber());
        } catch (ClinicException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/bill.jsp").forward(request, response);
    }
}
