package com.handmade.ecommerce.refund.configuration.dao;

import com.handmade.ecommerce.order.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface RefundRepository extends JpaRepository<Refund,String> {}
