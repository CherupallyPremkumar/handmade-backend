package com.handmade.ecommerce.payment.model;

import lombok.Getter;
import lombok.Setter;
import org.chenile.workflow.activities.model.ActivityEnabledStateEntity;
import org.chenile.workflow.activities.model.ActivityLog;

import java.math.BigDecimal;
import java.util.*;
import com.handmade.ecommerce.payment.model.PaymentActivityLog;
import org.chenile.workflow.model.*;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

@Entity
@Table(name = "payment")
@Getter
@Setter
public class Payment extends AbstractJpaStateEntity
        implements ActivityEnabledStateEntity,
        ContainsTransientMap {
    private String checkoutId;
    private String buyerId;
    private BigDecimal totalAmount;
    private String currency;
    private Boolean isPaymentDone = false;

    @Column(name = "idempotency_key", unique = true)
    private String idempotencyKey; // For duplicate request prevention

    // Relationship to PSP session details
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_session_id", referencedColumnName = "id")
    private PaymentSession paymentSession;

    @Transient
    public TransientMap transientMap = new TransientMap();

    public TransientMap getTransientMap() {
        return this.transientMap;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    public List<PaymentActivityLog> activities = new ArrayList<>();

    @Override
    public Collection<ActivityLog> obtainActivities() {
        Collection<ActivityLog> acts = new ArrayList<>();
        for (ActivityLog a : activities) {
            acts.add(a);
        }
        return acts;
    }

    @Override
    public ActivityLog addActivity(String eventId, String comment) {
        PaymentActivityLog activityLog = new PaymentActivityLog();
        activityLog.activityName = eventId;
        activityLog.activityComment = comment;
        activityLog.activitySuccess = true;
        activities.add(activityLog);
        return activityLog;
    }
}
