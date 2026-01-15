package com.handmade.ecommerce.domain.governance;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_audit_snapshot")
public class AuditSnapshot extends BaseJpaEntity {

    @Column(name = "audit_process_id", length = 36, nullable = false)
    private String auditProcessId;

    @Column(name = "snapshot_type", length = 50)
    private String snapshotType; // PRE_AUDIT, POST_AUDIT

    @Column(name = "entity_data_json", columnDefinition = "TEXT")
    private String entityDataJson;
}
