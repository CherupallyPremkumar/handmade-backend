package com.handmade.ecommerce.order.service.healthcheck;

import com.handmade.ecommerce.order.OrderService;
import com.handmade.ecommerce.events.model.OrderLineEvent;
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

    @EventListener
    public void handleOrderLineEvent(OrderLineEvent event) {
        String orderId = event.getOrderId();
        switch (event.getActionType()) {
            case ADD, UPDATE, INCREMENT, DECREMENT:
                super.processById(orderId, "refactorOrder",null);
                break;
            default:
                System.out.println("Unsupported action: " + event.getActionType());
        }
        Order order = super.processById(orderId, "", null).getMutatedEntity();
    }

}