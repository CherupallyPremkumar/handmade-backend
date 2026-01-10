package com.handmade.ecommerce.platform.observability;

import com.handmade.ecommerce.platform.observability.entity.MetricsSnapshot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for MetricsSnapshot
 * Generated from entity file
 */
@Repository
public interface MetricsSnapshotRepository extends JpaRepository<MetricsSnapshot, String> {
    
    List<MetricsSnapshot> findByMetricName(String metricName);
}
