package com.handmade.ecommerce.offer.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import org.chenile.workflow.activities.model.ActivityEnabledStateEntity;
import org.chenile.workflow.activities.model.ActivityLog;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Offer Aggregate Root
 * Unifies Product Variant, Seller, Price, and Inventory.
 * 
 * Follows Amazon model where an 'Offer' is a specific seller's 
 * proposition for a product variant.
 */
@Entity
@Table(name = "hm_offer", uniqueConstraints = {
    @UniqueConstraint(name = "uk_offer_seller_variant_region", columnNames = {"seller_id", "variant_id", "region_id"})
})
@Getter
@Setter
public class Offer extends AbstractJpaStateEntity implements ActivityEnabledStateEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;

    @Column(name = "variant_id", nullable = false, length = 36)
    private String variantId;

    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;

    @Column(name = "region_id", nullable = false, length = 36)
    private String regionId;

    @Column(name = "price", nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    @Column(name = "currency", nullable = false, length = 10)
    private String currency;

    @Column(name = "activation_reason", length = 255)
    private String activationReason;

    @Column(name = "suspension_reason", length = 255)
    private String suspensionReason;

    @Column(name = "offer_code", length = 50, unique = true)
    private String offerCode;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "offer_id")
    public List<OfferActivityLog> activities = new ArrayList<>();

    @Override
    public Collection<ActivityLog> obtainActivities() {
        Collection<ActivityLog> acts = new ArrayList<>();
        for (ActivityLog a : activities) {
            acts.add(a);
        }
        return acts;
    }

    @Override
    public ActivityLog addActivity(String eventId, String comment) {
        OfferActivityLog activityLog = new OfferActivityLog();
        activityLog.activityName = eventId;
        activityLog.activityComment = comment;
        activityLog.activitySuccess = true;
        activities.add(activityLog);
        return activityLog;
    }
}
