package com.handmade.ecommerce.product.domain.model;

import com.handmade.ecommerce.core.model.AbstractSeller;
import com.handmade.ecommerce.core.model.VariantAttribute;
import org.chenile.workflow.activities.model.ActivityEnabledStateEntity;
import org.chenile.workflow.activities.model.ActivityLog;
import java.util.*;
import org.chenile.workflow.model.*;
import jakarta.persistence.*;

@Entity
@Table(name = "hmvariant")
public class Variant extends AbstractSeller
        implements ActivityEnabledStateEntity,
        ContainsTransientMap {
    private String productId;
    
    /**
     * @deprecated Seller-specific data will move to Offer module. Do not add new seller-related fields here.
     * use Offer module for seller specific data
     */
    @Deprecated
    private String artisanId;
    private String sku;
    private String title;
    private String displayName;
    private String description;
    private String featureDescription;

    // Image URLs (managed by separate image service)
    @Column(name = "primary_image_url", length = 500)
    private String primaryImageUrl;

    @ElementCollection
    @CollectionTable(name = "variant_image_urls", joinColumns = @JoinColumn(name = "variant_id"))
    @Column(name = "image_url", length = 500)
    private List<String> imageUrls = new ArrayList<>();

    // Shipping profiles (multiple packaging options)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "variant_id")
    private List<ShippingProfile> shippingProfiles = new ArrayList<>();

    @OneToMany
    private List<VariantAttribute> attributes = new ArrayList<>();

    @Transient
    public TransientMap transientMap = new TransientMap();

    public TransientMap getTransientMap() {
        return this.transientMap;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    public List<VariantActivityLog> activities = new ArrayList<>();

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
        VariantActivityLog activityLog = new VariantActivityLog();
        activityLog.activityName = eventId;
        activityLog.activityComment = comment;
        activityLog.activitySuccess = true;
        activities.add(activityLog);
        return activityLog;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getArtisanId() {
        return artisanId;
    }

    public void setArtisanId(String artisanId) {
        this.artisanId = artisanId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<VariantAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<VariantAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getFeatureDescription() {
        return featureDescription;
    }

    public void setFeatureDescription(String featureDescription) {
        this.featureDescription = featureDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrimaryImageUrl() {
        return primaryImageUrl;
    }

    public void setPrimaryImageUrl(String primaryImageUrl) {
        this.primaryImageUrl = primaryImageUrl;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<ShippingProfile> getShippingProfiles() {
        return shippingProfiles;
    }

    public void setShippingProfiles(List<ShippingProfile> shippingProfiles) {
        this.shippingProfiles = shippingProfiles;
    }

    /**
     * Get the default shipping profile
     */
    public ShippingProfile getDefaultShippingProfile() {
        return shippingProfiles.stream()
                .filter(ShippingProfile::getIsDefault)
                .findFirst()
                .orElse(shippingProfiles.isEmpty() ? null : shippingProfiles.get(0));
    }
}
