package com.handmade.ecommerce.catalog.configuration.dao;

import com.handmade.ecommerce.catalog.model.CatalogItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository  public interface CatalogItemRepository extends JpaRepository<CatalogItem,String> {}
