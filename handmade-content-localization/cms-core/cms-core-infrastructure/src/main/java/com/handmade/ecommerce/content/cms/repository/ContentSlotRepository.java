package com.handmade.ecommerce.content.cms;

import com.handmade.ecommerce.content.cms.entity.ContentSlot;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ContentSlot
 * Generated from entity file
 */
@Repository
public interface ContentSlotRepository extends JpaRepository<ContentSlot, String> {
    
    Optional<ContentSlot> findBySlotKey(String slotKey);
    List<ContentSlot> findByActiveAssetId(String activeAssetId);
    List<ContentSlot> findByPriority(String priority);
    List<ContentSlot> findByIsActive(Boolean isActive);

    // Existence checks
    boolean existsBySlotKey(String slotKey);
}
