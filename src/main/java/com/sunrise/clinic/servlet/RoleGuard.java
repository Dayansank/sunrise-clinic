package com.sunrise.clinic.servlet;

import com.sunrise.clinic.model.StaffUser;
import com.sunrise.clinic.pattern.StaffAccessPolicy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.function.Predicate;

final class RoleGuard {
    private RoleGuard() {
    }

    static boolean allow(HttpServletRequest request, HttpServletResponse response,
                         Predicate<StaffAccessPolicy> rule) throws IOException {
        StaffUser staff = (StaffUser) request.getSession().getAttribute("staffUser");
        StaffAccessPolicy access = StaffAccessPolicy.forUser(staff);
        if (staff == null || !rule.test(access)) {
            response.sendRedirect(request.getContextPath() + "/menu.jsp");
            return false;
        }
        return true;
    }
}
