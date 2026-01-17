package com.handmade.ecommerce.deliveryattempt.configuration.dao;

import com.handmade.ecommerce.logistics.model.DeliveryAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface DeliveryAttemptRepository extends JpaRepository<DeliveryAttempt,String> {}
