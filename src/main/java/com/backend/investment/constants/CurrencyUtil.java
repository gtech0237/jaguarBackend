package com.backend.investment.constants;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public final class CurrencyUtil {

    /**
     * Fixed Exchange Rate
     * 1 USDT = ₹100
     */
    public static final BigDecimal USDT_RATE =
            BigDecimal.valueOf(100);

    private CurrencyUtil() {
    }

    /**
     * INR -> USDT
     *
     * Example:
     * ₹1000 -> 10.00 USDT
     */
    public static BigDecimal inrToUsdt(BigDecimal inr) {

        return inr.divide(
                USDT_RATE,
                2,
                RoundingMode.HALF_UP
        );
    }

    /**
     * USDT -> INR
     *
     * Example:
     * 10 USDT -> ₹1000
     */
    public static BigDecimal usdtToInr(BigDecimal usdt) {

        return usdt.multiply(USDT_RATE);
    }

}