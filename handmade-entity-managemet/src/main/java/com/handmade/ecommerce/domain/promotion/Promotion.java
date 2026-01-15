package com.handmade.ecommerce.domain.promotion;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * Promotion - Core promotion entity managed by STM
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_promotion")
public class Promotion extends AbstractJpaStateEntity {

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "promotion_type", length = 50, nullable = false)
    private String promotionType; // PERCENTAGE, FIXED_AMOUNT, BOGO

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "exclusive")
    private Boolean exclusive = false;
}
