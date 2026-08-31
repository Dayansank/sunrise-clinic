package com.sunrise.clinic.pattern;

import java.math.BigDecimal;

/** Treatment cost plus the consultation fee. */
public class StandardBillingStrategy implements BillingStrategy {
    @Override
    public BigDecimal calculate(BigDecimal treatmentCost, BigDecimal consultationFee) {
        return treatmentCost.add(consultationFee);
    }
}
