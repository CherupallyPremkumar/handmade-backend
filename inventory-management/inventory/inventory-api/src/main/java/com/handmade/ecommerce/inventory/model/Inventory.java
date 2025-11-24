package com.handmade.ecommerce.inventory.model;

import org.antlr.v4.runtime.misc.NotNull;
import org.chenile.workflow.activities.model.ActivityEnabledStateEntity;
import org.chenile.workflow.activities.model.ActivityLog;
import java.util.*;
import com.handmade.ecommerce.inventory.model.InventoryActivityLog;
import org.chenile.workflow.model.*;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Table(name = "inventory")
public class Inventory extends AbstractJpaStateEntity implements ActivityEnabledStateEntity, ContainsTransientMap
{
    @Column(name = "quantity_on_hand")
    private int quantityOnHand;
    @NotNull
    @Column(name = "variant_id")
    private String variantId;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "location_id")
    private String locationId;
    @Column(name = "quantity_committed")
    private int quantityCommitted;
    @Column(name = "quantity_available")
    private int quantityAvailable;
    @Column(name = "quantity_back_ordered")
    private int quantityBackOrdered;


    private String warehouseId;
    @Transient
    public TransientMap transientMap = new TransientMap();
    public TransientMap getTransientMap(){
        return this.transientMap;
    }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    public List< InventoryActivityLog> activities = new ArrayList<>();


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
        InventoryActivityLog activityLog = new InventoryActivityLog();
        activityLog.activityName = eventId;
        activityLog.activityComment = comment;
        activityLog.activitySuccess = true;
        activities.add(activityLog);
        return activityLog;
    }
}
