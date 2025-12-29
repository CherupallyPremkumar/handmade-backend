package com.handmade.ecommerce.identity.dto;

import java.time.LocalDate;

/**
 * Request to submit identity documents
 */
public class IdentityDocumentRequest {

    private String legalName;
    private LocalDate dateOfBirth;
    private String nationalId;
    private String idType; // "PASSPORT", "DRIVERS_LICENSE", "NATIONAL_ID"
    private String idCountry; // ISO 3166-1 alpha-2
    private String nationality; // ISO 3166-1 alpha-2

    // Constructors
    public IdentityDocumentRequest() {
    }

    public IdentityDocumentRequest(String legalName, LocalDate dateOfBirth, String nationalId,
            String idType, String idCountry, String nationality) {
        this.legalName = legalName;
        this.dateOfBirth = dateOfBirth;
        this.nationalId = nationalId;
        this.idType = idType;
        this.idCountry = idCountry;
        this.nationality = nationality;
    }

    // Getters and Setters

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
