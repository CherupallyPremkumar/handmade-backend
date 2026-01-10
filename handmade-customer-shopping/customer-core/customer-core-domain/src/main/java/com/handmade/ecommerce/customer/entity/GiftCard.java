package com.handmade.ecommerce.customer.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_gift_card
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_gift_card")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class GiftCard extends BaseJpaEntity {
    
    @Column(name = "customer_id", length = 36)
    private String customerId;
    @Column(name = "claim_code", nullable = false, length = 50, unique = true)
    private String claimCode;
    @Column(name = "initial_balance", precision = 19, scale = 4)
    private BigDecimal initialBalance;
    @Column(name = "current_balance", precision = 19, scale = 4)
    private BigDecimal currentBalance;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "expiry_date")
    private Date expiryDate;
    @Column(name = "status", length = 50)
    private String status;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
