package com.sunrise.clinic.service;

import com.sunrise.clinic.dao.AppointmentDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SlotService {
    private final AppointmentDAO appointmentDAO;

    public SlotService() {
        this(new AppointmentDAO());
    }

    public SlotService(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    public List<LocalTime> allClinicSlots() {
        List<LocalTime> slots = new ArrayList<>();
        LocalTime time = LocalTime.of(9, 0);
        LocalTime last = LocalTime.of(16, 30);
        while (!time.isAfter(last)) {
            slots.add(time);
            time = time.plusMinutes(30);
        }
        return slots;
    }

    public List<LocalTime> availableSlots(int dentistId, LocalDate date) {
        List<LocalTime> open = new ArrayList<>();
        for (LocalTime slot : allClinicSlots()) {
            if (!appointmentDAO.isSlotTaken(dentistId, date, slot)) {
                open.add(slot);
            }
        }
        return open;
    }
}
