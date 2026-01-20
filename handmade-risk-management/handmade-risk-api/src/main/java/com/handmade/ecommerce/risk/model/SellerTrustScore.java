package com.handmade.ecommerce.risk.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_trust_score")
public class SellerTrustScore extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "overall_score", nullable = false)
    private Integer overallScore;

    @Column(name = "fraud_probability", precision = 5, scale = 4, nullable = false)
    private BigDecimal fraudProbability;

    @Column(name = "last_calculated_at")
    private Date lastCalculatedAt;
}
