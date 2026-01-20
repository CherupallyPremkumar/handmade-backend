package com.handmade.ecommerce.tax.configuration.dao;

import com.handmade.ecommerce.tax.model.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository   public interface TaxRateRepository extends JpaRepository<TaxRate,String> {}
