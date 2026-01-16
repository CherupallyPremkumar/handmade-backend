package com.handmade.ecommerce.qa.configuration.dao;

import com.handmade.ecommerce.qa.model.Qa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface QaRepository extends JpaRepository<Qa,String> {}
