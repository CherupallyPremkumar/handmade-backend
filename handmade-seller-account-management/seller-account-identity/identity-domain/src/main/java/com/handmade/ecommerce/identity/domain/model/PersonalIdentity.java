package com.handmade.ecommerce.identity.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

/**
 * Personal Identity Information (PII)
 * 
 * SECURITY: This should be encrypted at rest in production
 * Consider using @Convert with encryption converter
 */
@Embeddable
public class PersonalIdentity {

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "id_type")
    private String idType; // "PASSPORT", "DRIVERS_LICENSE", "NATIONAL_ID"

    @Column(name = "id_country")
    private String idCountry; // ISO 3166-1 alpha-2

    @Column(name = "nationality")
    private String nationality; // ISO 3166-1 alpha-2

    // Constructors
    public PersonalIdentity() {
    }

    public PersonalIdentity(String legalName, LocalDate dateOfBirth, String nationalId,
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
