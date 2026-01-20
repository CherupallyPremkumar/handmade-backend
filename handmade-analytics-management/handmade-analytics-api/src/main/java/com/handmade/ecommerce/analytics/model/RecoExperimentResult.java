package com.handmade.ecommerce.analytics.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_recommendation_experiment_result")
public class RecoExperimentResult extends BaseJpaEntity {

    @Column(name = "experiment_id", length = 100, nullable = false)
    private String experimentId;

    @Column(name = "variant_id", length = 100, nullable = false)
    private String variantId;

    @Column(name = "product_id", length = 36)
    private String productId;

    @Column(name = "conversion_rate", precision = 5, scale = 4)
    private BigDecimal conversionRate;

    @Column(name = "revenue_uplift", precision = 19, scale = 4)
    private BigDecimal revenueUplift;

    @Column(name = "captured_at")
    private Date capturedAt;
}
