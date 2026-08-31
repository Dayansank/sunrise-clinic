package com.sunrise.clinic.pattern;

import com.sunrise.clinic.model.AdminUser;
import com.sunrise.clinic.model.StaffUser;

/** What each staff role is allowed to do. */
public interface StaffAccessPolicy {
    boolean canRegisterAppointments();

    boolean canSearchAppointments();

    boolean canCreateBills();

    boolean canViewReports();

    boolean canManageStaff();

    static StaffAccessPolicy forUser(StaffUser user) {
        if (user instanceof AdminUser || (user != null && "ADMIN".equalsIgnoreCase(user.getRole()))) {
            return new AdminAccessPolicy();
        }
        return new ReceptionAccessPolicy();
    }
}
