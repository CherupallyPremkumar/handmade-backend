package com.handmade.ecommerce.domain.finance;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_fee_definition")
public class FeeDefinition extends BaseJpaEntity {

    @Column(name = "fee_code", length = 50, nullable = false, unique = true)
    private String feeCode;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Column(name = "fee_type", length = 50)
    private String feeType = "REFERRAL";

    @Lob
    @Column(name = "calculation_logic")
    private String calculationLogic; // JSON or script

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
