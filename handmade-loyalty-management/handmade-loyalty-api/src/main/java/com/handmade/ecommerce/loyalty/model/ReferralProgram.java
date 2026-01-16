package com.handmade.ecommerce.loyalty.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_referral_program")
public class ReferralProgram extends BaseJpaEntity {

    @Column(name = "referrer_id", length = 36, nullable = false)
    private String referrerId;

    @Column(name = "referral_code", length = 50, nullable = false, unique = true)
    private String referralCode;

    @Column(name = "referral_status", length = 50)
    private String referralStatus; // ACTIVE, EXPIRED
}
