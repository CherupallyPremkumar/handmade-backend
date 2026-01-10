package com.handmade.ecommerce.customer;

import com.handmade.ecommerce.customer.entity.Subscription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Subscription
 * Generated from entity file
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
    
    List<Subscription> findByCustomerId(String customerId);
    List<Subscription> findByPlanType(String planType);
    List<Subscription> findByStatus(String status);
}
