package com.handmade.ecommerce.policy.configuration.dao;

import com.handmade.ecommerce.policy.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PolicyRepository extends JpaRepository<Policy,String> {}
