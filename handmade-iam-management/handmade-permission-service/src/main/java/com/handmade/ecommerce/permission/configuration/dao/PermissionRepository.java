package com.handmade.ecommerce.permission.configuration.dao;

import com.handmade.ecommerce.iam.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PermissionRepository extends JpaRepository<Permission,String> {}
