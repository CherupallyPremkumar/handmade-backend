package com.handmade.ecommerce.domain.seller;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * SellerBadge - Achievement badges for sellers
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_badge")
public class SellerBadge extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "badge_type", length = 50, nullable = false)
    private String badgeType;

    @Column(name = "badge_name", length = 255)
    private String badgeName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "icon_url", length = 500)
    private String iconUrl;

    @Column(name = "earned_date")
    private Date earnedDate;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
