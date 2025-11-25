package com.handmade.ecommerce.cash.configuration.dao;

import com.handmade.ecommerce.cash.model.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface CashRepository extends JpaRepository<Cash,String> {}
