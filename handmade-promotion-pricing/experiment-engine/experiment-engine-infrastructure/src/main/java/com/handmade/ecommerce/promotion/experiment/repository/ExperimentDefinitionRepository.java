package com.handmade.ecommerce.promotion.experiment;

import com.handmade.ecommerce.promotion.experiment.entity.ExperimentDefinition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ExperimentDefinition
 * Generated from entity file
 */
@Repository
public interface ExperimentDefinitionRepository extends JpaRepository<ExperimentDefinition, String> {
    
    Optional<ExperimentDefinition> findByExperimentKey(String experimentKey);
    List<ExperimentDefinition> findByExperimentName(String experimentName);
    List<ExperimentDefinition> findByStatus(String status);
    List<ExperimentDefinition> findByType(String type);

    // Existence checks
    boolean existsByExperimentKey(String experimentKey);
}
