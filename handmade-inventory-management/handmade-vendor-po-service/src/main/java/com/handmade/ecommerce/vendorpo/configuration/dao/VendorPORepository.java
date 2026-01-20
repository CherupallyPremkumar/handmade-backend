package com.handmade.ecommerce.vendorpo.configuration.dao;

import com.handmade.ecommerce.inventory.model.VendorPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface VendorPORepository extends JpaRepository<VendorPO,String> {}
