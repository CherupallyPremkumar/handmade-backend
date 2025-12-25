package com.handmade.ecommerce.platform.configuration.dao;

import com.handmade.ecommerce.platform.domain.entity.PlatformAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatformAuditLogRepository extends JpaRepository<PlatformAuditLog, String> {
    List<PlatformAuditLog> findByPlatformIdOrderByOccurredAtDesc(String platformId);
}
