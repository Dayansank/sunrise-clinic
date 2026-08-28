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
                "Login as reception. This desk is for walk-in patients.",
                "Register New Appointment - name, address, phone, dentist, treatment, date and time.",
                "It makes a number like APT-2026-0001. Same dentist/time cant be booked twice.",
                "Display Appointment Details - search by that number. QR shows if the visit is found.",
                "After treatment open Calculate and Print Bill. Total is treatment + consultation fee.",
                "Online bookings also show up when you search the number.",
                "Reception cant open income reports. Ask admin.",
                "Click Exit when you finish."
        );
    }

    private List<String> adminSteps() {
        return List.of(
                "Login as admin. This side is for office work, not the walk-in desk.",
                "Staff accounts - add or delete admin/reception users.",
                "Clinic catalogue - dentists and treatments. Booking pages pick them up after refresh.",
                "Consultation fee comes from clinic_settings in MySQL.",
                "Dont delete your own account. Need at least one admin.",
                "Reports - todays list, charts and income.",
                "Display Appointment Details still works for looking up a number.",
                "Admin cant register walk-ins or print the bill. Reception does that.",
                "Click Exit when you finish."
        );
    }
}
