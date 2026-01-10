package com.handmade.ecommerce.platform.risk;

import com.handmade.ecommerce.platform.risk.entity.ComplianceDocument;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ComplianceDocument
 * Generated from entity file
 */
@Repository
public interface ComplianceDocumentRepository extends JpaRepository<ComplianceDocument, String> {
    
    List<ComplianceDocument> findByEntityType(String entityType);
    List<ComplianceDocument> findByEntityId(String entityId);
    List<ComplianceDocument> findByDocType(String docType);
    List<ComplianceDocument> findByVerificationStatus(String verificationStatus);
}
