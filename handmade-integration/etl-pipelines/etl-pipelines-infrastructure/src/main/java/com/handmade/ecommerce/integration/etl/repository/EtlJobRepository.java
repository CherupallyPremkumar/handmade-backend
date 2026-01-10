package com.handmade.ecommerce.integration.etl;

import com.handmade.ecommerce.integration.etl.entity.EtlJob;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for EtlJob
 * Generated from entity file
 */
@Repository
public interface EtlJobRepository extends JpaRepository<EtlJob, String> {
    
    List<EtlJob> findByJobName(String jobName);
    List<EtlJob> findByStatus(String status);
}
