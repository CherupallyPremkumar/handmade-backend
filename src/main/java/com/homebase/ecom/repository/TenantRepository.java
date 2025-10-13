package com.homebase.ecom.repository;

import com.homebase.ecom.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<TenantEntity, String> {

    // Find tenant by URL path (used in frontend)
    Optional<TenantEntity> findByUrlPath(String urlPath);
}