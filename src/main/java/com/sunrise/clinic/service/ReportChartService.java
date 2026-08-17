package com.sunrise.clinic.service;

import com.sunrise.clinic.model.Appointment;
import com.sunrise.clinic.util.JsonUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportChartService {
    private final AppointmentService appointmentService;
    private final BillingService billingService;

    public ReportChartService() {
        this(new AppointmentService(), new BillingService());
    }

    public ReportChartService(AppointmentService appointmentService, BillingService billingService) {
        this.appointmentService = appointmentService;
        this.billingService = billingService;
    }

    public String statusDoughnutJson(LocalDate date) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        counts.put("BOOKED", 0);
        counts.put("COMPLETED", 0);
        counts.put("CANCELLED", 0);
        for (Appointment appointment : appointmentService.findByDate(date)) {
            counts.merge(appointment.getStatus(), 1, Integer::sum);
        }
        return JsonUtil.gson().toJson(Map.of(
                "labels", new ArrayList<>(counts.keySet()),
                "values", new ArrayList<>(counts.values())
        ));
    }

    public String incomeBarJson() {
        List<String> labels = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();
        for (Map<String, Object> row : billingService.incomeByDentist()) {
            labels.add(String.valueOf(row.get("dentistName")));
            values.add((BigDecimal) row.get("totalIncome"));
        }
        return JsonUtil.gson().toJson(Map.of("labels", labels, "values", values));
    }

    public String weeklyLineJson(LocalDate endDate) {
        List<String> labels = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate day = endDate.minusDays(i);
            labels.add(day.toString());
            values.add(appointmentService.findByDate(day).size());
        }
        return JsonUtil.gson().toJson(Map.of("labels", labels, "values", values));
    }
}
