package com.handmade.ecommerce.tax.configuration.dao;

import com.handmade.ecommerce.tax.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface TaxRepository extends JpaRepository<Tax,String> {}
