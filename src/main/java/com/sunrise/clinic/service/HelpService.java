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
                "Log in with the reception account. This desk handles walk-in patients only.",
                "Choose Register New Appointment to add a patient, dentist, treatment, date and time.",
                "The system creates a unique appointment number such as APT-2026-0001 and blocks double bookings.",
                "Use Display Appointment Details to search by appointment number and show the QR ticket.",
                "After treatment, use Calculate and Print Bill. Total = treatment cost + the consultation fee stored in the database.",
                "Patients can also book online. Those visits still appear when you search the appointment number.",
                "Reception cannot open clinic income reports. Ask an admin for that.",
                "Click Exit to end the session safely."
        );
    }

    private List<String> adminSteps() {
        return List.of(
                "Log in with the admin account. This portal is for clinic management, not the walk-in desk.",
                "Open Staff accounts to add or delete admin and reception logins.",
                "Open Clinic catalogue to add dentists and treatments. Those names appear on booking pages straight away.",
                "The consultation fee is read from the database, so a change in clinic_settings shows on new bills.",
                "You cannot delete your own account, and the clinic must keep at least one admin.",
                "Open Reports to see today’s appointments, status charts and income by dentist.",
                "Use Display Appointment Details to look up any visit by appointment number.",
                "Admin cannot register walk-in appointments or print bills. Reception does that work.",
                "Click Exit to end the session safely."
        );
    }
}
