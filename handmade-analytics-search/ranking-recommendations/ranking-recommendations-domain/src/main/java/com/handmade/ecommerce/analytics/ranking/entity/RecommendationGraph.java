package com.handmade.ecommerce.analytics.ranking.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_recommendation_graph
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_recommendation_graph")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RecommendationGraph extends BaseJpaEntity {
    
    @Column(name = "source_entity_id", nullable = false, length = 36)
    private String sourceEntityId;
    @Column(name = "target_entity_id", nullable = false, length = 36)
    private String targetEntityId;
    @Column(name = "relation_type", length = 50)
    private String relationType;
    @Column(name = "strength", precision = 10, scale = 4)
    private BigDecimal strength;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
