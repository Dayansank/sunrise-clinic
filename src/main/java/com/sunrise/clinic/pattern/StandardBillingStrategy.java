package com.sunrise.clinic.pattern;

import java.math.BigDecimal;

public class StandardBillingStrategy implements BillingStrategy {
    @Override
    public BigDecimal calculate(BigDecimal treatmentCost, BigDecimal consultationFee) {
        return treatmentCost.add(consultationFee);
    }
}
