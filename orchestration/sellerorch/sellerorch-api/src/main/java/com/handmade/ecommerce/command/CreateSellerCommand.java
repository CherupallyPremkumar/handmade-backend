package com.handmade.ecommerce.command;

/**
 * Command object for creating a seller.
 * This is a placeholder - you should expand this with actual seller fields.
 */
public class CreateSellerCommand {
    private String sellerName;
    private String email;
    private String businessName;
    private String taxId;
    private String bankAccountNumber;
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

    public String getKycDocumentId() {
        return kycDocumentId;
    }

    public void setKycDocumentId(String kycDocumentId) {
        this.kycDocumentId = kycDocumentId;
    }
}
