package com.handmade.ecommerce.content.localization;

import com.handmade.ecommerce.content.localization.entity.Language;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Language
 * Generated from entity file
 */
@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {
    
    List<Language> findByLanguageCode(String languageCode);
    List<Language> findByNativeName(String nativeName);
    List<Language> findByIsActive(Boolean isActive);
}
