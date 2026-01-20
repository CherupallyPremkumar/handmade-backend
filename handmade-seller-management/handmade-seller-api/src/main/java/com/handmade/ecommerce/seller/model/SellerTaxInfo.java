package com.handmade.ecommerce.seller.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.util.Date;

/**
 * SellerTaxInfo - Seller tax information
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_tax_info")
public class SellerTaxInfo extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false, unique = true)
    private String sellerId;

    @Column(name = "company_name", length = 255)
    private String companyName;

    @Column(name = "company_type", length = 100)
    private String companyType;

    @Column(name = "business_registration_number", length = 100)
    private String businessRegistrationNumber;

    @Column(name = "pan_number", length = 20)
    private String panNumber;

    @Column(name = "gst_number", length = 20)
    private String gstNumber;

    @Column(name = "vat_number", length = 50)
    private String vatNumber;

    @Column(name = "tax_country", length = 50)
    private String taxCountry;

    @Column(name = "tax_state", length = 50)
    private String taxState;

    @Column(name = "address_line1", length = 255)
    private String addressLine1;

    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "verification_date")
    private Date verificationDate;

    @ElementCollection
    @CollectionTable(name = "hm_seller_tax_documents", joinColumns = @JoinColumn(name = "tax_info_id"))
    private java.util.List<SellerTaxDocuments> taxDocuments;
}
