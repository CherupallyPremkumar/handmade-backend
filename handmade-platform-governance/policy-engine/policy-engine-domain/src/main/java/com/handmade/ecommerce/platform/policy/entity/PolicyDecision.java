package com.handmade.ecommerce.platform.policy.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_policy_decision
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_policy_decision")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PolicyDecision extends BaseJpaEntity {
    
    @Column(name = "trace_id", nullable = false, length = 36, unique = true)
    private String traceId;
    @Column(name = "policy_key", nullable = false, length = 100)
    private String policyKey;
    @Column(name = "input_context_hash", length = 64)
    private String inputContextHash;
    @Column(name = "input_context_json")
    private String inputContextJson;
    @Column(name = "final_decision", length = 50)
    private String finalDecision;
    @Column(name = "executing_service", length = 100)
    private String executingService;
    @Column(name = "decision_time")
    private Date decisionTime;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
