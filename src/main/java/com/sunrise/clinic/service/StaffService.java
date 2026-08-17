package com.sunrise.clinic.service;

import com.sunrise.clinic.dao.UserDAO;
import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.StaffUser;
import com.sunrise.clinic.util.PasswordUtil;
import com.sunrise.clinic.util.ValidationUtil;

import java.util.List;
import java.util.Locale;

/**
 * Create / delete admin and reception logins. Last admin cannot be removed.
 */
public class StaffService {
    private final UserDAO userDAO;

    public StaffService() {
        this(new UserDAO());
    }

    public StaffService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<StaffUser> listStaff() {
        return userDAO.findAll();
    }

    public void createStaff(String username, String fullName, String password, String role) {
        if (ValidationUtil.isBlank(username) || username.trim().length() < 3 || username.trim().length() > 50) {
            throw new ClinicException("Username must be between 3 and 50 characters.");
        }
        if (!username.trim().matches("[A-Za-z0-9._-]+")) {
            throw new ClinicException("Username may use letters, numbers, dot, underscore or hyphen only.");
        }
        if (!ValidationUtil.isValidName(fullName)) {
            throw new ClinicException("Full name must be between 3 and 100 characters.");
        }
        if (!ValidationUtil.isValidPassword(password)) {
            throw new ClinicException("Password must be at least 6 characters.");
        }
        String normalisedRole = role == null ? "" : role.trim().toUpperCase(Locale.ROOT);
        if (!normalisedRole.equals("ADMIN") && !normalisedRole.equals("RECEPTION")) {
            throw new ClinicException("Role must be Admin or Reception.");
        }
        if (userDAO.findByUsername(username.trim()) != null) {
            throw new ClinicException("That username is already in use.");
        }
        userDAO.insert(username.trim(), PasswordUtil.hash(password), fullName.trim(), normalisedRole);
    }

    public String deleteStaff(int targetId, int currentAdminId) {
        if (targetId == currentAdminId) {
            throw new ClinicException("You cannot delete your own account while logged in.");
        }
        StaffUser target = userDAO.findById(targetId);
        if (target == null) {
            throw new ClinicException("Staff account was not found.");
        }
        if ("ADMIN".equalsIgnoreCase(target.getRole()) && userDAO.countActiveAdmins() <= 1) {
            throw new ClinicException("The clinic must keep at least one active admin.");
        }
        if (userDAO.countAppointmentsCreatedBy(targetId) > 0) {
            userDAO.deactivate(targetId);
            return target.getUsername() + " had appointment records, so the login was disabled instead of deleted.";
        }
        try {
            userDAO.deleteById(targetId);
            return target.getUsername() + " was deleted.";
        } catch (IllegalStateException e) {
            userDAO.deactivate(targetId);
            return target.getUsername() + " could not be deleted, so the login was disabled.";
        }
    }
}
