package com.handmade.ecommerce.command;

/**
 * Shipping dimensions for a product variant
 * Used to calculate shipping costs and validate packaging
 */
public class ShippingDimensions {
    private Double weight; // in kg
    private Double length; // in cm
    private Double width; // in cm
    private Double height; // in cm

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    /**
     * Calculate volumetric weight (for shipping)
     * Formula: (L x W x H) / 5000
     */
    public Double getVolumetricWeight() {
        if (length != null && width != null && height != null) {
            return (length * width * height) / 5000.0;
        }
        return null;
    }
}
