package com.handmade.ecommerce.orderline.configuration.dao;

import com.handmade.ecommerce.order.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface OrderLineRepository extends JpaRepository<OrderLine,String> {}
