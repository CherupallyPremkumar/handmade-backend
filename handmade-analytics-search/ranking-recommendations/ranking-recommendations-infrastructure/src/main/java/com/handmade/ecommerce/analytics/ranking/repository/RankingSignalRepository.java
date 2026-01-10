package com.handmade.ecommerce.analytics.ranking;

import com.handmade.ecommerce.analytics.ranking.entity.RankingSignal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for RankingSignal
 * Generated from entity file
 */
@Repository
public interface RankingSignalRepository extends JpaRepository<RankingSignal, String> {
    
    List<RankingSignal> findByEntityId(String entityId);
    List<RankingSignal> findBySignalType(String signalType);
}
