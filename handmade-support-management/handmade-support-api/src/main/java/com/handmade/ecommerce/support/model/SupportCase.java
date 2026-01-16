package com.handmade.ecommerce.support.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * SupportCase - Manages customer and seller support tickets
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_case_management")
public class SupportCase extends AbstractJpaStateEntity {

    @Column(name = "case_type", length = 50, nullable = false)
    private String caseType; // ORDER_ISSUE, ACCOUNT_ISSUE, TECHNICAL

    @Column(name = "priority", length = 20, nullable = false)
    private String priority = "NORMAL";

    @Column(name = "subject", length = 255, nullable = false)
    private String subject;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_by_user_id", length = 36, nullable = false)
    private String createdByUserId;

    @Column(name = "assigned_to", length = 36)
    private String assignedTo; // Agent ID

    @Column(name = "related_entity_id", length = 36)
    private String relatedEntityId; // Order ID, Product ID, etc.
}
