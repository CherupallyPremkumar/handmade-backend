package com.handmade.ecommerce.inventory.domain.repository;

import com.handmade.ecommerce.inventory.domain.aggregate.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, String> {
    List<InventoryTransaction> findByInventoryId(String inventoryId);
}
