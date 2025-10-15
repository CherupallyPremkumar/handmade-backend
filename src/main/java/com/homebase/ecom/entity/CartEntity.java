package com.homebase.ecom.entity;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "hm_cart")
public class CartEntity extends AbstractJpaStateEntity {

    @Column(name = "customer_id")
    private String customerId;

    @Column(name="txn", precision = 10, scale = 2)
    private BigDecimal txn = BigDecimal.ZERO;

    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(name = "transaction_amount", precision = 10, scale = 2)
    private BigDecimal transactionAmount = BigDecimal.ZERO;


    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String customerId() {
        return customerId;
    }

    public CartEntity setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }



    public BigDecimal discount() {
        return discount;
    }

    public BigDecimal totalAmount() {
        return totalAmount;
    }

    public CartEntity setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public BigDecimal txn() {
        return txn;
    }

    public CartEntity setTxn(BigDecimal txn) {
        this.txn = txn;
        return this;
    }

    public BigDecimal taxAmount() {
        return taxAmount;
    }

    public CartEntity setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public BigDecimal transactionAmount() {
        return transactionAmount;
    }

    public CartEntity setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }
}
