package com.handmade.ecommerce.domain.loyalty;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_loyalty_program")
public class LoyaltyProgram extends BaseJpaEntity {

    @Column(name = "program_name", length = 100, nullable = false)
    private String programName;

    @Column(name = "tier_level")
    private Integer tierLevel = 1;

    @Column(name = "benefits_json", columnDefinition = "TEXT")
    private String benefitsJson;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
