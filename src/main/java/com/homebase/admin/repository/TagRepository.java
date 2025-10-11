package com.homebase.admin.repository;

import com.homebase.admin.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    
    List<Tag> findByTenantId(String tenantId);
    
    Optional<Tag> findByIdAndTenantId(String id, String tenantId);
    
    Optional<Tag> findBySlugAndTenantId(String slug, String tenantId);
}
