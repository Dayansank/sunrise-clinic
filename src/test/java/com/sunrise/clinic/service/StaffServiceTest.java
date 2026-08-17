package com.sunrise.clinic.service;

import com.sunrise.clinic.dao.UserDAO;
import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.AdminUser;
import com.sunrise.clinic.model.ReceptionUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StaffServiceTest {

    @Mock
    private UserDAO userDAO;

    @Test
    void createsReceptionAccount() {
        when(userDAO.findByUsername("desk2")).thenReturn(null);
        new StaffService(userDAO).createStaff("desk2", "Samanthi Jayasuriya", "Staff@123", "reception");
        verify(userDAO).insert(eq("desk2"), anyString(), eq("Samanthi Jayasuriya"), eq("RECEPTION"));
    }

    @Test
    void rejectsDuplicateUsername() {
        when(userDAO.findByUsername("admin")).thenReturn(new AdminUser());
        StaffService service = new StaffService(userDAO);
        assertThrows(ClinicException.class, () -> service.createStaff("admin", "Nadeesha Perera", "Admin@123", "ADMIN"));
        verify(userDAO, never()).insert(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void cannotDeleteOwnAccount() {
        StaffService service = new StaffService(userDAO);
        ClinicException error = assertThrows(ClinicException.class, () -> service.deleteStaff(1, 1));
        assertEquals("You cannot delete your own account while logged in.", error.getMessage());
    }

    @Test
    void deletesReceptionWithNoAppointments() {
        ReceptionUser reception = new ReceptionUser();
        reception.setUserId(4);
        reception.setUsername("desk2");
        reception.setRole("RECEPTION");
        when(userDAO.findById(4)).thenReturn(reception);
        when(userDAO.countAppointmentsCreatedBy(4)).thenReturn(0);
        String message = new StaffService(userDAO).deleteStaff(4, 1);
        verify(userDAO).deleteById(4);
        assertEquals("desk2 was deleted.", message);
    }
}
