package com.handmade.ecommerce.order.configuration;

import com.handmade.ecommerce.event.EventBus;
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
import com.handmade.ecommerce.order.model.AOrder;
import com.handmade.ecommerce.order.service.cmds.*;
import com.handmade.ecommerce.order.service.healthcheck.OrderHealthChecker;
import com.handmade.ecommerce.order.service.store.OrderEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.stmcmds.StmAuthoritiesBuilder;
import java.util.function.Function;
import org.chenile.core.context.ChenileExchange;
import org.chenile.stm.State;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.activities.AreActivitiesComplete;
import com.handmade.ecommerce.order.service.postSaveHooks.*;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class OrderConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/order/order-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Order";
    public static final String PREFIX_FOR_RESOLVER = "order";

    @Bean BeanFactoryAdapter orderBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}

	
	@Bean STMFlowStoreImpl orderFlowStore(
            @Qualifier("orderBeanFactoryAdapter") BeanFactoryAdapter orderBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(orderBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Order> orderEntityStm(@Qualifier("orderFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Order> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider orderActionsInfoProvider(@Qualifier("orderFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("order",provider);
        return provider;
	}
	
	@Bean EntityStore<Order> orderEntityStore() {
		return new OrderEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Order> _orderStateEntityService_(
			@Qualifier("orderEntityStm") STM<Order> stm,
			@Qualifier("orderActionsInfoProvider") STMActionsInfoProvider orderInfoProvider,
			@Qualifier("orderEntityStore") EntityStore<Order> entityStore){
		return new StateEntityServiceImpl<>(stm, orderInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 


    @Bean @Autowired DefaultPostSaveHook<Order> orderDefaultPostSaveHook(
    @Qualifier("orderTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
    DefaultPostSaveHook<Order> postSaveHook = new DefaultPostSaveHook<>(stmTransitionActionResolver);
    return postSaveHook;
    }

    @Bean @Autowired GenericEntryAction<Order> orderEntryAction(@Qualifier("orderEntityStore") EntityStore<Order> entityStore,
    @Qualifier("orderActionsInfoProvider") STMActionsInfoProvider orderInfoProvider,
    @Qualifier("orderFlowStore") STMFlowStoreImpl stmFlowStore,
    @Qualifier("orderDefaultPostSaveHook") DefaultPostSaveHook<Order> postSaveHook)  {
    GenericEntryAction<Order> entryAction =  new GenericEntryAction<Order>(entityStore,orderInfoProvider,postSaveHook);
    stmFlowStore.setEntryAction(entryAction);
    return entryAction;
    }

    @Bean @Autowired DefaultAutomaticStateComputation<Order> orderDefaultAutoState(
    @Qualifier("orderTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
    @Qualifier("orderFlowStore") STMFlowStoreImpl stmFlowStore){
    DefaultAutomaticStateComputation<Order> autoState = new DefaultAutomaticStateComputation<>(stmTransitionActionResolver);
    stmFlowStore.setDefaultAutomaticStateComputation(autoState);
    return autoState;
    }

	@Bean GenericExitAction<Order> orderExitAction(@Qualifier("orderFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Order> exitAction = new GenericExitAction<Order>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader orderFlowReader(@Qualifier("orderFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean OrderHealthChecker orderHealthChecker(){
    	return new OrderHealthChecker();
    }

    @Bean STMTransitionAction<Order> defaultorderSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver orderTransitionActionResolver(
    @Qualifier("defaultorderSTMTransitionAction") STMTransitionAction<Order> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction,true);
    }

    @Bean @Autowired StmBodyTypeSelector orderBodyTypeSelector(
    @Qualifier("orderActionsInfoProvider") STMActionsInfoProvider orderInfoProvider,
    @Qualifier("orderTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(orderInfoProvider,stmTransitionActionResolver);
    }


    @Bean @Autowired STMTransitionAction<Order> orderBaseTransitionAction(
        @Qualifier("orderTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
        @Qualifier("orderActivityChecker") ActivityChecker activityChecker,
        @Qualifier("orderFlowStore") STMFlowStoreImpl stmFlowStore){
        BaseTransitionAction<Order> baseTransitionAction = new BaseTransitionAction<>(stmTransitionActionResolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker orderActivityChecker(@Qualifier("orderFlowStore") STMFlowStoreImpl stmFlowStore){
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete activitiesCompletionCheck(@Qualifier("orderActivityChecker") ActivityChecker activityChecker){
        return new AreActivitiesComplete(activityChecker);
    }

    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "order" + eventId + "Action" for the method name. (order is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/order/order-states.xml


    @Bean AddOrderAction
            orderAddAction(){
        return new AddOrderAction();
    }

    @Bean NoOrderAction
            orderNoAction(){
        return new NoOrderAction();
    }

    @Bean OrderPlacedOrderAction
            orderOrderPlacedAction(){
        return new OrderPlacedOrderAction();
    }

    @Bean FulfillOrderAction
            orderFulfillAction(){
        return new FulfillOrderAction();
    }

    @Bean YesOrderAction
            orderYesAction(){
        return new YesOrderAction();
    }


    @Bean ConfigProviderImpl orderConfigProvider() {
        return new ConfigProviderImpl();
    }

    @Bean ConfigBasedEnablementStrategy orderConfigBasedEnablementStrategy(
        @Qualifier("orderConfigProvider") ConfigProvider configProvider,
        @Qualifier("orderFlowStore") STMFlowStoreImpl stmFlowStore) {
        ConfigBasedEnablementStrategy enablementStrategy = new ConfigBasedEnablementStrategy(configProvider,PREFIX_FOR_PROPERTIES);
        stmFlowStore.setEnablementStrategy(enablementStrategy);
        return enablementStrategy;
    }


    @Bean @Autowired Function<ChenileExchange, String[]> orderEventAuthoritiesSupplier(
        @Qualifier("orderActionsInfoProvider") STMActionsInfoProvider orderInfoProvider)
                    throws Exception{
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(orderInfoProvider);
        return builder.build();
    }


    @Bean AVAILABLEOrderPostSaveHook
        orderAVAILABLEPostSaveHook(){
            return new AVAILABLEOrderPostSaveHook();
    }

    @Bean BACKORDEROrderPostSaveHook
        orderBACKORDERPostSaveHook(){
            return new BACKORDEROrderPostSaveHook();
    }

}
