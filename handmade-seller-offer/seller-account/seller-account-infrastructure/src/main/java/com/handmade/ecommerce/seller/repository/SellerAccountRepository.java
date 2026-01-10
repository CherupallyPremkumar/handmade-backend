package com.handmade.ecommerce.seller;

import com.handmade.ecommerce.seller.entity.SellerAccount;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerAccount
 * Generated from entity file
 */
@Repository
public interface SellerAccountRepository extends JpaRepository<SellerAccount, String> {
    
    List<SellerAccount> findByPlatformId(String platformId);
    Optional<SellerAccount> findByEmail(String email);
    List<SellerAccount> findByBusinessName(String businessName);
    List<SellerAccount> findByOnboardingPolicyId(String onboardingPolicyId);

    // STM State queries
    @Query("SELECT e FROM SellerAccount e WHERE e.state.stateId = :stateId")
    List<SellerAccount> findByStateId(@Param("stateId") String stateId);

    @Query("SELECT e FROM SellerAccount e WHERE e.state.flowId = :flowId")
    List<SellerAccount> findByFlowId(@Param("flowId") String flowId);

    @Query("SELECT e FROM SellerAccount e WHERE e.state.flowId = :flowId AND e.state.stateId = :stateId")
    List<SellerAccount> findByFlowIdAndStateId(@Param("flowId") String flowId, @Param("stateId") String stateId);

    // Existence checks
    boolean existsByEmail(String email);
}
