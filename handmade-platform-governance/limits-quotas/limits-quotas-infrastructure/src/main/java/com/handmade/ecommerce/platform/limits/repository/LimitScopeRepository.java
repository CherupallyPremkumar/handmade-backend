package com.handmade.ecommerce.platform.limits;

import com.handmade.ecommerce.platform.limits.entity.LimitScope;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for LimitScope
 * Generated from entity file
 */
@Repository
public interface LimitScopeRepository extends JpaRepository<LimitScope, String> {
    
    List<LimitScope> findByLimitId(String limitId);
    List<LimitScope> findByPriority(String priority);
}
