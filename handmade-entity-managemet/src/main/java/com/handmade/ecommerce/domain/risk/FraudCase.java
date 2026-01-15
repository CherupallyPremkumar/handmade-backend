package com.handmade.ecommerce.domain.risk;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_fraud_case")
public class FraudCase extends AbstractJpaStateEntity {

    @Column(name = "reference_id", length = 36, nullable = false)
    private String referenceId; // ID of the entity under investigation

    @Column(name = "status", length = 20, nullable = false)
    private String status = "OPEN";

    @Column(name = "investigator_id", length = 36)
    private String investigatorId;

    @Column(name = "outcome", length = 50)
    private String outcome; // FRAUD_CONFIRMED, FALSE_POSITIVE
}
