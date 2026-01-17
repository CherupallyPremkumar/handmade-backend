package com.handmade.ecommerce.inventoryreservation.configuration.dao;

import com.handmade.ecommerce.inventory.model.InventoryReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface InventoryReservationRepository extends JpaRepository<InventoryReservation,String> {}
