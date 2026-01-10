package com.handmade.ecommerce.platform.risk.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_compliance_document
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_compliance_document")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ComplianceDocument extends BaseJpaEntity {
    
    @Column(name = "entity_type", length = 50)
    private String entityType;
    @Column(name = "entity_id", nullable = false, length = 36)
    private String entityId;
    @Column(name = "doc_type", length = 50)
    private String docType;
    @Column(name = "file_uri", length = 255)
    private String fileUri;
    @Column(name = "verification_status", length = 20)
    private String verificationStatus;
    @Column(name = "expiry_date")
    private Date expiryDate;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
