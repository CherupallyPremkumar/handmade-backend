package com.handmade.ecommerce.platform.governance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_audit_snapshot
 * Stores historical snapshots of entities for auditing and rollback.
 */
@Entity
@Table(name = "hm_audit_snapshot")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class AuditSnapshot extends BaseJpaEntity {

    @Column(name = "entity_name", nullable = false, length = 100)
    private String entityName;

    @Column(name = "entity_id", nullable = false, length = 36)
    private String entityId;

    @Lob
    @Column(name = "snapshot_data")
    private String snapshotData;

    @Column(name = "snapshotted_at")
    private LocalDateTime snapshottedAt;

    @Column(name = "reason", length = 255)
    private String reason;

    @Column(name = "version")
    private Long version;

    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
