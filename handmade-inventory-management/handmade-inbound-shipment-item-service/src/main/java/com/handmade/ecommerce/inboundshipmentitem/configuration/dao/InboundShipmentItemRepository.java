package com.handmade.ecommerce.inboundshipmentitem.configuration.dao;

import com.handmade.ecommerce.inventory.model.InboundShipmentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface InboundShipmentItemRepository extends JpaRepository<InboundShipmentItem,String> {}
