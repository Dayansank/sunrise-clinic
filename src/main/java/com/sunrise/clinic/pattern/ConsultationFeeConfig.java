package com.sunrise.clinic.pattern;

import com.sunrise.clinic.dao.ClinicSettingsDAO;

import java.math.BigDecimal;

/**
 * Singleton for the consultation fee. I read it from clinic_settings every time
 * so if admin changes the fee in MySQL, the next bill uses the new amount.
 */
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
