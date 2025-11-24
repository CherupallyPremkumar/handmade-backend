package com.handmade.ecommerce.orderline.service.postSaveHooks;

import com.handmade.ecommerce.events.model.OrderLineEvent;
import com.handmade.ecommerce.orderline.model.Orderline;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

/**
 Contains customized post Save Hook for the State ID.
*/
public class FULFILLEDOrderlinePostSaveHook implements PostSaveHook<Orderline>{

    @Autowired
    private ApplicationEventPublisher eventPublisher;
	@Override
    public void execute(Orderline orderline, TransientMap map){
        OrderLineEvent fulfilledEvent = new OrderLineEvent(
                this, // source
                orderline.getOrderId(),
                OrderLineEvent.ActionType.FULFILLED
        );

        eventPublisher.publishEvent(fulfilledEvent);
    }
}
