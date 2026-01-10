package com.handmade.ecommerce.promotion.experiment;

import com.handmade.ecommerce.promotion.experiment.entity.ExperimentAudience;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ExperimentAudience
 * Generated from entity file
 */
@Repository
public interface ExperimentAudienceRepository extends JpaRepository<ExperimentAudience, String> {
    
    List<ExperimentAudience> findByExperimentId(String experimentId);
    List<ExperimentAudience> findByRuleName(String ruleName);
    List<ExperimentAudience> findByPriority(String priority);
}
