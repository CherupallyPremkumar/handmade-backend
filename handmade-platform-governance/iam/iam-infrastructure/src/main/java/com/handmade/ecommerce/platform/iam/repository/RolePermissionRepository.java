package com.handmade.ecommerce.platform.iam;

import com.handmade.ecommerce.platform.iam.entity.RolePermission;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for RolePermission
 * Generated from entity file
 */
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, String> {
    
    List<RolePermission> findByRoleId(String roleId);
    List<RolePermission> findByPermissionId(String permissionId);
}
