package com.handmade.ecommerce.workflowtask.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.event.model.WorkflowTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.workflowtask.configuration.dao.WorkflowTaskRepository;
import java.util.Optional;

public class WorkflowTaskEntityStore implements EntityStore<WorkflowTask>{
    @Autowired private WorkflowTaskRepository workflowtaskRepository;

	@Override
	public void store(WorkflowTask entity) {
        workflowtaskRepository.save(entity);
	}

	@Override
	public WorkflowTask retrieve(String id) {
        Optional<WorkflowTask> entity = workflowtaskRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find WorkflowTask with ID " + id);
	}

}
