package com.homebase.admin.repository;

import com.homebase.admin.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    
    List<Product> findByTenantId(String tenantId);
    
    Optional<Product> findByIdAndTenantId(String id, String tenantId);
    
    List<Product> findByCategoryAndTenantId(String category, String tenantId);
    
    List<Product> findByFeaturedAndTenantId(Boolean featured, String tenantId);
    
    @Query("SELECT p FROM Product p WHERE p.tenantId = ?1 AND p.stock < ?2")
    List<Product> findLowStockProducts(String tenantId, Integer threshold);
    
    Long countByTenantId(String tenantId);
}
