package com.sunrise.clinic.ws;

import com.sunrise.clinic.dto.ApiResponse;
import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.StaffUser;
import com.sunrise.clinic.service.AuthService;
import com.sunrise.clinic.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

@WebServlet("/api/auth/login")
public class ApiAuthServlet extends HttpServlet {
    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Map<?, ?> body = JsonUtil.read(request, Map.class);
            String username = body.get("username") == null ? "" : String.valueOf(body.get("username"));
            String password = body.get("password") == null ? "" : String.valueOf(body.get("password"));
            StaffUser user = authService.login(username, password);
            HttpSession session = request.getSession(true);
            session.setAttribute("staffUser", user);
            JsonUtil.write(response, 200, ApiResponse.ok("Login successful.", Map.of(
                    "username", user.getUsername(),
                    "fullName", user.getFullName(),
                    "role", user.getRole()
            )));
        } catch (ClinicException e) {
            JsonUtil.write(response, 401, ApiResponse.fail(e.getMessage()));
        } catch (Exception e) {
            JsonUtil.write(response, 400, ApiResponse.fail("Invalid login request."));
        }
    }
}
