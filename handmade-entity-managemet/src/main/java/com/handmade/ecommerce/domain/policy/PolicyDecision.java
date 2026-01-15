package com.handmade.ecommerce.domain.policy;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_policy_decision")
public class PolicyDecision extends BaseJpaEntity {

    @Column(name = "trace_id", length = 36, nullable = false, unique = true)
    private String traceId;

    @Column(name = "policy_key", length = 100, nullable = false)
    private String policyKey;

    @Column(name = "input_context_hash", length = 64)
    private String inputContextHash;

    @Column(name = "input_context_json", columnDefinition = "TEXT")
    private String inputContextJson;

    @Column(name = "final_decision", length = 50)
    private String finalDecision; // ALLOW, DENY

    @Column(name = "executing_service", length = 100)
    private String executingService;

    @Column(name = "decision_time")
    private Date decisionTime;
}
