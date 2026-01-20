package com.handmade.ecommerce.domain.governance;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_audit_snapshot")
public class AuditSnapshot extends BaseJpaEntity {

    @Column(name = "entity_name", length = 100, nullable = false)
    private String entityName;

    @Column(name = "entity_id", length = 36, nullable = false)
    private String entityId;

    @Lob
    @Column(name = "snapshot_data")
    private String snapshotData; // JSON representation

    @Column(name = "snapshotted_at")
    private java.util.Date snapshottedAt;

    @Column(name = "reason", length = 255)
    private String reason;
}
