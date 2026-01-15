package com.handmade.ecommerce.domain.seller;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SellerTaxInfo - Seller tax information
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_tax_info")
public class SellerTaxInfo extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false, unique = true)
    private String sellerId;

    @Column(name = "tax_id", length = 100)
    private String taxId;

    @Column(name = "tax_id_type", length = 50)
    private String taxIdType;

    @Column(name = "tax_classification", length = 50)
    private String taxClassification;

    @Column(name = "vat_number", length = 100)
    private String vatNumber;

    @Column(name = "tax_country", length = 2)
    private String taxCountry;

    @Column(name = "tax_exemption")
    private Boolean taxExemption = false;
}
