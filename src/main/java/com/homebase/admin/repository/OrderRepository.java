package com.homebase.admin.repository;

import com.homebase.admin.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByTenantId(String tenantId);
    
    Optional<Order> findByIdAndTenantId(Long id, String tenantId);
    
    List<Order> findByStatusAndTenantId(Order.OrderStatus status, String tenantId);
    
    Long countByTenantId(String tenantId);
    
    @Query("SELECT SUM(o.total) FROM Order o WHERE o.tenantId = ?1")
    BigDecimal getTotalSalesByTenant(String tenantId);
}
