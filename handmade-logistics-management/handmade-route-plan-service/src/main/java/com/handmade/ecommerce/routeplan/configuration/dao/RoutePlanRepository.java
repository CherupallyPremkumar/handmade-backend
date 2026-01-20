package com.handmade.ecommerce.routeplan.configuration.dao;

import com.handmade.ecommerce.logistics.model.RoutePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface RoutePlanRepository extends JpaRepository<RoutePlan,String> {}
