package com.handmade.ecommerce.seller.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_seller_tax_info
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_seller_tax_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerTaxInfo extends BaseJpaEntity {
    
    @Column(name = "seller_id", nullable = false, length = 36)
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
    private Boolean isActive;
    @Column(name = "is_verified")
    private Boolean isVerified;
    @Column(name = "verification_date")
    private Date verificationDate;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
