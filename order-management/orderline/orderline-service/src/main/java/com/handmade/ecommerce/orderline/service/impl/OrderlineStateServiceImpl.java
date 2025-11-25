package com.handmade.ecommerce.orderline.service.impl;

import com.handmade.ecommerce.orderline.model.Orderline;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.springframework.context.ApplicationEventPublisher;

/**
 * State service for handling Orderline entity transitions
 */
public class OrderlineStateServiceImpl extends StateEntityServiceImpl<Orderline> {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * @param stm                    the state machine that has read the corresponding State Transition Diagram
     * @param stmActionsInfoProvider the provider that gives out info about the state diagram
     * @param entityStore            the store for persisting the entity
     * @param applicationEventPublisher for publishing events when Orderline updates
     */
    public OrderlineStateServiceImpl(STM<Orderline> stm,
                                     STMActionsInfoProvider stmActionsInfoProvider,
                                     EntityStore<Orderline> entityStore,
                                     ApplicationEventPublisher applicationEventPublisher) {
        super(stm, stmActionsInfoProvider, entityStore);
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    protected Orderline processEntity(Orderline entity, String event, Object payload) {
        return super.processEntity(entity, event, payload);
    }

    public StateEntityServiceResponse<Orderline> create(Orderline entity) {
        Orderline orderline = new Orderline();
        orderline.setOrderId(entity.getOrderId());
        orderline.setProductId(entity.getProductId());
        orderline.setQuantity(entity.getQuantity());
        // Persist the entity
        StateEntityServiceResponse<Orderline> serviceResponse = super.create(entity);
        // Trigger the state machine transition
        serviceResponse = processById(serviceResponse.getMutatedEntity().getId(), "addOrderLine", null);
        return serviceResponse;
    }

    @Override
    public StateEntityServiceResponse<Orderline> process(Orderline entity, String event, Object payload) {
        return super.process(entity, event, payload);
    }

    @Override
    public StateEntityServiceResponse<Orderline> processById(String id, String event, Object payload) {
        StateEntityServiceResponse<Orderline> serviceResponse = super.processById(id, event, payload);
        return serviceResponse;
    }
}