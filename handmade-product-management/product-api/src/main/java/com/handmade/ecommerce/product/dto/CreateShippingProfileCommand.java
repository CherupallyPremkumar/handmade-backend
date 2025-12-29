package com.handmade.ecommerce.product.dto;

/**
 * Command to create a shipping profile for a variant
 */
public class CreateShippingProfileCommand {

    private String variantId;
    private String profileType; // STANDARD, GIFT_WRAP, BULK, etc.
    private ShippingDimensions shippingDimensions;
    private String packagingType; // BOX, ENVELOPE, POLY_MAILER, etc.
    private Boolean isDefault;
    private String notes;

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public ShippingDimensions getShippingDimensions() {
        return shippingDimensions;
    }

    public void setShippingDimensions(ShippingDimensions shippingDimensions) {
        this.shippingDimensions = shippingDimensions;
    }

    public String getPackagingType() {
        return packagingType;
    }

    public void setPackagingType(String packagingType) {
        this.packagingType = packagingType;
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
