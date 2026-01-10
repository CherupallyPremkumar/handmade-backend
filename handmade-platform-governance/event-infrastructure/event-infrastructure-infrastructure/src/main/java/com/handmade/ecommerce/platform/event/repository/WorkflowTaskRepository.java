package com.handmade.ecommerce.platform.event;

import com.handmade.ecommerce.platform.event.entity.WorkflowTask;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for WorkflowTask
 * Generated from entity file
 */
@Repository
public interface WorkflowTaskRepository extends JpaRepository<WorkflowTask, String> {
    
    List<WorkflowTask> findByWorkflowType(String workflowType);
    List<WorkflowTask> findByInstanceId(String instanceId);
    List<WorkflowTask> findByStepName(String stepName);
    List<WorkflowTask> findByStatus(String status);
}
