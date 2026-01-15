package com.handmade.ecommerce.domain.analytics;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * BrowseHistory - User product browse history
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_browse_history")
public class BrowseHistory extends BaseJpaEntity {

    @Column(name = "user_id", length = 36)
    private String userId;

    @Column(name = "session_id", length = 100)
    private String sessionId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "viewed_at", nullable = false)
    private Date viewedAt;
}
