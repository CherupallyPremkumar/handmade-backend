package com.handmade.ecommerce.compliancedocument.configuration.dao;

import com.handmade.ecommerce.risk.model.ComplianceDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ComplianceDocumentRepository extends JpaRepository<ComplianceDocument,String> {}
