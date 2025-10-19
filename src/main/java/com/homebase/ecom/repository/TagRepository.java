package com.homebase.ecom.repository;

import com.homebase.ecom.domain.Tag;
import com.homebase.ecom.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, String> {
    
    List<TagEntity> findByTenant(String tenantId);
    
    Optional<TagEntity> findByIdAndTenant(String id, String tenantId);
    
    Optional<TagEntity> findBySlugAndTenant(String slug, String tenantId);
}
