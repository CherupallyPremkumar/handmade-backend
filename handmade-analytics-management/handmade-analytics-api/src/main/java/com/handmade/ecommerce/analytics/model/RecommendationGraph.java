package com.handmade.ecommerce.analytics.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_recommendation_graph")
public class RecommendationGraph extends BaseJpaEntity {

    @Column(name = "source_entity_id", length = 36, nullable = false)
    private String sourceEntityId;

    @Column(name = "target_entity_id", length = 36, nullable = false)
    private String targetEntityId;

    @Column(name = "relation_type", length = 50)
    private String relationType;

    @Column(name = "strength", precision = 10, scale = 4)
    private BigDecimal strength;
}
