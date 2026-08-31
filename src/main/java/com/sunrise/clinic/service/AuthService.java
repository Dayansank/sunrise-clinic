package com.sunrise.clinic.service;

import com.sunrise.clinic.dao.UserDAO;
import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.StaffUser;
import com.sunrise.clinic.util.PasswordUtil;
import com.sunrise.clinic.util.ValidationUtil;

/**
 * Checks staff username and password. Passwords are stored as SHA-256 with a salt.
 */
public class AuthService {
    private final UserDAO userDAO;

    public AuthService() {
        this(new UserDAO());
    }

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public StaffUser login(String username, String password) {
        if (ValidationUtil.isBlank(username) || ValidationUtil.isBlank(password)) {
            throw new ClinicException("Username and password are required.");
        }
        String hash = userDAO.findPasswordHash(username.trim());
        if (hash == null || !PasswordUtil.matches(password, hash)) {
            throw new ClinicException("Invalid username or password.");
        }
        StaffUser user = userDAO.findByUsername(username.trim());
        if (user == null || !user.isActive()) {
            throw new ClinicException("This staff account is not active.");
        }
        return user;
    }
}
