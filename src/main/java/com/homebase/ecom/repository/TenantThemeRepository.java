package com.homebase.ecom.repository;

import com.homebase.ecom.entity.TenantThemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantThemeRepository extends JpaRepository<TenantThemeEntity, String> {
}
