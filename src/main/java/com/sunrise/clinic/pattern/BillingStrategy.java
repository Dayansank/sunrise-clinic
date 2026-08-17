package com.sunrise.clinic.pattern;

import java.math.BigDecimal;

/**
 * Strategy for the bill total. Right now we only have one rule
 * (treatment + consultation), but I can add another class later without touching the servlet.
 */
public interface BillingStrategy {
    BigDecimal calculate(BigDecimal treatmentCost, BigDecimal consultationFee);
}
