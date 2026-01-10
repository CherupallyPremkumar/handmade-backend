package com.handmade.ecommerce.analytics.ranking;

import com.handmade.ecommerce.analytics.ranking.entity.RecommendationGraph;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for RecommendationGraph
 * Generated from entity file
 */
@Repository
public interface RecommendationGraphRepository extends JpaRepository<RecommendationGraph, String> {
    
    List<RecommendationGraph> findBySourceEntityId(String sourceEntityId);
    List<RecommendationGraph> findByTargetEntityId(String targetEntityId);
    List<RecommendationGraph> findByRelationType(String relationType);
}
