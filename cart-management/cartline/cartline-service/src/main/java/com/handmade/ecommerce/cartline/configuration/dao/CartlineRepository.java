package com.handmade.ecommerce.cartline.configuration.dao;

import com.handmade.ecommerce.cartline.model.Cartline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface CartlineRepository extends JpaRepository<Cartline,String> {}
