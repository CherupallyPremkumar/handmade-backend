package com.handmade.ecommerce.seller.performance.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_seller_recommendation
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_seller_recommendation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerRecommendation extends BaseJpaEntity {
    
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "recommendation_type", nullable = false, length = 50)
    private String recommendationType;
    @Column(name = "resource_id", length = 36)
    private String resourceId;
    @Column(name = "priority", length = 20)
    private String priority;
    @Column(name = "details_json")
    private String detailsJson;
    @Column(name = "status", length = 20)
    private String status;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
