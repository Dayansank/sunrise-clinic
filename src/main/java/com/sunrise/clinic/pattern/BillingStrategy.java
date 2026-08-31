package com.sunrise.clinic.pattern;

import java.math.BigDecimal;

/** How the bill total is worked out. */
public interface BillingStrategy {
    BigDecimal calculate(BigDecimal treatmentCost, BigDecimal consultationFee);
}
