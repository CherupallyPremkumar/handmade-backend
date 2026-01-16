package com.handmade.ecommerce.integration.configuration.dao;

import com.handmade.ecommerce.integration.model.Integration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface IntegrationRepository extends JpaRepository<Integration,String> {}
