package com.handmade.ecommerce.platform.iam;

import com.handmade.ecommerce.platform.iam.entity.Permission;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Permission
 * Generated from entity file
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    
    Optional<Permission> findByName(String name);

    // Existence checks
    boolean existsByName(String name);
}
