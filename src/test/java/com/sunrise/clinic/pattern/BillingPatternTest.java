package com.sunrise.clinic.pattern;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class BillingPatternTest {

    @Test
    void consultationFeeConfigIsSingleton() {
        assertSame(ConsultationFeeConfig.getInstance(), ConsultationFeeConfig.getInstance());
    }

    @Test
    void standardStrategyAddsConsultationFee() {
        BigDecimal total = new StandardBillingStrategy()
                .calculate(new BigDecimal("8000.00"), new BigDecimal("1500.00"));
        assertEquals(new BigDecimal("9500.00"), total);
    }
}
