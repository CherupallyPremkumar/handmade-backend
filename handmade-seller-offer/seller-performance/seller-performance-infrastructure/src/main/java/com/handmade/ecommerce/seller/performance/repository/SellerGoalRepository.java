package com.handmade.ecommerce.seller.performance;

import com.handmade.ecommerce.seller.performance.entity.SellerGoal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SellerGoal
 * Generated from entity file
 */
@Repository
public interface SellerGoalRepository extends JpaRepository<SellerGoal, String> {
    
    List<SellerGoal> findBySellerId(String sellerId);
    List<SellerGoal> findByGoalType(String goalType);
    List<SellerGoal> findByStatus(String status);
}
