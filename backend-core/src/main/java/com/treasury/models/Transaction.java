package com.treasury.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entity;
    private String currency;

    @Column(name = "original_amount")
    private BigDecimal amount;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    private String direction;

    @Column(name = "product_type")
    private String productType;

    // Default Constructor
    public Transaction() {
    }

    // All-Args Constructor
    public Transaction(Long id, String entity, String currency, BigDecimal amount, LocalDate transactionDate, String direction, String productType) {
        this.id = id;
        this.entity = entity;
        this.currency = currency;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.direction = direction;
        this.productType = productType;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEntity() { return entity; }
    public void setEntity(String entity) { this.entity = entity; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }

    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }
}