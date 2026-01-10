package com.handmade.ecommerce.seller.messaging.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_seller_message
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_seller_message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerMessage extends BaseJpaEntity {
    
    @Column(name = "conversation_id", nullable = false, length = 36)
    private String conversationId;
    @Column(name = "sender_id", nullable = false, length = 36)
    private String senderId;
    @Column(name = "sender_type", nullable = false, length = 50)
    private String senderType;
    @Column(name = "message_text", nullable = false)
    private String messageText;
    @Column(name = "has_attachment")
    private Boolean hasAttachment;
    @Column(name = "attachment_url", length = 2048)
    private String attachmentUrl;
    @Column(name = "is_read")
    private Boolean isRead;
    @Column(name = "read_at")
    private Date readAt;
    @Column(name = "sent_at", nullable = false)
    private Date sentAt;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
