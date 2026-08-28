package com.sunrise.clinic.pattern;

import java.math.BigDecimal;

public interface BillingStrategy {
    BigDecimal calculate(BigDecimal treatmentCost, BigDecimal consultationFee);
}
