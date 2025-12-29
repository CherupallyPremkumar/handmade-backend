package com.handmade.ecommerce.onboarding.dto;

public class IdentityUpdateRequest {
    private String legalName;
    private String dateOfBirth;
    private String nationalId;
    private String idType;
    private String idCountry;

    public IdentityUpdateRequest(String legalName, String dateOfBirth, String nationalId, String idType,
            String idCountry) {
        this.legalName = legalName;
        this.dateOfBirth = dateOfBirth;
        this.nationalId = nationalId;
        this.idType = idType;
        this.idCountry = idCountry;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(String idCountry) {
        this.idCountry = idCountry;
    }

}