package com.handmade.ecommerce.content.cms;

import com.handmade.ecommerce.content.cms.entity.ContentPage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ContentPage
 * Generated from entity file
 */
@Repository
public interface ContentPageRepository extends JpaRepository<ContentPage, String> {
    
    Optional<ContentPage> findBySlug(String slug);
    List<ContentPage> findByContentType(String contentType);
    List<ContentPage> findByStatus(String status);

    // Existence checks
    boolean existsBySlug(String slug);
}
