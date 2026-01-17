package com.handmade.ecommerce.inventoryadjustment.configuration.dao;

import com.handmade.ecommerce.inventory.model.InventoryAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface InventoryAdjustmentRepository extends JpaRepository<InventoryAdjustment,String> {}
