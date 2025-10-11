package com.homebase.admin.repository;

import com.homebase.admin.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, String> {

    // Find tenant by URL path (used in frontend)
    Optional<Tenant> findByUrlPath(String urlPath);
}