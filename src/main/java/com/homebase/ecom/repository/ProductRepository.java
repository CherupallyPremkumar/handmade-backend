package com.homebase.ecom.repository;


import com.homebase.ecom.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    
    List<ProductEntity> findByTenant(String tenantId);
    
    Optional<ProductEntity> findByIdAndTenant(String id, String tenantId);
    
    List<ProductEntity> findByCategoryAndTenant(String category, String tenantId);
    
    List<ProductEntity> findByFeaturedAndTenant(Boolean featured, String tenantId);
    
    @Query("SELECT p FROM ProductEntity p WHERE p.tenant = ?1 AND p.stock < ?2")
    List<ProductEntity> findLowStockProducts(String tenantId, Integer threshold);
    
    Long countByTenant(String tenantId);
}
