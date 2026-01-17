package com.handmade.ecommerce.role.configuration.dao;

import com.handmade.ecommerce.iam.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface RoleRepository extends JpaRepository<Role,String> {}
