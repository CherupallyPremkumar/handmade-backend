package com.handmade.ecommerce.product.configuration.dao;

import com.handmade.ecommerce.product.domain.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantRepository extends JpaRepository<Variant, String> {
}
