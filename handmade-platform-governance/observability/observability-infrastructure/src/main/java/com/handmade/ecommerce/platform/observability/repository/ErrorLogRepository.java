package com.handmade.ecommerce.platform.observability;

import com.handmade.ecommerce.platform.observability.entity.ErrorLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ErrorLog
 * Generated from entity file
 */
@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, String> {
    
    List<ErrorLog> findByServiceName(String serviceName);
    List<ErrorLog> findByTraceId(String traceId);
    List<ErrorLog> findBySpanId(String spanId);
}
