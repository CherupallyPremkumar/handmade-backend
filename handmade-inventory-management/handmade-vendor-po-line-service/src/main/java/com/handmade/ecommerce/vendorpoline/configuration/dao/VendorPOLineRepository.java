package com.handmade.ecommerce.vendorpoline.configuration.dao;

import com.handmade.ecommerce.inventory.model.VendorPOLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface VendorPOLineRepository extends JpaRepository<VendorPOLine,String> {}
