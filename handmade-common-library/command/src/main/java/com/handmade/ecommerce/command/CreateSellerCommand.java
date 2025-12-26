package com.handmade.ecommerce.command;

import java.util.List;
import java.util.Map;

/**
 * Command object for creating a seller.
 * This will be populated from JSON input in your onboarding API.
 */
public class CreateSellerCommand {

    // Basic seller info
    private String sellerName;
    private String email;
    private String phoneNumber;
    private String businessName;
    private String country; // IN / US / etc.
    private String taxId; // GSTIN / EIN / SSN

    // Bank account details
    private String bankAccountNumber;
    private String ifscCode;         // For India
    private String routingNumber;     // For US
    private String accountHolderName;

    // KYC & Documents
    private String kycDocumentId;
    private List<String> uploadedDocuments; // List of document IDs

    // Address
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;

    // Optional metadata
    private Map<String, Object> metadata;

    // Getters and Setters
    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }

    public String getBankAccountNumber() { return bankAccountNumber; }
    public void setBankAccountNumber(String bankAccountNumber) { this.bankAccountNumber = bankAccountNumber; }

    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }

    public String getRoutingNumber() { return routingNumber; }
    public void setRoutingNumber(String routingNumber) { this.routingNumber = routingNumber; }

    public String getAccountHolderName() { return accountHolderName; }
    public void setAccountHolderName(String accountHolderName) { this.accountHolderName = accountHolderName; }

    public String getKycDocumentId() { return kycDocumentId; }
    public void setKycDocumentId(String kycDocumentId) { this.kycDocumentId = kycDocumentId; }

    public List<String> getUploadedDocuments() { return uploadedDocuments; }
    public void setUploadedDocuments(List<String> uploadedDocuments) { this.uploadedDocuments = uploadedDocuments; }

    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }

    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}