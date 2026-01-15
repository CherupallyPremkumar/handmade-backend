package com.handmade.ecommerce.domain.dispute;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * DisputeEvidence - Evidence files uploaded for a dispute
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_dispute_evidence")
public class DisputeEvidence extends BaseJpaEntity {

    @Column(name = "dispute_id", length = 36, nullable = false)
    private String disputeId;

    @Column(name = "uploader_id", length = 36, nullable = false)
    private String uploaderId; // Customer or Seller ID

    @Column(name = "uploader_type", length = 50, nullable = false)
    private String uploaderType; // CUSTOMER, SELLER, SUPPORT

    @Column(name = "file_url", length = 2048, nullable = false)
    private String fileUrl;

    @Column(name = "file_type", length = 50)
    private String fileType; // IMAGE, PDF, TEXT

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
