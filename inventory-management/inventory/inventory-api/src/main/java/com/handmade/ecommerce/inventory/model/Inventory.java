package com.handmade.ecommerce.inventory.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.chenile.workflow.activities.model.ActivityEnabledStateEntity;
import org.chenile.workflow.activities.model.ActivityLog;
import java.util.*;
import com.handmade.ecommerce.inventory.model.InventoryActivityLog;
import org.chenile.workflow.model.*;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
@Entity
@Setter
@Getter
@Table(name = "inventory")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "inventory_type")
public class Inventory extends AbstractJpaStateEntity
        implements ActivityEnabledStateEntity, ContainsTransientMap {

    @Column(name = "variant_id")
    private String variantId;

    @Column(name = "location_id")
    private String locationId;

    @Column(name = "quantity_on_hand")
    private int quantityOnHand;

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

    public int getReservedQuantity() {
        return quantityCommitted;
    }
}
