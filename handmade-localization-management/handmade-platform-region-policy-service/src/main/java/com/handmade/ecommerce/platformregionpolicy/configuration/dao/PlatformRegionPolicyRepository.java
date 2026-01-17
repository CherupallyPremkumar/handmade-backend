package com.handmade.ecommerce.platformregionpolicy.configuration.dao;

import com.handmade.ecommerce.localization.model.PlatformRegionPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PlatformRegionPolicyRepository extends JpaRepository<PlatformRegionPolicy,String> {}
