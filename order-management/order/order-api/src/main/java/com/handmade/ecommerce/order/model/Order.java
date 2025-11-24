package com.handmade.ecommerce.order.model;

import com.handmade.ecommerce.location.model.Location;
import org.chenile.workflow.activities.model.ActivityEnabledStateEntity;
import org.chenile.workflow.activities.model.ActivityLog;

import java.math.BigDecimal;
import java.util.*;
import com.handmade.ecommerce.order.model.OrderActivityLog;
import org.chenile.workflow.model.*;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // can also use SINGLE_TABLE or TABLE_PER_CLASS
@Table(name = "orders")
public class Order extends AbstractJpaStateEntity
    implements ActivityEnabledStateEntity
        ,
    ContainsTransientMap
{

    private static final long serialVersionUID = 5943127292911636088L;
    private String orderNo;
    private String userId;
    private String comments;
    private String sellerId;
    private String txn;
    private String paymentId;
    private String paymentMode;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private BigDecimal transactionAmount;
    private String locationId;
    private String warehouseId;
    private int deliveredCount;
    private String shippingAddressId;
    private Date savedDateTime;
    @Transient
    public TransientMap transientMap = new TransientMap();
    public TransientMap getTransientMap(){
        return this.transientMap;
    }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    public List< OrderActivityLog> activities = new ArrayList<>();

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
        OrderActivityLog activityLog = new OrderActivityLog();
        activityLog.activityName = eventId;
        activityLog.activityComment = comment;
        activityLog.activitySuccess = true;
        activities.add(activityLog);
        return activityLog;
    }
}
