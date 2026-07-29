package com.grocery.portal;

import java.math.BigDecimal;

public enum DeliveryOption {
    PICKUP(new BigDecimal("0.00")),
    STANDARD(new BigDecimal("5.99")),
    EXPRESS(new BigDecimal("12.99"));

    private final BigDecimal fee;

    DeliveryOption(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getDeliveryFee() {
        return fee;
    }
}