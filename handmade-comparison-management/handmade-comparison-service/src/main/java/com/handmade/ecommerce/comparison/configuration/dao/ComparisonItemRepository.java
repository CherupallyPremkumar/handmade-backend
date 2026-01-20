package com.handmade.ecommerce.comparison.configuration.dao;

import com.handmade.ecommerce.comparison.model.ComparisonItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ComparisonItemRepository extends JpaRepository<ComparisonItem,String> {}
