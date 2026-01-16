package com.handmade.ecommerce.subscription.configuration.dao;

import com.handmade.ecommerce.customer.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface SubscriptionRepository extends JpaRepository<Subscription,String> {}
