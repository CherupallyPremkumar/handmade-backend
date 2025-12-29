package com.handmade.ecommerce.catalog.repository;

import com.handmade.ecommerce.catalog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("SELECT c FROM Category c WHERE c.parentId IS NULL AND c.active = true ORDER BY c.displayOrder")
    List<Category> findRootCategories();

    @Query("SELECT c FROM Category c WHERE c.parentId = :parentId AND c.active = true ORDER BY c.displayOrder")
    List<Category> findByParentId(String parentId);

    List<Category> findByActiveTrue();
}
