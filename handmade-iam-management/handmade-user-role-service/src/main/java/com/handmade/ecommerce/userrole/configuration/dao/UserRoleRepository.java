package com.handmade.ecommerce.userrole.configuration.dao;

import com.handmade.ecommerce.iam.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface UserRoleRepository extends JpaRepository<UserRole,String> {}
