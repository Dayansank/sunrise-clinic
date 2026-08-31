package com.sunrise.clinic.servlet;

import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.pattern.StaffAccessPolicy;
import com.sunrise.clinic.service.CatalogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {
    private final CatalogService catalogService = new CatalogService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!RoleGuard.allow(request, response, StaffAccessPolicy::canManageStaff)) {
            return;
        }
        showPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!RoleGuard.allow(request, response, StaffAccessPolicy::canManageStaff)) {
            return;
        }
        String action = request.getParameter("action");
        try {
            switch (action == null ? "" : action) {
                case "fee" -> {
                    catalogService.saveConsultationFee(request.getParameter("consultationFee"));
                    request.setAttribute("success", "Consultation fee updated. New bills will use this amount.");
                }
                case "add-dentist" -> {
                    catalogService.addDentist(request.getParameter("dentistName"), request.getParameter("specialization"));
                    request.setAttribute("success", "Dentist added. Booking pages will show the new name.");
                }
                case "delete-dentist" -> {
                    request.setAttribute("success", catalogService.deleteDentist(Integer.parseInt(request.getParameter("dentistId"))));
                }
                case "add-treatment" -> {
                    catalogService.addTreatment(request.getParameter("typeName"), request.getParameter("cost"));
                    request.setAttribute("success", "Treatment added. Booking pages will show the new type and cost.");
                }
                case "delete-treatment" -> {
                    request.setAttribute("success", catalogService.deleteTreatment(Integer.parseInt(request.getParameter("treatmentId"))));
                }
                default -> request.setAttribute("error", "Unknown action.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Choose a valid record to delete.");
        } catch (ClinicException e) {
            request.setAttribute("error", e.getMessage());
        }
        showPage(request, response);
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("dentists", catalogService.dentists());
        request.setAttribute("treatments", catalogService.treatments());
        request.setAttribute("consultationFee", catalogService.consultationFee());
        request.getRequestDispatcher("/catalog.jsp").forward(request, response);
    }
}
