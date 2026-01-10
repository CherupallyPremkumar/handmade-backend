package com.handmade.ecommerce.promotion.loyalty.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_referral_program
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_referral_program")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ReferralProgram extends BaseJpaEntity {
    
    @Column(name = "referrer_id", nullable = false, length = 36)
    private String referrerId;
    @Column(name = "referral_code", nullable = false, length = 50, unique = true)
    private String referralCode;
    @Column(name = "referral_status", length = 50)
    private String referralStatus;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
