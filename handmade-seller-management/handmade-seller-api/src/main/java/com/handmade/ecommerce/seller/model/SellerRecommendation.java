package com.handmade.ecommerce.seller.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SellerRecommendation - Platform recommendations for sellers
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_recommendation")
public class SellerRecommendation extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "recommendation_type", length = 50, nullable = false)
    private String recommendationType;

    @Column(name = "resource_id", length = 36)
    private String resourceId;

    @Column(name = "priority", length = 20)
    private String priority;

    @Lob
    @Column(name = "details_json")
    private String detailsJson;

    @Column(name = "status", length = 20)
    private String status = "NEW";
}
