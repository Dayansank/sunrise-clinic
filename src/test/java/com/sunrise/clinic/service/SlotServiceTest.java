package com.sunrise.clinic.service;

import com.sunrise.clinic.dao.AppointmentDAO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SlotServiceTest {

    @Test
    void clinicSlotsRunFromNineToHalfFour() {
        List<LocalTime> slots = new SlotService(mock(AppointmentDAO.class)).allClinicSlots();
        assertEquals(LocalTime.of(9, 0), slots.get(0));
        assertEquals(LocalTime.of(16, 30), slots.get(slots.size() - 1));
        assertEquals(16, slots.size());
    }

    @Test
    void hidesTakenSlot() {
        AppointmentDAO dao = mock(AppointmentDAO.class);
        LocalDate date = LocalDate.of(2026, 9, 1);
        when(dao.isSlotTaken(1, date, LocalTime.of(10, 0))).thenReturn(true);
        List<LocalTime> open = new SlotService(dao).availableSlots(1, date);
        assertFalse(open.contains(LocalTime.of(10, 0)));
        assertTrue(open.contains(LocalTime.of(10, 30)));
    }
}
