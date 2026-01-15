package com.handmade.ecommerce.domain.seller;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * SellerTaxDocuments - Tax-related documents for sellers
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_tax_documents")
public class SellerTaxDocuments extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "document_type", length = 50, nullable = false)
    private String documentType;

    @Column(name = "document_url", length = 500, nullable = false)
    private String documentUrl;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "verification_notes", columnDefinition = "TEXT")
    private String verificationNotes;
}
