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
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_kyc")
public class SellerKyc extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false, unique = true)
    private String sellerId;

    @Column(name = "document_type", length = 50)
    private String documentType;

    @Column(name = "document_number", length = 100)
    private String documentNumber;

    @Column(name = "s3_key", length = 255)
    private String s3Key;

    @Column(name = "verification_status", length = 50)
    private String verificationStatus;

    @Column(name = "verified_at")
    private Date verifiedAt;

    @Column(name = "verified_by", length = 255)
    private String verifiedBy;

    @Column(name = "rejection_reason", length = 255)
    private String rejectionReason;
}
