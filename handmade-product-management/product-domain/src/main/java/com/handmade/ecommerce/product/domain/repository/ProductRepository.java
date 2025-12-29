package com.handmade.ecommerce.product.domain.repository;

import com.handmade.ecommerce.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {}
