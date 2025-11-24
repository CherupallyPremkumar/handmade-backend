package com.handmade.ecommerce.variant.configuration.dao;

import com.handmade.ecommerce.variant.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface VariantRepository extends JpaRepository<Variant,String> {}
