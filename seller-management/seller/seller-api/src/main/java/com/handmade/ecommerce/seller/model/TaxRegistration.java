package com.handmade.ecommerce.seller.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "seller_tax_registration")
public class TaxRegistration  extends BaseJpaEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_info_id", nullable = false)
    private TaxInfo taxInfo;

    @Column(length = 50)
    private String country;

    @Column(length = 100)
    private String taxIdNumber;

    private LocalDate registrationDate;

    private LocalDate expiryDate;

    @Column(length = 50)
    private String status; // ACTIVE / EXPIRED / SUSPENDED

    @ElementCollection
    @CollectionTable(name = "tax_registration_documents", joinColumns = @JoinColumn(name = "registration_id"))
    @Column(name = "document_url")
    private List<String> documents = new ArrayList<>();

    public TaxInfo getTaxInfo() {
        return taxInfo;
    }

    public void setTaxInfo(TaxInfo taxInfo) {
        this.taxInfo = taxInfo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTaxIdNumber() {
        return taxIdNumber;
    }

    public void setTaxIdNumber(String taxIdNumber) {
        this.taxIdNumber = taxIdNumber;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    // Getters & Setters
}