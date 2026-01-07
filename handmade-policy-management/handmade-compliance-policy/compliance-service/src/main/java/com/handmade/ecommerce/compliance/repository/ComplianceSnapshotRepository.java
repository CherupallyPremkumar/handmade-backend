package com.handmade.ecommerce.compliance.repository;

import com.handmade.ecommerce.compliance.domain.entity.ComplianceSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplianceSnapshotRepository extends JpaRepository<ComplianceSnapshot, String> {
}
