package com.handmade.ecommerce.stripe.configuration.dao;

import com.handmade.ecommerce.stripe.model.Stripe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface StripeRepository extends JpaRepository<Stripe,String> {}
