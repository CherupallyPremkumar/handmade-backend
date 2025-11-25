package com.handmade.ecommerce.order.service.healthcheck;

import com.handmade.ecommerce.order.OrderService;
import com.handmade.ecommerce.order.model.Order;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends StateEntityServiceImpl<Order> implements OrderService {

    public OrderServiceImpl(STM<Order> stm,
            STMActionsInfoProvider stmActionsInfoProvider,
            EntityStore<Order> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }



}