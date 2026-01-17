package com.handmade.ecommerce.deliveryexception.configuration.dao;

import com.handmade.ecommerce.logistics.model.DeliveryException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface DeliveryExceptionRepository extends JpaRepository<DeliveryException,String> {}
