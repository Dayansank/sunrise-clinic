package com.sunrise.clinic.service;

import com.sunrise.clinic.model.StaffUser;
import com.sunrise.clinic.pattern.StaffAccessPolicy;

import java.util.List;

public class HelpService {

    public List<String> steps() {
        return receptionSteps();
    }

    public List<String> steps(StaffUser staff) {
        StaffAccessPolicy access = StaffAccessPolicy.forUser(staff);
        if (access.canViewReports()) {
            return adminSteps();
        }
        return receptionSteps();
    }

    private List<String> receptionSteps() {
        return List.of(
                "Login as reception.",
                "Register New Appointment - name, address, phone, dentist, treatment, date and time.",
                "It gives a number like APT-2026-0001. Same dentist and time cant be booked twice.",
                "Display Appointment Details - search with that number.",
                "After treatment open Calculate and Print Bill.",
                "Click Exit when you are done."
        );
    }

    private List<String> adminSteps() {
        return List.of(
                "Login as admin.",
                "Open Reports for the doughnut, bar and line charts.",
                "You can look up an appointment number on Display.",
                "Walk-in register and bills are for reception.",
                "Click Exit when you are done."
        );
    }
}
