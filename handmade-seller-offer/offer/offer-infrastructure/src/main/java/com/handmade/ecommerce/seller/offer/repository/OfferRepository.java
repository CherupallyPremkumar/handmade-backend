package com.handmade.ecommerce.seller.offer;

import com.handmade.ecommerce.seller.offer.entity.Offer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for Offer
 * Generated from entity file
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {
    
    List<Offer> findByPlatformId(String platformId);
    List<Offer> findByProductId(String productId);
    List<Offer> findBySellerId(String sellerId);
    List<Offer> findByConditionType(String conditionType);
    List<Offer> findByTaxCode(String taxCode);

    // STM State queries
    @Query("SELECT e FROM Offer e WHERE e.state.stateId = :stateId")
    List<Offer> findByStateId(@Param("stateId") String stateId);

    @Query("SELECT e FROM Offer e WHERE e.state.flowId = :flowId")
    List<Offer> findByFlowId(@Param("flowId") String flowId);

    @Query("SELECT e FROM Offer e WHERE e.state.flowId = :flowId AND e.state.stateId = :stateId")
    List<Offer> findByFlowIdAndStateId(@Param("flowId") String flowId, @Param("stateId") String stateId);
}
