package com.handmade.ecommerce.platform.configuration.dao;

import com.handmade.ecommerce.platform.domain.entity.PlatformFeatureConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatformFeatureConfigRepository extends JpaRepository<PlatformFeatureConfig, String> {
    List<PlatformFeatureConfig> findByPlatformIdOrderByVersionDesc(String platformId);
}
