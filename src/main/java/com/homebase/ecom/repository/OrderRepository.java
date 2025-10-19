package com.homebase.ecom.repository;

import com.homebase.ecom.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    
    List<OrderEntity> findByTenant(String tenantId);
    
    Optional<OrderEntity> findByOrderNumberAndTenant(String orderNumber, String tenantId);

    
    List<OrderEntity> findByTenantAndStatus(String tenantId, OrderEntity.OrderStatus status);
    
    Optional<OrderEntity> findByIdAndTenant(String id, String tenantId);
    
    List<OrderEntity> findByStatusAndTenant(OrderEntity.OrderStatus status, String tenantId);
    
    Long countByTenant(String tenantId);
    @Query("SELECT SUM(o.total) FROM OrderEntity o WHERE o.tenant = ?1")
    BigDecimal getTotalSalesByTenant(String tenantId);
}
