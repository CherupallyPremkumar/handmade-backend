package com.handmade.ecommerce.support.configuration.dao;

import com.handmade.ecommerce.support.model.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface SupportRepository extends JpaRepository<Support,String> {}
