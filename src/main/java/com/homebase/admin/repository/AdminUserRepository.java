package com.homebase.admin.repository;

import com.homebase.admin.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    
    Optional<AdminUser> findByEmailAndTenantId(String email, String tenantId);
    
    Optional<AdminUser> findByEmail(String email);
    
    Boolean existsByEmailAndTenantId(String email, String tenantId);
}
