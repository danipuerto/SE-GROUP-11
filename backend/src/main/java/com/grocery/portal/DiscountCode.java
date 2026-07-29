package com.grocery.portal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.math.BigDecimal;
@Entity
public class DiscountCode {
    @Id
    @GeneratedValue
    private int id;

    private String code;
    private String discountType;
    private BigDecimal value;
    private boolean active;

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDiscountType() {
        return discountType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPercentage(BigDecimal percentage) {
        this.value = percentage;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
