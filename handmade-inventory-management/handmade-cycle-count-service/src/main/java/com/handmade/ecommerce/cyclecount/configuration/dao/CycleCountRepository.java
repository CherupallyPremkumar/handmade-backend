package com.handmade.ecommerce.cyclecount.configuration.dao;

import com.handmade.ecommerce.inventory.model.CycleCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface CycleCountRepository extends JpaRepository<CycleCount,String> {}
