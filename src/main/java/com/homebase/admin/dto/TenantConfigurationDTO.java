package com.homebase.admin.dto;

public class TenantConfigurationDTO {
    private String primaryColor;
    private String secondaryColor;
    private Boolean enablePromotions;
    private Boolean enableReviews;

    // Private constructor for builder
    private TenantConfigurationDTO(Builder builder) {
        this.primaryColor = builder.primaryColor;
        this.secondaryColor = builder.secondaryColor;
        this.enablePromotions = builder.enablePromotions;
        this.enableReviews = builder.enableReviews;
    }


    // Getters
    public String getPrimaryColor() { return primaryColor; }
    public String getSecondaryColor() { return secondaryColor; }
    public Boolean getEnablePromotions() { return enablePromotions; }
    public Boolean getEnableReviews() { return enableReviews; }

    // Builder class
    public static class Builder {
        private String primaryColor;
        private String secondaryColor;
        private Boolean enablePromotions;
        private Boolean enableReviews;

        public Builder primaryColor(String primaryColor) { this.primaryColor = primaryColor; return this; }
        public Builder secondaryColor(String secondaryColor) { this.secondaryColor = secondaryColor; return this; }
        public Builder enablePromotions(Boolean enablePromotions) { this.enablePromotions = enablePromotions; return this; }
        public Builder enableReviews(Boolean enableReviews) { this.enableReviews = enableReviews; return this; }

        public TenantConfigurationDTO build() {
            return new TenantConfigurationDTO(this);
        }
    }
}