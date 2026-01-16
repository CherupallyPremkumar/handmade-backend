package com.handmade.ecommerce.workflowtask.configuration.dao;

import com.handmade.ecommerce.event.model.WorkflowTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface WorkflowTaskRepository extends JpaRepository<WorkflowTask,String> {}
