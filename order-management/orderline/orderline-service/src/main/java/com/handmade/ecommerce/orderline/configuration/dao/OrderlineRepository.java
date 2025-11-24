package com.handmade.ecommerce.orderline.configuration.dao;

import com.handmade.ecommerce.orderline.model.Orderline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface OrderlineRepository extends JpaRepository<Orderline,String> {}
