package com.handmade.ecommerce.domain.policy;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_policy_definition")
public class PolicyDefinition extends AbstractJpaStateEntity {

    @Column(name = "policy_key", length = 100, nullable = false, unique = true)
    private String policyKey;

    @Column(name = "policy_name", nullable = false)
    private String policyName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "default_effect", length = 50)
    private String defaultEffect = "DENY"; // ALLOW, DENY

    @Column(name = "owner_service", length = 100)
    private String ownerService;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
