package com.handmade.ecommerce.observability.configuration.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ObservabilityRepository extends JpaRepository<Observability,String> {}
