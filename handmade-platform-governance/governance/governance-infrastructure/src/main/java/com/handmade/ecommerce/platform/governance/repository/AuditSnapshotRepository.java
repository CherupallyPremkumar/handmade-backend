package com.handmade.ecommerce.platform.governance.repository;

import com.handmade.ecommerce.platform.governance.entity.AuditSnapshot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for AuditSnapshot
 */
@Repository
public interface AuditSnapshotRepository extends JpaRepository<AuditSnapshot, String> {

    List<AuditSnapshot> findByEntityName(String entityName);

    List<AuditSnapshot> findByEntityId(String entityId);

    List<AuditSnapshot> findByEntityNameAndEntityIdOrderBySnapshottedAtDesc(String entityName, String entityId);
}
