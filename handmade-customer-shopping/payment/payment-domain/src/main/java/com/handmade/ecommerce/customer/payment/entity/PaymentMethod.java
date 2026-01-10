package com.handmade.ecommerce.customer.payment.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_payment_method
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_payment_method")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PaymentMethod extends BaseJpaEntity {
    
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "method_type", nullable = false, length = 50)
    private String methodType;
    @Column(name = "provider", length = 100)
    private String provider;
    @Column(name = "provider_method_id", length = 255)
    private String providerMethodId;
    @Column(name = "card_brand", length = 50)
    private String cardBrand;
    @Column(name = "card_last_four", length = 4)
    private String cardLastFour;
    @Column(name = "card_expiry_month")
    private String cardExpiryMonth;
    @Column(name = "card_expiry_year")
    private String cardExpiryYear;
    @Column(name = "cardholder_name", length = 255)
    private String cardholderName;
    @Column(name = "billing_address_id", length = 36)
    private String billingAddressId;
    @Column(name = "bank_account_last_four", length = 4)
    private String bankAccountLastFour;
    @Column(name = "bank_name", length = 255)
    private String bankName;
    @Column(name = "bank_account_type", length = 50)
    private String bankAccountType;
    @Column(name = "is_default")
    private Boolean isDefault;
    @Column(name = "is_verified")
    private Boolean isVerified;
    @Column(name = "verified_at")
    private Date verifiedAt;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "deactivated_at")
    private Date deactivatedAt;
    @Column(name = "deactivation_reason", length = 500)
    private String deactivationReason;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
