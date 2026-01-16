package com.handmade.ecommerce.governance.configuration.dao;

import com.handmade.ecommerce.governance.model.Governance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface GovernanceRepository extends JpaRepository<Governance,String> {}
