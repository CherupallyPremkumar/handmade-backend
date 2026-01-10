package com.handmade.ecommerce.promotion.experiment;

import com.handmade.ecommerce.promotion.experiment.entity.ExperimentAssignment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ExperimentAssignment
 * Generated from entity file
 */
@Repository
public interface ExperimentAssignmentRepository extends JpaRepository<ExperimentAssignment, String> {
    
    List<ExperimentAssignment> findByExperimentKey(String experimentKey);
    List<ExperimentAssignment> findByVariantKey(String variantKey);
    List<ExperimentAssignment> findByEntityId(String entityId);
}
