package com.handmade.ecommerce.domain.risk;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_compliance_document")
public class ComplianceDocument extends AbstractJpaStateEntity {

    @Column(name = "entity_type", length = 50)
    private String entityType; // SELLER, PRODUCT

    @Column(name = "entity_id", length = 36, nullable = false)
    private String entityId;

    @Column(name = "doc_type", length = 50)
    private String docType; // VAT_CERT, IDENTITY_PROOF

    @Column(name = "file_uri", length = 255)
    private String fileUri;

    @Column(name = "expiry_date")
    private Date expiryDate;
}
