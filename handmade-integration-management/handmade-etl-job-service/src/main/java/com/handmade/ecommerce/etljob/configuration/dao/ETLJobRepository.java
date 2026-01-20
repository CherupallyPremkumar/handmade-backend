package com.handmade.ecommerce.etljob.configuration.dao;

import com.handmade.ecommerce.integration.model.ETLJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ETLJobRepository extends JpaRepository<ETLJob,String> {}
