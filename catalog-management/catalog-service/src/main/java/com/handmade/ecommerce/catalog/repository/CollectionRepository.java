package com.handmade.ecommerce.catalog.repository;

import com.handmade.ecommerce.catalog.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, String> {

    @Query("SELECT c FROM Collection c WHERE c.active = true AND c.autoUpdate = true")
    List<Collection> findAllActiveDynamicCollections();
}
