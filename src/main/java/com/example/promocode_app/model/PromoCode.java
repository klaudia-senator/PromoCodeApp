package com.example.promocode_app.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PROMO_CODES")
public class PromoCode {
    @Id
    @Column(name = "CODE")
    private String code;
    @Column(name = "EXPIRATION_DATE", nullable = false)
    private LocalDate expirationDate;
    @Column(name = "DISCOUNT", nullable = false)
    private BigDecimal discount;
    @Column(name = "CURRENCY", nullable = false)
    private String currency;
    @Column(name = "MAX_USAGE", nullable = false)
    private Integer maxUsage;
    @Column(name = "USAGE")
    private Integer usage = 0;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(Integer maxUsage) {
        this.maxUsage = maxUsage;
    }

    public Integer getUsage() {
        return usage;
    }

    public void setUsage(Integer usage) {
        this.usage = usage;
    }
}
