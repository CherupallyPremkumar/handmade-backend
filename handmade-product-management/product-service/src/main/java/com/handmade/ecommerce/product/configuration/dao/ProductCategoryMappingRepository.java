package com.handmade.ecommerce.product.repository;

import com.handmade.ecommerce.product.domain.model.ProductCategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryMappingRepository extends JpaRepository<ProductCategoryMapping, Long> {

    List<ProductCategoryMapping> findByProductId(String productId);

    void deleteByProductId(String productId);
}
