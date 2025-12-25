package com.handmade.ecommerce.platform.infrastructure.persistence.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 * JPA Embeddable for CorporateIdentity.
 * Infrastructure layer - persistence model.
 */
@Embeddable
public class CorporateIdentityEmbeddable implements Serializable {
    
    @Column(name = "corporate_legal_name")
    private String legalName;
    
    @Column(name = "corporate_tax_id")
    private String taxId;
    
    @Column(name = "corporate_registration_number")
    private String registrationNumber;
    
    @Column(name = "corporate_jurisdiction")
    private String jurisdiction;

    protected CorporateIdentityEmbeddable() {}

    public CorporateIdentityEmbeddable(String legalName, String taxId, String registrationNumber, String jurisdiction) {
        this.legalName = legalName;
        this.taxId = taxId;
        this.registrationNumber = registrationNumber;
        this.jurisdiction = jurisdiction;
    }

    public String getLegalName() { return legalName; }
    public void setLegalName(String legalName) { this.legalName = legalName; }

    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getJurisdiction() { return jurisdiction; }
    public void setJurisdiction(String jurisdiction) { this.jurisdiction = jurisdiction; }
}
