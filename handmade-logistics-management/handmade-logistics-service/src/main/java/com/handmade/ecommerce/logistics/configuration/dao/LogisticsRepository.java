package com.handmade.ecommerce.logistics.configuration.dao;

import com.handmade.ecommerce.logistics.model.Logistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface LogisticsRepository extends JpaRepository<Logistics,String> {}
