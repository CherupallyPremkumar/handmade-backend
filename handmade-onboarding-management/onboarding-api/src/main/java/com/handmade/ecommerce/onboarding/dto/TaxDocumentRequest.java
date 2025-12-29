package com.handmade.ecommerce.onboarding.dto;

/**
 * Request to submit tax documents
 */
public class TaxDocumentRequest {

    private String taxIdType; // "GST", "VAT", "EIN", "PAN"
    private String taxIdNumber;
    private String taxCountry; // ISO 3166-1 alpha-2
    private String businessName;
    private String businessAddress;

    // Document uploads (URLs after upload to S3)
    private String taxCertificateUrl;
    private String gstCertificateUrl;

    // Constructors
    public TaxDocumentRequest() {
    }

    public TaxDocumentRequest(String taxIdType, String taxIdNumber, String taxCountry,
            String businessName, String businessAddress) {
        this.taxIdType = taxIdType;
        this.taxIdNumber = taxIdNumber;
        this.taxCountry = taxCountry;
        this.businessName = businessName;
        this.businessAddress = businessAddress;
    }

    // Getters and Setters

    public String getTaxIdType() {
        return taxIdType;
    }

    public void setTaxIdType(String taxIdType) {
        this.taxIdType = taxIdType;
    }

    public String getTaxIdNumber() {
        return taxIdNumber;
    }

    public void setTaxIdNumber(String taxIdNumber) {
        this.taxIdNumber = taxIdNumber;
    }

    public String getTaxCountry() {
        return taxCountry;
    }

    public void setTaxCountry(String taxCountry) {
        this.taxCountry = taxCountry;
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

    public String getTaxCertificateUrl() {
        return taxCertificateUrl;
    }

    public void setTaxCertificateUrl(String taxCertificateUrl) {
        this.taxCertificateUrl = taxCertificateUrl;
    }

    public String getGstCertificateUrl() {
        return gstCertificateUrl;
    }

    public void setGstCertificateUrl(String gstCertificateUrl) {
        this.gstCertificateUrl = gstCertificateUrl;
    }
}
