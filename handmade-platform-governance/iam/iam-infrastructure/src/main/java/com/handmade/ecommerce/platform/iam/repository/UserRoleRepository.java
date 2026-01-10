package com.handmade.ecommerce.platform.iam;

import com.handmade.ecommerce.platform.iam.entity.UserRole;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for UserRole
 * Generated from entity file
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    
    List<UserRole> findByUserId(String userId);
    List<UserRole> findByRoleId(String roleId);
}
