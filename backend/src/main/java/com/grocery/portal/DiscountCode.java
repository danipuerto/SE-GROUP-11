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
    private BigDecimal percentage;
    private boolean active;

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public boolean isActive() {
        return active;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
