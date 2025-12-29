package com.handmade.ecommerce.product.domain.repository;

import com.handmade.ecommerce.product.domain.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface VariantRepository extends JpaRepository<Variant,String> {}
