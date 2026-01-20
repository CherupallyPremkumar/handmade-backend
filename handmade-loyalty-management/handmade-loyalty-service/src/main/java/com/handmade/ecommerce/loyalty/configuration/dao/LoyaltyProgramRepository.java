package com.handmade.ecommerce.loyalty.configuration.dao;

import com.handmade.ecommerce.loyalty.model.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram,String> {}
