package com.handmade.ecommerce.platform.configuration.dao;

import com.handmade.ecommerce.platform.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PlatformRepository extends JpaRepository<Platform,String> {}
