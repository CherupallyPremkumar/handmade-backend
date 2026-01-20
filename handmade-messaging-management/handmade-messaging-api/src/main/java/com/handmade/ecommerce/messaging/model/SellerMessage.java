package com.handmade.ecommerce.messaging.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * SellerMessage - Individual messages within a conversation
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_message")
public class SellerMessage extends BaseJpaEntity {

    @Column(name = "conversation_id", length = 36, nullable = false)
    private String conversationId;

    @Column(name = "sender_id", length = 36, nullable = false)
    private String senderId;

    @Column(name = "sender_type", length = 50, nullable = false)
    private String senderType; // CUSTOMER, SELLER, SUPPORT

    @Lob
    @Column(name = "message_text", nullable = false)
    private String messageText;

    @Column(name = "has_attachment")
    private Boolean hasAttachment = false;

    @Column(name = "attachment_url", length = 2048)
    private String attachmentUrl;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "read_at")
    private Date readAt;

    @Column(name = "sent_at", nullable = false)
    private Date sentAt;
}
