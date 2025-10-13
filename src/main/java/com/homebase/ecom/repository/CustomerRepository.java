package com.homebase.ecom.repository;

import com.homebase.ecom.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    
    List<CustomerEntity> findByTenant(String tenantId);
    
    Optional<CustomerEntity> findByIdAndTenant(String id, String tenantId);
    
    Optional<CustomerEntity> findByEmailAndTenant(String email, String tenantId);

    Optional<CustomerEntity> findByEmail(String email);
}
