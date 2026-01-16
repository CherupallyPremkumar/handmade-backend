package com.handmade.ecommerce.localization.configuration.dao;

import com.handmade.ecommerce.localization.model.Localization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface LocalizationRepository extends JpaRepository<Localization,String> {}
