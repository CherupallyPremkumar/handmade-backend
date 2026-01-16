package com.handmade.ecommerce.catalog.configuration.dao;

import com.handmade.ecommerce.catalog.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface CatalogRepository extends JpaRepository<Catalog,String> {}
