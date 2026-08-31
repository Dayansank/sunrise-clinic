package com.sunrise.clinic.servlet;

import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.StaffUser;
import com.sunrise.clinic.pattern.StaffAccessPolicy;
import com.sunrise.clinic.service.StaffService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/staff")
public class StaffServlet extends HttpServlet {
    private final StaffService staffService = new StaffService();

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
        StaffUser admin = (StaffUser) request.getSession().getAttribute("staffUser");
        String action = request.getParameter("action");
        try {
            if ("delete".equals(action)) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                request.setAttribute("success", staffService.deleteStaff(userId, admin.getUserId()));
            } else {
                staffService.createStaff(
                        request.getParameter("username"),
                        request.getParameter("fullName"),
                        request.getParameter("password"),
                        request.getParameter("role")
                );
                request.setAttribute("success", "Staff account created.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Choose a valid staff account to delete.");
        } catch (ClinicException e) {
            request.setAttribute("error", e.getMessage());
        }
        showPage(request, response);
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("staffList", staffService.listStaff());
        request.getRequestDispatcher("/staff.jsp").forward(request, response);
    }
}
