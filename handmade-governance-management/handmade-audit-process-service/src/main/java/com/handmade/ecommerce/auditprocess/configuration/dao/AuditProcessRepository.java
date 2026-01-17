package com.handmade.ecommerce.auditprocess.configuration.dao;

import com.handmade.ecommerce.governance.model.AuditProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface AuditProcessRepository extends JpaRepository<AuditProcess,String> {}
