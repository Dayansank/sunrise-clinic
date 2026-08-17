package com.sunrise.clinic.filter;

import com.sunrise.clinic.model.Patient;
import com.sunrise.clinic.model.StaffUser;
import com.sunrise.clinic.pattern.StaffAccessPolicy;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Security filter for every URL. Guests can only open the public pages.
 * Patients stay in the patient area. Staff are split: reception desk vs admin office.
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest http = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache");
        String path = normalize(http.getRequestURI(), http.getContextPath());

        if (isPublic(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = http.getSession(false);
        StaffUser staff = session == null ? null : (StaffUser) session.getAttribute("staffUser");
        Patient patient = session == null ? null : (Patient) session.getAttribute("patientUser");

        if (path.startsWith("/qr")) {
            if (staff == null && patient == null) {
                httpResponse.sendRedirect(http.getContextPath() + "/patient-login");
                return;
            }
            chain.doFilter(request, response);
            return;
        }

        if (isPatientArea(path)) {
            if (patient == null) {
                httpResponse.sendRedirect(http.getContextPath() + "/patient-login");
                return;
            }
            chain.doFilter(request, response);
            return;
        }

        if (staff == null) {
            if (path.startsWith("/api/")) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType("application/json");
                httpResponse.getWriter().write("{\"success\":false,\"message\":\"Please log in.\"}");
                return;
            }
            httpResponse.sendRedirect(http.getContextPath() + "/login.jsp");
            return;
        }

        StaffAccessPolicy access = StaffAccessPolicy.forUser(staff);
        String method = http.getMethod();
        if (isWalkInDesk(path, method) && !access.canRegisterAppointments()) {
            forbid(http, httpResponse, path, "Reception desk only.");
            return;
        }
        if (isBillingDesk(path, method) && !access.canCreateBills()) {
            forbid(http, httpResponse, path, "Reception billing only.");
            return;
        }
        if (isAdminOffice(path) && !access.canViewReports() && !access.canManageStaff()) {
            forbid(http, httpResponse, path, "Admin only.");
            return;
        }
        if (path.startsWith("/patterns")) {
            httpResponse.sendRedirect(http.getContextPath() + "/menu.jsp");
            return;
        }
        chain.doFilter(request, response);
    }

    private void forbid(HttpServletRequest http, HttpServletResponse httpResponse, String path, String message)
            throws IOException {
        if (path.startsWith("/api/")) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"success\":false,\"message\":\"" + message + "\"}");
            return;
        }
        httpResponse.sendRedirect(http.getContextPath() + "/menu.jsp");
    }

    private String normalize(String uri, String contextPath) {
        String path = uri.substring(contextPath.length());
        int semi = path.indexOf(';');
        if (semi >= 0) {
            path = path.substring(0, semi);
        }
        if (path.length() > 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    private boolean isPublic(String path) {
        return path.equals("/")
                || path.equals("/index.jsp")
                || path.startsWith("/css/")
                || path.startsWith("/images/")
                || path.equals("/login")
                || path.equals("/login.jsp")
                || path.startsWith("/patient-login")
                || path.startsWith("/patient-register")
                || path.startsWith("/api/auth/login")
                || path.startsWith("/api/slots");
    }

    private boolean isPatientArea(String path) {
        return path.startsWith("/patient-home")
                || path.startsWith("/patient-book")
                || path.startsWith("/patient-appointments")
                || path.startsWith("/patient-bills")
                || path.startsWith("/patient-cancel");
    }

    private boolean isWalkInDesk(String path, String method) {
        return path.startsWith("/appointment")
                || path.equals("/register.jsp")
                || (path.startsWith("/api/appointments") && "POST".equalsIgnoreCase(method));
    }

    private boolean isBillingDesk(String path, String method) {
        return path.startsWith("/bill")
                || path.equals("/bill.jsp")
                || (path.startsWith("/api/bills") && "POST".equalsIgnoreCase(method));
    }

    private boolean isAdminOffice(String path) {
        return path.startsWith("/reports")
                || path.startsWith("/staff")
                || path.startsWith("/catalog");
    }
}
