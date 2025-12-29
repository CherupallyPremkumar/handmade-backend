package com.handmade.ecommerce.seller.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SellerCreateRequest {

    private String sellerCode;
    private String sellerName;
    private String displayName;
    private String urlPath;
    private String logoUrl;
    private String currency;
    private String locale;
    private String timezone;

    private String supportEmail;
    private String supportPhone;
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;

    private String businessType; // Individual / Company / NGO
    private String configurationId;

    // Nested optional data
    private List<TaxInfoRequest> taxInfos;
    private List<SellerPaymentInfoRequest> paymentInfos;



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

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(String configurationId) {
        this.configurationId = configurationId;
    }

    public List<TaxInfoRequest> getTaxInfos() {
        return taxInfos;
    }

    public void setTaxInfos(List<TaxInfoRequest> taxInfos) {
        this.taxInfos = taxInfos;
    }

    public List<SellerPaymentInfoRequest> getPaymentInfos() {
        return paymentInfos;
    }

    public void setPaymentInfos(List<SellerPaymentInfoRequest> paymentInfos) {
        this.paymentInfos = paymentInfos;
    }
}
