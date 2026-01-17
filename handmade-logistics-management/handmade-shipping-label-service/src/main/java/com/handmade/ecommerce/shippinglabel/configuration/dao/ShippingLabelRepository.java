package com.handmade.ecommerce.shippinglabel.configuration.dao;

import com.handmade.ecommerce.logistics.model.ShippingLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ShippingLabelRepository extends JpaRepository<ShippingLabel,String> {}
