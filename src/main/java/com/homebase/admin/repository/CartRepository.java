package com.homebase.admin.repository;

import com.homebase.admin.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserIdAndTenantId(Long userId, String tenantId);
    Optional<Cart> findByIdAndUserIdAndTenantId(Long id, Long userId, String tenantId);
    Optional<Cart> findByUserIdAndProductIdAndTenantId(Long userId, Long productId, String tenantId);
    void deleteByUserIdAndTenantId(Long userId, String tenantId);
}
