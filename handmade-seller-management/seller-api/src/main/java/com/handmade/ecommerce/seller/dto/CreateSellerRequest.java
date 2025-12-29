package com.handmade.ecommerce.seller.dto;

/**
 * Request DTO for creating a seller.
 * Used for validation in the seller module.
 */
public class CreateSellerRequest {
    private String sellerName;
    private String email;
    private String businessName;
    private String countryCode;
    private com.handmade.ecommerce.platform.domain.enums.SellerType sellerType;
    private String taxId;
    private String bankAccountNumber;
    private String ifscCode;
    private String accountHolderName;
    private String kycDocumentId;

    // Getters and Setters
    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getKycDocumentId() {
        return kycDocumentId;
    }

    public void setKycDocumentId(String kycDocumentId) {
        this.kycDocumentId = kycDocumentId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public com.handmade.ecommerce.platform.domain.enums.SellerType getSellerType() {
        return sellerType;
    }

    public void setSellerType(com.handmade.ecommerce.platform.domain.enums.SellerType sellerType) {
        this.sellerType = sellerType;
    }
}
