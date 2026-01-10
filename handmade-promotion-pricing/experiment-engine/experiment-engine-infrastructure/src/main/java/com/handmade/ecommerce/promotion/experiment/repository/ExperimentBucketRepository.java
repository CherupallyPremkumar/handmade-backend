package com.handmade.ecommerce.promotion.experiment;

import com.handmade.ecommerce.promotion.experiment.entity.ExperimentBucket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ExperimentBucket
 * Generated from entity file
 */
@Repository
public interface ExperimentBucketRepository extends JpaRepository<ExperimentBucket, String> {
    
    List<ExperimentBucket> findByExperimentId(String experimentId);
    List<ExperimentBucket> findByVariantKey(String variantKey);
}
