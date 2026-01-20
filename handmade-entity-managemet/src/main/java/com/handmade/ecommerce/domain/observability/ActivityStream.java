package com.handmade.ecommerce.domain.observability;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_activity_stream")
public class ActivityStream extends BaseJpaEntity {

    @Column(name = "actor_id", length = 100)
    private String actorId;

    @Column(name = "actor_type", length = 50)
    private String actorType; // USER, SYSTEM, SELLER

    @Column(name = "action", length = 100, nullable = false)
    private String action;

    @Column(name = "entity_id", length = 100)
    private String entityId;

    @Column(name = "entity_type", length = 50)
    private String entityType;

    @Lob
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;
}
