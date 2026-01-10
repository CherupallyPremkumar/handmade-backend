package com.handmade.ecommerce.platform.event;

import com.handmade.ecommerce.platform.event.entity.JobScheduler;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for JobScheduler
 * Generated from entity file
 */
@Repository
public interface JobSchedulerRepository extends JpaRepository<JobScheduler, String> {
    
    List<JobScheduler> findByJobName(String jobName);
    List<JobScheduler> findByStatus(String status);
}
