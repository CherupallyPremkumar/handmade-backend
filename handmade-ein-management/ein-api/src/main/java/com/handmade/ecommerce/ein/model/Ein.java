package com.handmade.ecommerce.ein.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.time.LocalDateTime;

/**
 * EIN (Employer Identification Number) Entity
 * Stores EIN details for businesses in USA
 */
@Entity
@Table(name = "hm_ein", indexes = {
    @Index(name = "idx_ein_number", columnList = "ein_number"),
    @Index(name = "idx_seller_id", columnList = "seller_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_campus_code", columnList = "campus_code")
})
public class Ein extends BaseJpaEntity {

    /**
     * The 9-digit EIN number (without hyphen)
     * Format: XXXXXXXXX (stored without hyphen)
     * Display format: XX-XXXXXXX
     */
    @Column(name = "ein_number", nullable = false, unique = true, length = 9)
    private String einNumber;

    /**
     * Reference to the seller who owns this EIN
     */
    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    /**
     * Legal business name as registered with IRS
     */
    @Column(name = "legal_business_name", nullable = false, length = 255)
    private String legalBusinessName;

    /**
     * Doing Business As (DBA) name
     */
    @Column(name = "dba_name", length = 255)
    private String dbaName;

    /**
     * Campus code (first 2 digits of EIN)
     * Indicates IRS processing center
     */
    @Column(name = "campus_code", nullable = false, length = 2)
    private String campusCode;

    /**
     * IRS campus location
     * Examples: "Andover, MA", "Atlanta, GA", "Internet/Online"
     */
    @Column(name = "campus_location", length = 100)
    private String campusLocation;

    /**
     * Type of business entity
     * Values: SOLE_PROPRIETOR, PARTNERSHIP, CORPORATION, LLC, NON_PROFIT, etc.
     */
    @Column(name = "entity_type", length = 50)
    private String entityType;

    /**
     * Date when EIN was assigned by IRS
     */
    @Column(name = "assignment_date")
    private LocalDateTime assignmentDate;

    /**
     * Verification status
     * Values: PENDING, VERIFIED, FAILED, EXPIRED
     */
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    /**
     * Date when EIN was verified
     */
    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    /**
     * Method used for verification
     * Values: MANUAL, THIRD_PARTY, IRS_LETTER
     */
    @Column(name = "verification_method", length = 50)
    private String verificationMethod;

    /**
     * Response from verification service (if verified via third-party)
     */
    @Column(name = "verification_response", columnDefinition = "TEXT")
    private String verificationResponse;

    /**
     * Business address - line 1
     */
    @Column(name = "address_line1", length = 255)
    private String addressLine1;

    /**
     * Business address - line 2
     */
    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    /**
     * City
     */
    @Column(name = "city", length = 100)
    private String city;

    /**
     * State (2-letter code)
     */
    @Column(name = "state", length = 2)
    private String state;

    /**
     * ZIP code
     */
    @Column(name = "zip_code", length = 10)
    private String zipCode;

    /**
     * Primary business activity
     */
    @Column(name = "business_activity", length = 500)
    private String businessActivity;

    /**
     * NAICS code (North American Industry Classification System)
     */
    @Column(name = "naics_code", length = 10)
    private String naicsCode;

    /**
     * Number of employees
     */
    @Column(name = "employee_count")
    private Integer employeeCount;

    /**
     * Tax year end month (1-12)
     */
    @Column(name = "tax_year_end_month")
    private Integer taxYearEndMonth;

    /**
     * IRS compliance status
     * Values: ACTIVE, INACTIVE, REVOKED, SUSPENDED
     */
    @Column(name = "compliance_status", length = 20)
    private String complianceStatus;

    /**
     * Last updated from IRS or verification service
     */
    @Column(name = "last_updated_from_irs")
    private LocalDateTime lastUpdatedFromIrs;

    /**
     * Additional notes or remarks
     */
    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    // Getters and Setters

    public String getEinNumber() {
        return einNumber;
    }

    public void setEinNumber(String einNumber) {
        this.einNumber = einNumber;
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

    public String getDbaName() {
        return dbaName;
    }

    public void setDbaName(String dbaName) {
        this.dbaName = dbaName;
    }

    public String getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(String campusCode) {
        this.campusCode = campusCode;
    }

    public String getCampusLocation() {
        return campusLocation;
    }

    public void setCampusLocation(String campusLocation) {
        this.campusLocation = campusLocation;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public LocalDateTime getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(LocalDateTime assignmentDate) {
        this.assignmentDate = assignmentDate;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getBusinessActivity() {
        return businessActivity;
    }

    public void setBusinessActivity(String businessActivity) {
        this.businessActivity = businessActivity;
    }

    public String getNaicsCode() {
        return naicsCode;
    }

    public void setNaicsCode(String naicsCode) {
        this.naicsCode = naicsCode;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Integer getTaxYearEndMonth() {
        return taxYearEndMonth;
    }

    public void setTaxYearEndMonth(Integer taxYearEndMonth) {
        this.taxYearEndMonth = taxYearEndMonth;
    }

    public String getComplianceStatus() {
        return complianceStatus;
    }

    public void setComplianceStatus(String complianceStatus) {
        this.complianceStatus = complianceStatus;
    }

    public LocalDateTime getLastUpdatedFromIrs() {
        return lastUpdatedFromIrs;
    }

    public void setLastUpdatedFromIrs(LocalDateTime lastUpdatedFromIrs) {
        this.lastUpdatedFromIrs = lastUpdatedFromIrs;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Ein{" +
                "id='" + getId() + '\'' +
                ", einNumber='" + einNumber + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", legalBusinessName='" + legalBusinessName + '\'' +
                ", campusCode='" + campusCode + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
