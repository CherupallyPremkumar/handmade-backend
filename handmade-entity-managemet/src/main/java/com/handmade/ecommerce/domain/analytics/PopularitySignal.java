package com.handmade.ecommerce.domain.analytics;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_product_popularity_signal")
public class PopularitySignal extends BaseJpaEntity {

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "views_count")
    private Long viewsCount;

    @Column(name = "purchases_count")
    private Long purchasesCount;

    @Column(name = "wishlist_count")
    private Long wishlistCount;

    @Column(name = "trending_score", precision = 10, scale = 4)
    private BigDecimal trendingScore;

    @Column(name = "last_updated")
    private Date lastUpdated;
}
