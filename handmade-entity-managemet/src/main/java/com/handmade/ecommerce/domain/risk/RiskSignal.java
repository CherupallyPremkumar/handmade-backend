package com.handmade.ecommerce.domain.risk;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_risk_signal")
public class RiskSignal extends BaseJpaEntity {

    @Column(name = "entity_id", length = 36, nullable = false)
    private String entityId;

    @Column(name = "entity_type", length = 20, nullable = false)
    private String entityType;

    @Column(name = "signal_type", length = 50, nullable = false)
    private String signalType;

    @Column(name = "risk_score", nullable = false)
    private Integer riskScore;

    @Column(name = "evidence_data", columnDefinition = "TEXT")
    private String evidenceData;
}
