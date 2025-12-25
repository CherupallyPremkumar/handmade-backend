package com.handmade.ecommerce.platform.configuration.dao;

import com.handmade.ecommerce.platform.domain.entity.CommissionPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommissionPolicyRepository extends JpaRepository<CommissionPolicy, String> {
    
    // Find active policy: effectiveFrom <= Now AND (effectiveTo > Now OR effectiveTo IS NULL)
    // Ordered by version desc to get the latest if multiple match (though logic should prevent overlap)
    List<CommissionPolicy> findByPlatformIdOrderByPolicyVersionDesc(String platformId);

}
