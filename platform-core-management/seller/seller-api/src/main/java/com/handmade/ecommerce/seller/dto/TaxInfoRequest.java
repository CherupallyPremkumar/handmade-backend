package com.handmade.ecommerce.seller.dto;

import java.util.List;

public class TaxInfoRequest {
    private String companyName;
    private String companyType;
    private String panNumber;
    private String gstNumber;
    private String vatNumber;
    private String taxCountry;
    private String taxState;
    private List<TaxRegistrationRequest> registrations;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getTaxCountry() {
        return taxCountry;
    }

    public void setTaxCountry(String taxCountry) {
        this.taxCountry = taxCountry;
    }

    public String getTaxState() {
        return taxState;
    }

    public void setTaxState(String taxState) {
        this.taxState = taxState;
    }

    public List<TaxRegistrationRequest> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<TaxRegistrationRequest> registrations) {
        this.registrations = registrations;
    }
}
