package com.handmade.ecommerce.platform.risk.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_risk_signal
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_risk_signal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RiskSignal extends BaseJpaEntity {
    
    @Column(name = "entity_id", nullable = false, length = 36)
    private String entityId;
    @Column(name = "entity_type", nullable = false, length = 20)
    private String entityType;
    @Column(name = "signal_type", nullable = false, length = 50)
    private String signalType;
    @Column(name = "risk_score", nullable = false)
    private String riskScore;
    @Column(name = "evidence_data")
    private String evidenceData;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
