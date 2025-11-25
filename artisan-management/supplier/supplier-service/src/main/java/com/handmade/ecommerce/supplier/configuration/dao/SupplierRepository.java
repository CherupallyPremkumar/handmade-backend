package com.handmade.ecommerce.supplier.configuration.dao;

import com.handmade.ecommerce.supplier.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface SupplierRepository extends JpaRepository<Supplier,String> {}
