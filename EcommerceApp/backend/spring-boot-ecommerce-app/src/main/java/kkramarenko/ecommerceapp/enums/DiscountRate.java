package kkramarenko.ecommerceapp.enums;

import java.math.BigDecimal;

public enum DiscountRate {
    STARTER(BigDecimal.valueOf(0.00)),
    BRONZE(BigDecimal.valueOf(100.00)),
    SILVER(BigDecimal.valueOf(250.00)),
    GOLD(BigDecimal.valueOf(500.00)),
    PLATINUM(BigDecimal.valueOf(1000.00));

    private final BigDecimal targetPurchasesSum;

    DiscountRate(BigDecimal targetPurchasesSum) {
        this.targetPurchasesSum = targetPurchasesSum;
    }

    public BigDecimal getTargetPurchasesSum() {
        return targetPurchasesSum;
    }
}
