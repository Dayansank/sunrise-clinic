package com.sunrise.clinic.pattern;

import com.sunrise.clinic.dao.ClinicSettingsDAO;

import java.math.BigDecimal;

/** Consultation fee from clinic_settings. Next bill picks up a MySQL change. */
public final class ConsultationFeeConfig {
    private static final ConsultationFeeConfig INSTANCE = new ConsultationFeeConfig();
    private final ClinicSettingsDAO settingsDAO = new ClinicSettingsDAO();

    private ConsultationFeeConfig() {
    }

    public static ConsultationFeeConfig getInstance() {
        return INSTANCE;
    }

    public BigDecimal getFee() {
        return settingsDAO.findConsultationFee();
    }
}
