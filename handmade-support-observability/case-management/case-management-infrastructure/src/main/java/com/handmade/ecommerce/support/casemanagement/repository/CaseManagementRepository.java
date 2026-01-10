package com.handmade.ecommerce.support.casemanagement;

import com.handmade.ecommerce.support.casemanagement.entity.CaseManagement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CaseManagement
 * Generated from entity file
 */
@Repository
public interface CaseManagementRepository extends JpaRepository<CaseManagement, String> {
    
    List<CaseManagement> findByCaseType(String caseType);
    List<CaseManagement> findByStatus(String status);
    List<CaseManagement> findByPriority(String priority);
    List<CaseManagement> findByCreatedByUserId(String createdByUserId);
    List<CaseManagement> findByRelatedEntityId(String relatedEntityId);
}
