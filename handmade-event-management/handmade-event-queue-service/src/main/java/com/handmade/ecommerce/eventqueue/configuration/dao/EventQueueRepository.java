package com.handmade.ecommerce.eventqueue.configuration.dao;

import com.handmade.ecommerce.event.model.EventQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface EventQueueRepository extends JpaRepository<EventQueue,String> {}
