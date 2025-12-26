package com.handmade.ecommerce.product.model;

import com.handmade.ecommerce.core.model.AbstractSeller;
import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.core.model.ProductAttribute;
import org.chenile.workflow.activities.model.ActivityEnabledStateEntity;
import org.chenile.workflow.activities.model.ActivityLog;
import java.util.*;
import com.handmade.ecommerce.product.model.ProductActivityLog;
import org.chenile.workflow.model.*;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

@Entity
@Table(name = "hm_product")
public class Product extends AbstractSeller
        implements ActivityEnabledStateEntity,
        ContainsTransientMap {
    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "sub_category_id")
    private String subCategoryId;

    @Column(name = "base_price")
    private Money basePrice;

    @Column(name = "sales_description")
    private String salesDescription;

    @Column(name = "feature_description")
    private String featureDescription;

    @Column(name = "details_description")
    private String detailsDescription;

    @ManyToOne
    @JoinColumn(name = "product_attribute_id")
    private ProductAttribute productAttribute;

    // Image URLs (managed by separate image service)
    @Column(name = "primary_image_url", length = 500)
    private String primaryImageUrl;

    @Column(name = "primary_image_alt_text", length = 200)
    private String primaryImageAltText;

    @ElementCollection
    @CollectionTable(name = "product_additional_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url", length = 500)
    private List<String> additionalImageUrls = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @Transient
    public TransientMap transientMap = new TransientMap();

    public TransientMap getTransientMap() {
        return this.transientMap;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    public List<ProductActivityLog> activities = new ArrayList<>();

    public ProductAttribute getProductAttribute() {
        return productAttribute;
    }

    public void setProductAttribute(ProductAttribute productAttribute) {
        this.productAttribute = productAttribute;
    }

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
        ProductActivityLog activityLog = new ProductActivityLog();
        activityLog.activityName = eventId;
        activityLog.activityComment = comment;
        activityLog.activitySuccess = true;
        activities.add(activityLog);
        return activityLog;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeatureDescription() {
        return featureDescription;
    }

    public void setFeatureDescription(String featureDescription) {
        this.featureDescription = featureDescription;
    }

    public String getSalesDescription() {
        return salesDescription;
    }

    public void setSalesDescription(String salesDescription) {
        this.salesDescription = salesDescription;
    }

    public String getDetailsDescription() {
        return detailsDescription;
    }

    public void setDetailsDescription(String detailsDescription) {
        this.detailsDescription = detailsDescription;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getPrimaryImageUrl() {
        return primaryImageUrl;
    }

    public void setPrimaryImageUrl(String primaryImageUrl) {
        this.primaryImageUrl = primaryImageUrl;
    }

    public String getPrimaryImageAltText() {
        return primaryImageAltText;
    }

    public void setPrimaryImageAltText(String primaryImageAltText) {
        this.primaryImageAltText = primaryImageAltText;
    }

    public List<String> getAdditionalImageUrls() {
        return additionalImageUrls;
    }

    public void setAdditionalImageUrls(List<String> additionalImageUrls) {
        this.additionalImageUrls = additionalImageUrls;
    }

    public void setTransientMap(TransientMap transientMap) {
        this.transientMap = transientMap;
    }

    public List<ProductActivityLog> getActivities() {
        return activities;
    }

    public void setActivities(List<ProductActivityLog> activities) {
        this.activities = activities;
    }

}
