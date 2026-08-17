package com.sunrise.clinic.pattern;

import java.math.BigDecimal;

/**
 * Our normal bill: treatment cost + consultation fee from clinic_settings.
 */
public class StandardBillingStrategy implements BillingStrategy {
    @Override
    public BigDecimal calculate(BigDecimal treatmentCost, BigDecimal consultationFee) {
        return treatmentCost.add(consultationFee);
    }
}
