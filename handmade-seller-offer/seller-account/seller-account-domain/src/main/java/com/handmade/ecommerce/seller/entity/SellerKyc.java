package com.handmade.ecommerce.seller.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_seller_kyc
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_seller_kyc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerKyc extends BaseJpaEntity {
    
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "document_type", length = 50)
    private String documentType;
    @Column(name = "document_number", length = 100)
    private String documentNumber;
    @Column(name = "s3_key", length = 255)
    private String s3Key;
    @Column(name = "verification_status", length = 50)
    private String verificationStatus;
    @Column(name = "rejection_reason", length = 255)
    private String rejectionReason;
    @Column(name = "verified_at")
    private Date verifiedAt;
    @Column(name = "verified_by", length = 255)
    private String verifiedBy;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
