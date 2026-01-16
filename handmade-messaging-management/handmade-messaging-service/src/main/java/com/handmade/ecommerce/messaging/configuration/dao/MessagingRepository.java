package com.handmade.ecommerce.messaging.configuration.dao;

import com.handmade.ecommerce.messaging.model.Messaging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface MessagingRepository extends JpaRepository<Messaging,String> {}
