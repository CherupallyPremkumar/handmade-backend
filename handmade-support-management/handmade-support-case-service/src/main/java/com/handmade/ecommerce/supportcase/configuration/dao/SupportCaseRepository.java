package com.handmade.ecommerce.supportcase.configuration.dao;

import com.handmade.ecommerce.support.model.SupportCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface SupportCaseRepository extends JpaRepository<SupportCase,String> {}
