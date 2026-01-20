package com.handmade.ecommerce.domain.analytics;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Recommendation - Product recommendations (e.g., Also Bought)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_recommendation")
public class Recommendation extends BaseJpaEntity {

    @Column(name = "source_product_id", length = 36, nullable = false)
    private String sourceProductId;

    @Column(name = "target_product_id", length = 36, nullable = false)
    private String targetProductId;

    @Column(name = "recommendation_type", length = 50, nullable = false)
    private String recommendationType = "ALSO_BOUGHT";

    @Column(name = "score", precision = 5, scale = 4, nullable = false)
    private BigDecimal score;

    @Column(name = "computed_at", nullable = false)
    private Date computedAt;
}
