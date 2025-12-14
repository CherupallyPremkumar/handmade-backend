package com.handmade.ecommerce.paymentorder.model;

import lombok.Getter;
import lombok.Setter;
import org.chenile.workflow.activities.model.ActivityEnabledStateEntity;
import org.chenile.workflow.activities.model.ActivityLog;

import java.math.BigDecimal;
import java.util.*;

import org.chenile.workflow.model.*;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Table(name = "paymentorder")
@Getter
@Setter
public class PaymentOrder extends AbstractJpaStateEntity
    implements ActivityEnabledStateEntity
        ,
    ContainsTransientMap
{
    @Column(name = "payment_id", nullable = false)
    private String paymentId;

    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(length = 3, nullable = false)
    private String currency;

    @Column(name = "psp_name", length = 50)
    private String pspName;

    @Column(name = "psp_reference_id")
    private String pspReferenceId;

    @Column(name = "wallet_updated", nullable = false)
    private Boolean walletUpdated = false;

    @Column(name = "ledger_updated", nullable = false)
    private Boolean ledgerUpdated = false;

    /**
     * Retry count for failed payments
     * Used by retry mechanism
     */
    @Column(name = "retry_count", nullable = false)
    private Integer retryCount = 0;

    /**
     * Last error message (if failed)
     * Helps with debugging
     */
    @Column(name = "last_error", length = 1000)
    private String lastError;
    @Transient
    public TransientMap transientMap = new TransientMap();
    public TransientMap getTransientMap(){
        return this.transientMap;
    }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    public List< PaymentorderActivityLog> activities = new ArrayList<>();

    @Override
    public Collection<ActivityLog> obtainActivities() {
        Collection<ActivityLog> acts = new ArrayList<>();
        for (ActivityLog a: activities){
            acts.add(a);
        }
        return acts;
    }

    @Override
    public ActivityLog addActivity(String eventId, String comment) {
        PaymentorderActivityLog activityLog = new PaymentorderActivityLog();
        activityLog.activityName = eventId;
        activityLog.activityComment = comment;
        activityLog.activitySuccess = true;
        activities.add(activityLog);
        return activityLog;
    }
}
