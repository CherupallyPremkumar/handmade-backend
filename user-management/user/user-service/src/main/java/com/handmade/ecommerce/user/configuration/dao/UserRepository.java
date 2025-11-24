package com.handmade.ecommerce.user.configuration.dao;

import com.handmade.ecommerce.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface UserRepository extends JpaRepository<User,String> {}
