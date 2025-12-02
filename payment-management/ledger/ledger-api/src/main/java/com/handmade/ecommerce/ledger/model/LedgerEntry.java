package com.handmade.ecommerce.ledger.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "hm_ledger_entry")
public class LedgerEntry extends BaseJpaEntity {
    private String transactionId;
    private String paymentOrderId;
    private String accountId;
    private String accountType; // BUYER, SELLER, PLATFORM
    private String entryType; // DEBIT, CREDIT
    private BigDecimal amount;
    private String currency;
    private String description;


}