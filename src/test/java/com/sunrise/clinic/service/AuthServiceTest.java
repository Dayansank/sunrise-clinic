package com.sunrise.clinic.service;

import com.sunrise.clinic.dao.UserDAO;
import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.StaffUser;
import com.sunrise.clinic.util.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserDAO userDAO;

    @Test
    void rejectsWrongPassword() {
        when(userDAO.findPasswordHash("admin")).thenReturn(PasswordUtil.hash("Admin@123"));
        AuthService service = new AuthService(userDAO);
        assertThrows(ClinicException.class, () -> service.login("admin", "wrong"));
    }

    @Test
    void acceptsValidStaff() {
        StaffUser user = new StaffUser();
        user.setUsername("admin");
        user.setFullName("Nadeesha Perera");
        user.setRole("ADMIN");
        user.setActive(true);
        when(userDAO.findPasswordHash("admin")).thenReturn(PasswordUtil.hash("Admin@123"));
        when(userDAO.findByUsername("admin")).thenReturn(user);

        StaffUser loggedIn = new AuthService(userDAO).login("admin", "Admin@123");
        assertEquals("ADMIN", loggedIn.getRole());
    }
}
