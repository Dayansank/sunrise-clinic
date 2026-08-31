package com.sunrise.clinic.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        boolean patient = session != null && session.getAttribute("patientUser") != null;
        if (session != null) {
            session.invalidate();
        }
        if (patient) {
            response.sendRedirect(request.getContextPath() + "/patient-login");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
