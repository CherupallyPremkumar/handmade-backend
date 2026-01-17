package com.handmade.ecommerce.event.service;

import com.handmade.ecommerce.event.model.Scheduler;

public interface SchedulerService {

    public Scheduler save(Scheduler entity);

    public Scheduler retrieve(String id);
}
