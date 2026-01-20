package com.handmade.ecommerce.orderline.configuration;

import org.chenile.stm.*;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.impl.*;
import org.chenile.stm.spring.SpringBeanFactoryAdapter;
import org.chenile.workflow.param.MinimalPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.chenile.workflow.service.stmcmds.*;
import com.handmade.ecommerce.order.model.OrderLine;
import com.handmade.ecommerce.orderline.service.cmds.*;
import com.handmade.ecommerce.orderline.service.healthcheck.OrderLineHealthChecker;
import com.handmade.ecommerce.orderline.service.store.OrderLineEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class OrderLineConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/orderline/orderline-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "OrderLine";
    public static final String PREFIX_FOR_RESOLVER = "orderline";

    @Bean BeanFactoryAdapter orderlineBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl orderlineFlowStore(
            @Qualifier("orderlineBeanFactoryAdapter") BeanFactoryAdapter orderlineBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(orderlineBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<OrderLine> orderlineEntityStm(@Qualifier("orderlineFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<OrderLine> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider orderlineActionsInfoProvider(@Qualifier("orderlineFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("orderline",provider);
        return provider;
	}
	
	@Bean EntityStore<OrderLine> orderlineEntityStore() {
		return new OrderLineEntityStore();
	}
	
	@Bean StateEntityServiceImpl<OrderLine> _orderlineStateEntityService_(
			@Qualifier("orderlineEntityStm") STM<OrderLine> stm,
			@Qualifier("orderlineActionsInfoProvider") STMActionsInfoProvider orderlineInfoProvider,
			@Qualifier("orderlineEntityStore") EntityStore<OrderLine> entityStore){
		return new StateEntityServiceImpl<>(stm, orderlineInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<OrderLine> orderlineEntryAction(@Qualifier("orderlineEntityStore") EntityStore<OrderLine> entityStore,
			@Qualifier("orderlineActionsInfoProvider") STMActionsInfoProvider orderlineInfoProvider,
            @Qualifier("orderlineFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<OrderLine> entryAction =  new GenericEntryAction<OrderLine>(entityStore,orderlineInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<OrderLine> orderlineExitAction(@Qualifier("orderlineFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<OrderLine> exitAction = new GenericExitAction<OrderLine>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader orderlineFlowReader(@Qualifier("orderlineFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean OrderLineHealthChecker orderlineHealthChecker(){
    	return new OrderLineHealthChecker();
    }

    @Bean STMTransitionAction<OrderLine> defaultorderlineSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver orderlineTransitionActionResolver(
    @Qualifier("defaultorderlineSTMTransitionAction") STMTransitionAction<OrderLine> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector orderlineBodyTypeSelector(
    @Qualifier("orderlineActionsInfoProvider") STMActionsInfoProvider orderlineInfoProvider,
    @Qualifier("orderlineTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(orderlineInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<OrderLine> orderlineBaseTransitionAction(
        @Qualifier("orderlineTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "orderline" + eventId for the method name. (orderline is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/orderline/orderline-states.xml

    @Bean CompleteOrderLineAction
            completeOrderLineAction(){
        return new CompleteOrderLineAction();
    }
    @Bean ReturnOrderLineAction
            returnOrderLineAction(){
        return new ReturnOrderLineAction();
    }

    @Bean PickOrderLineAction
            pickOrderLineAction(){
        return new PickOrderLineAction();
    }
    @Bean PackOrderLineAction
            packOrderLineAction(){
        return new PackOrderLineAction();
    }
    @Bean CancelOrderLineAction
            cancelOrderLineAction(){
        return new CancelOrderLineAction();
    }
    @Bean AllocateOrderLineAction
            allocateOrderLineAction(){
        return new AllocateOrderLineAction();
    }
    @Bean ShipOrderLineAction
            shipOrderLineAction(){
        return new ShipOrderLineAction();
    }
    @Bean DeliverOrderLineAction
    deliverOrderLineAction(){
        return new DeliverOrderLineAction();
    }


}
