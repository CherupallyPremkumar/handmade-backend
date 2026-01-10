package com.handmade.ecommerce.seller;

import com.handmade.ecommerce.seller.entity.SellerStore;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerStore
 * Generated from entity file
 */
@Repository
public interface SellerStoreRepository extends JpaRepository<SellerStore, String> {
    
    List<SellerStore> findByPlatformId(String platformId);
    List<SellerStore> findBySellerAccountId(String sellerAccountId);
    Optional<SellerStore> findBySellerCode(String sellerCode);
    List<SellerStore> findBySellerName(String sellerName);
    List<SellerStore> findByDisplayName(String displayName);
    Optional<SellerStore> findByUrlPath(String urlPath);
    List<SellerStore> findBySupportEmail(String supportEmail);
    List<SellerStore> findBySupportPhone(String supportPhone);
    List<SellerStore> findByContactEmail(String contactEmail);
    List<SellerStore> findByContactPhone(String contactPhone);
    List<SellerStore> findByConfigurationId(String configurationId);
    List<SellerStore> findByBusinessType(String businessType);

    // STM State queries
    @Query("SELECT e FROM SellerStore e WHERE e.state.stateId = :stateId")
    List<SellerStore> findByStateId(@Param("stateId") String stateId);

    @Query("SELECT e FROM SellerStore e WHERE e.state.flowId = :flowId")
    List<SellerStore> findByFlowId(@Param("flowId") String flowId);

    @Query("SELECT e FROM SellerStore e WHERE e.state.flowId = :flowId AND e.state.stateId = :stateId")
    List<SellerStore> findByFlowIdAndStateId(@Param("flowId") String flowId, @Param("stateId") String stateId);

    // Existence checks
    boolean existsBySellerCode(String sellerCode);
    boolean existsByUrlPath(String urlPath);
}
