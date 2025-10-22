package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.Inventory;
import com.homebase.ecom.entitystore.InventoryEntityStore;
import com.homebase.ecom.service.InventoryStateService;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class InventoryStateServiceImpl extends StateEntityServiceImpl<Inventory> implements InventoryStateService {




    protected  final InventoryEntityStore<Inventory> entityStore;

    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     */
    public InventoryStateServiceImpl(STM<Inventory> stm, STMActionsInfoProvider stmActionsInfoProvider, InventoryEntityStore<Inventory> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
        this.entityStore = entityStore;
    }

    @Override
    protected Inventory processEntity(Inventory entity, String event, Object payload) {
        return super.processEntity(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Inventory> create(Inventory entity) {
        return super.create(entity);
    }

    @Override
    public StateEntityServiceResponse<Inventory> process(Inventory entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Inventory> processById(String id, String event, Object payload) {
        return super.processById(id, event, payload);
    }

    @Override
    public boolean checkStock(String product, int quantity) throws Exception {
        Inventory inventory=entityStore.findByProductVariantId(product);
        return Objects.equals(inventory.getCurrentState().getStateId(), "IN_STOCK") && inventory.getQuantityAvailable() >= quantity;
    }
}
