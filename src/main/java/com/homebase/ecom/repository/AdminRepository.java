package com.homebase.ecom.repository;

import com.homebase.ecom.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, String> {
    
    Optional<AdminEntity> findByEmailAndTenant(String email, String tenantId);
    
    Optional<AdminEntity> findByEmail(String email);
    
    Boolean existsByEmailAndTenant(String email, String tenantId);
}
