package com.handmade.ecommerce.payout.configuration.dao;

import com.handmade.ecommerce.finance.model.Payout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PayoutRepository extends JpaRepository<Payout,String> {}
