package com.homebase.admin.repository;

import com.homebase.admin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    
    List<Category> findByTenantId(String tenantId);
    
    Optional<Category> findByIdAndTenantId(String id, String tenantId);
    
    Optional<Category> findBySlugAndTenantId(String slug, String tenantId);
}
