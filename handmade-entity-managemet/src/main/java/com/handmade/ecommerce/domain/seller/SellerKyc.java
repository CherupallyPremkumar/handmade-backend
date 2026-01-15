package com.handmade.ecommerce.domain.seller;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * SellerKyc - Seller KYC (Know Your Customer) information
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_kyc")
public class SellerKyc extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false, unique = true)
    private String sellerId;

    @Column(name = "identity_verified")
    private Boolean identityVerified = false;

    @Column(name = "document_type", length = 50)
    private String documentType;

    @Column(name = "document_number", length = 100)
    private String documentNumber;

    @Column(name = "verification_status", length = 50)
    private String verificationStatus;

    @Column(name = "verified_date")
    private Date verifiedDate;

    @Column(name = "verification_provider", length = 100)
    private String verificationProvider;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;
}
