package com.homebase.ecom.repository;

import com.homebase.ecom.domain.Inventory;
import com.homebase.ecom.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, String> {
}
