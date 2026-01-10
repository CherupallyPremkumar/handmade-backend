package com.handmade.ecommerce.platform.iam;

import com.handmade.ecommerce.platform.iam.entity.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Role
 * Generated from entity file
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    
    Optional<Role> findByName(String name);
    List<Role> findByTenantId(String tenantId);

    // Existence checks
    boolean existsByName(String name);
}
