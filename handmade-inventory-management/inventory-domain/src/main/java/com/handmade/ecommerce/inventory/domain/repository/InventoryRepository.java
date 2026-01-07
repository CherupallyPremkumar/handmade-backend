package com.handmade.ecommerce.inventory.domain.repository;

import com.handmade.ecommerce.inventory.domain.aggregate.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
    Optional<Inventory> findByVariantIdAndSellerId(String variantId, String sellerId);
}
