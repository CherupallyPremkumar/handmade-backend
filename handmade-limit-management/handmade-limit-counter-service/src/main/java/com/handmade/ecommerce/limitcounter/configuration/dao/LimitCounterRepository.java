package com.handmade.ecommerce.limitcounter.configuration.dao;

import com.handmade.ecommerce.limit.model.LimitCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface LimitCounterRepository extends JpaRepository<LimitCounter,String> {}
