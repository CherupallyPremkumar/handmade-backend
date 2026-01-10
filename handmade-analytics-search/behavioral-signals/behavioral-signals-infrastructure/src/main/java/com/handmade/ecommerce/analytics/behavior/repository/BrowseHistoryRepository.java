package com.handmade.ecommerce.analytics.behavior;

import com.handmade.ecommerce.analytics.behavior.entity.BrowseHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for BrowseHistory
 * Generated from entity file
 */
@Repository
public interface BrowseHistoryRepository extends JpaRepository<BrowseHistory, String> {
    
    List<BrowseHistory> findByUserId(String userId);
    List<BrowseHistory> findBySessionId(String sessionId);
    List<BrowseHistory> findByProductId(String productId);
}
