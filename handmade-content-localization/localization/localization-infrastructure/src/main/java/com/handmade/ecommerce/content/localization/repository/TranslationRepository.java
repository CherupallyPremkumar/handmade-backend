package com.handmade.ecommerce.content.localization;

import com.handmade.ecommerce.content.localization.entity.Translation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Translation
 * Generated from entity file
 */
@Repository
public interface TranslationRepository extends JpaRepository<Translation, String> {
    
    List<Translation> findByLanguageId(String languageId);
    List<Translation> findByTranslationKey(String translationKey);
    List<Translation> findByEntityType(String entityType);
    List<Translation> findByEntityId(String entityId);
}
