package com.handmade.ecommerce.domain.support;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * OrderNote - Internal notes attached to orders for support
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_order_note")
public class OrderNote extends BaseJpaEntity {

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "author_id", length = 36, nullable = false)
    private String authorId; // Support Agent ID

    @Lob
    @Column(name = "note_text", nullable = false)
    private String noteText;

    @Column(name = "is_internal")
    private Boolean isInternal = true; // False if visible to customer
}
