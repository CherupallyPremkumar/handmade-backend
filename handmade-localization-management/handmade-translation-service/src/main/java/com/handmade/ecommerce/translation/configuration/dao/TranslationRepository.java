package com.handmade.ecommerce.translation.configuration.dao;

import com.handmade.ecommerce.localization.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface TranslationRepository extends JpaRepository<Translation,String> {}
