package com.handmade.ecommerce.analytics.ranking.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_ranking_signal
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_ranking_signal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RankingSignal extends BaseJpaEntity {
    
    @Column(name = "entity_id", nullable = false, length = 36)
    private String entityId;
    @Column(name = "signal_type", length = 50)
    private String signalType;
    @Column(name = "signal_weight", precision = 10, scale = 4)
    private BigDecimal signalWeight;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
