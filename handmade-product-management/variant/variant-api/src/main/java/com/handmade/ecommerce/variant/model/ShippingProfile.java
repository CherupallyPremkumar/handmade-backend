package com.handmade.ecommerce.variant.model;

import com.handmade.ecommerce.core.model.ShippingDimensions;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.time.LocalDateTime;

/**
 * Shipping Profile for a variant
 * Allows multiple packaging options per variant (standard, gift wrap, bulk,
 * etc.)
 * Tracks historical changes for auditing and fee disputes
 */
@Entity
@Table(name = "variant_shipping_profile")
public class ShippingProfile extends BaseJpaEntity {

    @Column(name = "variant_id", nullable = false)
    private String variantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_type", nullable = false)
    private ProfileType profileType;

    @Embedded
    private ShippingDimensions dimensions;

    @Column(name = "packaging_type")
    private String packagingType; // BOX, ENVELOPE, POLY_MAILER, TUBE, etc.

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_default")
    private Boolean isDefault = false;


    @Column(name = "notes")
    private String notes;

    public enum ProfileType {
        STANDARD, // Regular packaging
        GIFT_WRAP, // Gift wrapped
        BULK, // Bulk/wholesale packaging
        FRAGILE, // Extra protective packaging
        OVERSIZED, // Oversized item
        HAZMAT, // Hazardous materials
        PERISHABLE, // Refrigerated/temperature controlled
        CUSTOM // Custom packaging
    }



    // Getters and Setters
    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public ProfileType getProfileType() {
        return profileType;
    }

    public void setProfileType(ProfileType profileType) {
        this.profileType = profileType;
    }

    public ShippingDimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(ShippingDimensions dimensions) {
        this.dimensions = dimensions;
    }

    public String getPackagingType() {
        return packagingType;
    }

    public void setPackagingType(String packagingType) {
        this.packagingType = packagingType;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }



    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
