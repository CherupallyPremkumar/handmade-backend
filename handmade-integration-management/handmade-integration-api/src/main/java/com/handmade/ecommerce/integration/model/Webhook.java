package com.handmade.ecommerce.integration.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_webhook")
public class Webhook extends BaseJpaEntity {

    @Column(name = "target_url", length = 1000, nullable = false)
    private String targetUrl;

    @Column(name = "event_type", length = 100)
    private String eventType; // ORDER_CREATED, PRODUCT_UPDATED

    @Column(name = "secret_key")
    private String secretKey;

    @Column(name = "status", length = 20)
    private String status = "ACTIVE";
}
