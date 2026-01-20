package com.handmade.ecommerce.domain.seller;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * SellerActionItem - Action items for sellers to complete
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_action_item")
public class SellerActionItem extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "action_type", length = 50, nullable = false)
    private String actionType;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "severity", length = 20)
    private String severity;
}
