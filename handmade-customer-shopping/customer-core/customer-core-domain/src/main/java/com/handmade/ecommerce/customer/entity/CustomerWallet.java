package com.handmade.ecommerce.customer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_customer_wallet
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_customer_wallet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CustomerWallet extends BaseJpaEntity {
    
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "provider_token", length = 255)
    private String providerToken;
    @Column(name = "last_4_digits", length = 4)
    private String last4Digits;
    @Column(name = "card_brand", length = 50)
    private String cardBrand;
    @Column(name = "expiry_month")
    private String expiryMonth;
    @Column(name = "expiry_year")
    private String expiryYear;
    @Column(name = "is_default")
    private Boolean isDefault;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
