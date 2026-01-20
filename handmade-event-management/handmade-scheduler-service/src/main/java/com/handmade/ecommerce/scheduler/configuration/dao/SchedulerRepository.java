package com.handmade.ecommerce.scheduler.configuration.dao;

import com.handmade.ecommerce.event.model.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface SchedulerRepository extends JpaRepository<Scheduler,String> {}
