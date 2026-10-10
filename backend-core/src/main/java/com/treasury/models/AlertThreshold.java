package com.treasury.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "alert_thresholds")
public class AlertThreshold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entity;
    private String region;
    private String currency;

    @Column(name = "threshold_amount")
    private BigDecimal thresholdAmount;

    // Default Constructor
    public AlertThreshold() {
    }

    // All-args Constructor
    public AlertThreshold(Long id, String entity, String region, String currency, BigDecimal thresholdAmount) {
        this.id = id;
        this.entity = entity;
        this.region = region;
        this.currency = currency;
        this.thresholdAmount = thresholdAmount;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEntity() { return entity; }
    public void setEntity(String entity) { this.entity = entity; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public BigDecimal getThresholdAmount() { return thresholdAmount; }
    public void setThresholdAmount(BigDecimal thresholdAmount) { this.thresholdAmount = thresholdAmount; }
}