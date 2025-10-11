package com.homebase.admin.repository;

import com.homebase.admin.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    
    List<Customer> findByTenantId(String tenantId);
    
    Optional<Customer> findByIdAndTenantId(String id, String tenantId);
    
    Optional<Customer> findByEmailAndTenantId(String email, String tenantId);

    Optional<Customer> findByEmail(String email);
}
