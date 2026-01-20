package com.handmade.ecommerce.domain.governance;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_privacy_policy_ack")
public class PrivacyPolicyAck extends BaseJpaEntity {

    @Column(name = "user_id", length = 36, nullable = false)
    private String userId;

    @Column(name = "version_accepted", length = 50)
    private String versionAccepted;

    @Column(name = "accepted_at")
    private Date acceptedAt;

    @Column(name = "terms_type", length = 50)
    private String termsType; // PRIVACY_POLICY, TERMS_OF_SERVICE

    @Column(name = "policy_id", length = 50)
    private String policyId;

    @Column(name = "status", length = 20)
    private String status;
}
