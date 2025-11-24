package com.handmade.ecommerce.shipping.model;

import org.chenile.workflow.activities.model.ActivityEnabledStateEntity;
import org.chenile.workflow.activities.model.ActivityLog;
import java.util.*;
import com.handmade.ecommerce.shipping.model.ShippingActivityLog;
import org.chenile.workflow.model.*;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Table(name = "shipping")
public class Shipping extends AbstractJpaStateEntity
    implements ActivityEnabledStateEntity
        ,
    ContainsTransientMap
{
	public String description;
    @Transient
    public TransientMap transientMap = new TransientMap();
    public TransientMap getTransientMap(){
        return this.transientMap;
    }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    public List< ShippingActivityLog> activities = new ArrayList<>();

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
        ShippingActivityLog activityLog = new ShippingActivityLog();
        activityLog.activityName = eventId;
        activityLog.activityComment = comment;
        activityLog.activitySuccess = true;
        activities.add(activityLog);
        return activityLog;
    }
}
