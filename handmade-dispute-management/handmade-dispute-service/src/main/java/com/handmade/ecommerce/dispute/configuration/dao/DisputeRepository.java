package com.handmade.ecommerce.dispute.configuration.dao;

import com.handmade.ecommerce.dispute.model.Dispute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface DisputeRepository extends JpaRepository<Dispute,String> {}
