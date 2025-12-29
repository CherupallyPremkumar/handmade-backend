package com.handmade.ecommerce.gstin.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.time.LocalDateTime;

/**
 * GSTIN (Goods and Services Tax Identification Number) Entity
 * Stores GSTIN details for sellers/businesses in India
 */
@Entity
@Table(name = "hm_gstin", indexes = {
    @Index(name = "idx_gstin_number", columnList = "gstin_number"),
    @Index(name = "idx_seller_id", columnList = "seller_id"),
    @Index(name = "idx_status", columnList = "status")
})
public class Gstin extends BaseJpaEntity {

    /**
     * The 15-character GSTIN number
     * Format: XXAAAAA9999A1Z9
     */
    @Column(name = "gstin_number", nullable = false, unique = true, length = 15)
    private String gstinNumber;

    /**
     * Reference to the seller who owns this GSTIN
     */
    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    /**
     * Legal business name as registered with GST
     */
    @Column(name = "legal_business_name", nullable = false, length = 255)
    private String legalBusinessName;

    /**
     * Trade name (if different from legal name)
     */
    @Column(name = "trade_name", length = 255)
    private String tradeName;

    /**
     * State code extracted from GSTIN (first 2 digits)
     */
    @Column(name = "state_code", nullable = false, length = 2)
    private String stateCode;

    /**
     * State name corresponding to the state code
     */
    @Column(name = "state_name", length = 100)
    private String stateName;

    /**
     * PAN number embedded in GSTIN (characters 3-12)
     */
    @Column(name = "pan_number", nullable = false, length = 10)
    private String panNumber;

    /**
     * Type of business entity
     * Values: PROPRIETORSHIP, PARTNERSHIP, COMPANY, LLP, etc.
     */
    @Column(name = "entity_type", length = 50)
    private String entityType;

    /**
     * Registration date with GST department
     */
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    /**
     * Verification status
     * Values: PENDING, VERIFIED, FAILED, EXPIRED
     */
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    /**
     * Date when GSTIN was verified with GST Portal
     */
    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    /**
     * Method used for verification
     * Values: MANUAL, API, THIRD_PARTY
     */
    @Column(name = "verification_method", length = 50)
    private String verificationMethod;

    /**
     * Response from GST Portal API (if verified via API)
     */
    @Column(name = "verification_response", columnDefinition = "TEXT")
    private String verificationResponse;

    /**
     * Registered address - line 1
     */
    @Column(name = "address_line1", length = 255)
    private String addressLine1;

    /**
     * Registered address - line 2
     */
    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    /**
     * City
     */
    @Column(name = "city", length = 100)
    private String city;

    /**
     * District
     */
    @Column(name = "district", length = 100)
    private String district;

    /**
     * Pincode
     */
    @Column(name = "pincode", length = 10)
    private String pincode;

    /**
     * Nature of business activities
     */
    @Column(name = "business_nature", length = 500)
    private String businessNature;

    /**
     * GST compliance status
     * Values: ACTIVE, SUSPENDED, CANCELLED, INACTIVE
     */
    @Column(name = "compliance_status", length = 20)
    private String complianceStatus;

    /**
     * Last updated date from GST Portal
     */
    @Column(name = "last_updated_from_portal")
    private LocalDateTime lastUpdatedFromPortal;

    /**
     * Additional notes or remarks
     */
    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    // Getters and Setters

    public String getGstinNumber() {
        return gstinNumber;
    }

    public void setGstinNumber(String gstinNumber) {
        this.gstinNumber = gstinNumber;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getLegalBusinessName() {
        return legalBusinessName;
    }

    public void setLegalBusinessName(String legalBusinessName) {
        this.legalBusinessName = legalBusinessName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public String getVerificationMethod() {
        return verificationMethod;
    }

    public void setVerificationMethod(String verificationMethod) {
        this.verificationMethod = verificationMethod;
    }

    public String getVerificationResponse() {
        return verificationResponse;
    }

    public void setVerificationResponse(String verificationResponse) {
        this.verificationResponse = verificationResponse;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getBusinessNature() {
        return businessNature;
    }

    public void setBusinessNature(String businessNature) {
        this.businessNature = businessNature;
    }

    public String getComplianceStatus() {
        return complianceStatus;
    }

    public void setComplianceStatus(String complianceStatus) {
        this.complianceStatus = complianceStatus;
    }

    public LocalDateTime getLastUpdatedFromPortal() {
        return lastUpdatedFromPortal;
    }

    public void setLastUpdatedFromPortal(LocalDateTime lastUpdatedFromPortal) {
        this.lastUpdatedFromPortal = lastUpdatedFromPortal;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Gstin{" +
                "id='" + getId() + '\'' +
                ", gstinNumber='" + gstinNumber + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", legalBusinessName='" + legalBusinessName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
