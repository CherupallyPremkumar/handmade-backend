package com.homebase.ecom.repository;

import com.homebase.ecom.domain.TaxRate;
import com.homebase.ecom.entity.TaxRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<TaxRateEntity,String> {
    Optional<TaxRateEntity> findByCountryAndStateAndProductCategory(String country, String state, String productCategory);
}
