package com.handmade.ecommerce.seller.account.service.tax;

/**
 * Response from tax validation API
 */
public class TaxValidationResponse {

    private boolean valid;
    private String status; // "ACTIVE", "INACTIVE", "SUSPENDED", "CANCELLED"
    private String businessName;
    private String businessAddress;
    private boolean nameMatches;
    private boolean addressMatches;
    private String errorMessage;
    private String providerMetadata; // JSON string

    public TaxValidationResponse() {
    }

    public TaxValidationResponse(boolean valid, String status) {
        this.valid = valid;
        this.status = status;
    }

    // Getters and Setters

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public boolean isNameMatches() {
        return nameMatches;
    }

    public void setNameMatches(boolean nameMatches) {
        this.nameMatches = nameMatches;
    }

    public boolean isAddressMatches() {
        return addressMatches;
    }

    public void setAddressMatches(boolean addressMatches) {
        this.addressMatches = addressMatches;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getProviderMetadata() {
        return providerMetadata;
    }

    public void setProviderMetadata(String providerMetadata) {
        this.providerMetadata = providerMetadata;
    }
}
