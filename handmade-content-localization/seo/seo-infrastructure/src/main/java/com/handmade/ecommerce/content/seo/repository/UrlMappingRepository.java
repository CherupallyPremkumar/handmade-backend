package com.handmade.ecommerce.content.seo;

import com.handmade.ecommerce.content.seo.entity.UrlMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for UrlMapping
 * Generated from entity file
 */
@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, String> {
    
    Optional<UrlMapping> findByCustomUrl(String customUrl);
    List<UrlMapping> findByMappingType(String mappingType);

    // Existence checks
    boolean existsByCustomUrl(String customUrl);
}
