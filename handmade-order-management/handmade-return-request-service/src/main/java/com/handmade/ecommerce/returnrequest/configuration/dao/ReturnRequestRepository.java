package com.handmade.ecommerce.returnrequest.configuration.dao;

import com.handmade.ecommerce.order.model.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ReturnRequestRepository extends JpaRepository<ReturnRequest,String> {}
