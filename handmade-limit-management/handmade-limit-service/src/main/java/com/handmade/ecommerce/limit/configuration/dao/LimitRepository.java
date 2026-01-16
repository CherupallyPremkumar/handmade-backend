package com.handmade.ecommerce.limit.configuration.dao;

import com.handmade.ecommerce.limit.model.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface LimitRepository extends JpaRepository<Limit,String> {}
