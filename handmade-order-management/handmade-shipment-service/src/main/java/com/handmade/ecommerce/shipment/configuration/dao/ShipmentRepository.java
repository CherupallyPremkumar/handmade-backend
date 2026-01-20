package com.handmade.ecommerce.shipment.configuration.dao;

import com.handmade.ecommerce.order.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ShipmentRepository extends JpaRepository<Shipment,String> {}
