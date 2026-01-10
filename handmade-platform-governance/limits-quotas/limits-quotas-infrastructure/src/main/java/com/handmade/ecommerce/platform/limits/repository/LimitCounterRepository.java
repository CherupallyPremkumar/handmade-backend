package com.handmade.ecommerce.platform.limits;

import com.handmade.ecommerce.platform.limits.entity.LimitCounter;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for LimitCounter
 * Generated from entity file
 */
@Repository
public interface LimitCounterRepository extends JpaRepository<LimitCounter, String> {
    
    List<LimitCounter> findByEntityId(String entityId);
    List<LimitCounter> findByLimitKey(String limitKey);
}
