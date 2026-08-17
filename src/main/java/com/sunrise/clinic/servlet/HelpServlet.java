package com.sunrise.clinic.servlet;

import com.sunrise.clinic.model.StaffUser;
import com.sunrise.clinic.service.HelpService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/help")
public class HelpServlet extends HttpServlet {
    private final HelpService helpService = new HelpService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StaffUser staff = (StaffUser) request.getSession().getAttribute("staffUser");
        request.setAttribute("steps", helpService.steps(staff));
        request.getRequestDispatcher("/help.jsp").forward(request, response);
    }
}
