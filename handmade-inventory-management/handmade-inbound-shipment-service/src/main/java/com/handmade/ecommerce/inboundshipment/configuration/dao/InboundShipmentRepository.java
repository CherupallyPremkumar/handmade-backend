package com.handmade.ecommerce.inboundshipment.configuration.dao;

import com.handmade.ecommerce.inventory.model.InboundShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface InboundShipmentRepository extends JpaRepository<InboundShipment,String> {}
