package com.handmade.ecommerce.seller.model;

import com.handmade.ecommerce.location.model.Location;
import org.chenile.workflow.activities.model.ActivityEnabledStateEntity;
import org.chenile.workflow.activities.model.ActivityLog;

import java.time.LocalDateTime;
import java.util.*;
import com.handmade.ecommerce.seller.model.SellerActivityLog;
import org.chenile.workflow.model.*;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Table(name = "hm_seller")
public class Seller extends AbstractJpaStateEntity
    implements ActivityEnabledStateEntity, ContainsTransientMap
{

    @Column(name = "seller_code", nullable = false, length = 50, unique = true)
    private String sellerCode;

    @Column(name = "seller_name", nullable = false, length = 100)
    private String sellerName;

    @Column(name = "display_name", length = 100)
    private String displayName;

    @Column(name = "url_path", nullable = false, length = 100, unique = true)
    private String urlPath;

    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    @Column(name = "currency", length = 10, nullable = false)
    private String currency;

    @Column(name = "locale", length = 10)
    private String locale;

    @Column(name = "timezone", length = 50)
    private String timezone;

    // ----------------------
    // Contact & Support
    // ----------------------
    @Column(name = "support_email", length = 100)
    private String supportEmail;

    @Column(name = "support_phone", length = 20)
    private String supportPhone;

    @Column(name = "contact_person", length = 100)
    private String contactPerson;

    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    // ----------------------
    // Configuration / Meta
    // ----------------------
    @Column(name = "configuration_id")
    private String configurationId;

    @Column(name = "business_type", length = 50)
    private String businessType; // Individual / Company / NGO

    @Column(name = "rating")
    private Double rating;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // ----------------------
    // Relationships
    // ----------------------

    /**
     * Each seller can have multiple tax info records
     * (for example, different country registrations).
     */
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaxInfo> taxInfos = new ArrayList<>();

    /**
     * A seller can have one or more payment information records
     * (e.g. Bank + PayPal + Stripe).
     */
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SellerPaymentInfo> paymentInfos = new ArrayList<>();

    @Transient
    public TransientMap transientMap = new TransientMap();
    public TransientMap getTransientMap(){
        return this.transientMap;
    }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    public List< SellerActivityLog> activities = new ArrayList<>();

    @Override
    public Collection<ActivityLog> obtainActivities() {
        Collection<ActivityLog> acts = new ArrayList<>();
        for (ActivityLog a: activities){
            acts.add(a);
        }
        return acts;
    }

    @Override
    public ActivityLog addActivity(String eventId, String comment) {
        SellerActivityLog activityLog = new SellerActivityLog();
        activityLog.activityName = eventId;
        activityLog.activityComment = comment;
        activityLog.activitySuccess = true;
        activities.add(activityLog);
        return activityLog;
    }

    public List<TaxInfo> getActiveTaxInfos() {
        return this.taxInfos.stream()
                .filter(TaxInfo::isActive)
                .toList();
    }


    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public String getSupportPhone() {
        return supportPhone;
    }

    public void setSupportPhone(String supportPhone) {
        this.supportPhone = supportPhone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(String configurationId) {
        this.configurationId = configurationId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }



    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<TaxInfo> getTaxInfos() {
        return taxInfos;
    }

    public void setTaxInfos(List<TaxInfo> taxInfos) {
        this.taxInfos = taxInfos;
    }

    public List<SellerPaymentInfo> getPaymentInfos() {
        return paymentInfos;
    }

    public void setPaymentInfos(List<SellerPaymentInfo> paymentInfos) {
        this.paymentInfos = paymentInfos;
    }

    public void setTransientMap(TransientMap transientMap) {
        this.transientMap = transientMap;
    }

    public List<SellerActivityLog> getActivities() {
        return activities;
    }

    public void setActivities(List<SellerActivityLog> activities) {
        this.activities = activities;
    }
}
