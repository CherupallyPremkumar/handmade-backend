package com.handmade.ecommerce.domain.governance;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_gdpr_request")
public class GDPRRequest extends AbstractJpaStateEntity {

    @Column(name = "user_id", length = 36, nullable = false)
    private String userId;

    @Column(name = "request_type", length = 50)
    private String requestType; // DATA_EXPORT, DATA_DELETION
    
    @Column(name = "requested_at")
    private Date requestedAt;

    @Column(name = "completed_at")
    private Date completedAt;

    @Column(name = "policy_id", length = 50)
    private String policyId;

    @Column(name = "policy_ack_id", length = 36)
    private String policyAckId;
}
