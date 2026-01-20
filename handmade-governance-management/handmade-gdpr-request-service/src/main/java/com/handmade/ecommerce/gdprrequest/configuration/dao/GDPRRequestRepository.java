package com.handmade.ecommerce.gdprrequest.configuration.dao;

import com.handmade.ecommerce.governance.model.GDPRRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface GDPRRequestRepository extends JpaRepository<GDPRRequest,String> {}
