package com.handmade.ecommerce.inventorytransfer.configuration.dao;

import com.handmade.ecommerce.inventory.model.InventoryTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface InventoryTransferRepository extends JpaRepository<InventoryTransfer,String> {}
