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
import com.handmade.ecommerce.orderline.model.Orderline;
import com.handmade.ecommerce.orderline.service.cmds.*;
import com.handmade.ecommerce.orderline.service.healthcheck.OrderlineHealthChecker;
import com.handmade.ecommerce.orderline.service.store.OrderlineEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.stmcmds.StmAuthoritiesBuilder;
import java.util.function.Function;
import org.chenile.core.context.ChenileExchange;
import org.chenile.stm.State;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.activities.AreActivitiesComplete;
import com.handmade.ecommerce.orderline.service.postSaveHooks.*;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class OrderlineConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/orderline/orderline-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Orderline";
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
	
	@Bean @Autowired STM<Orderline> orderlineEntityStm(@Qualifier("orderlineFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Orderline> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider orderlineActionsInfoProvider(@Qualifier("orderlineFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("orderline",provider);
        return provider;
	}
	
	@Bean EntityStore<Orderline> orderlineEntityStore() {
		return new OrderlineEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Orderline> _orderlineStateEntityService_(
			@Qualifier("orderlineEntityStm") STM<Orderline> stm,
			@Qualifier("orderlineActionsInfoProvider") STMActionsInfoProvider orderlineInfoProvider,
			@Qualifier("orderlineEntityStore") EntityStore<Orderline> entityStore){
		return new StateEntityServiceImpl<>(stm, orderlineInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 


    @Bean @Autowired DefaultPostSaveHook<Orderline> orderlineDefaultPostSaveHook(
    @Qualifier("orderlineTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
    DefaultPostSaveHook<Orderline> postSaveHook = new DefaultPostSaveHook<>(stmTransitionActionResolver);
    return postSaveHook;
    }

    @Bean @Autowired GenericEntryAction<Orderline> orderlineEntryAction(@Qualifier("orderlineEntityStore") EntityStore<Orderline> entityStore,
    @Qualifier("orderlineActionsInfoProvider") STMActionsInfoProvider orderlineInfoProvider,
    @Qualifier("orderlineFlowStore") STMFlowStoreImpl stmFlowStore,
    @Qualifier("orderlineDefaultPostSaveHook") DefaultPostSaveHook<Orderline> postSaveHook)  {
    GenericEntryAction<Orderline> entryAction =  new GenericEntryAction<Orderline>(entityStore,orderlineInfoProvider,postSaveHook);
    stmFlowStore.setEntryAction(entryAction);
    return entryAction;
    }

    @Bean @Autowired DefaultAutomaticStateComputation<Orderline> orderlineDefaultAutoState(
    @Qualifier("orderlineTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
    @Qualifier("orderlineFlowStore") STMFlowStoreImpl stmFlowStore){
    DefaultAutomaticStateComputation<Orderline> autoState = new DefaultAutomaticStateComputation<>(stmTransitionActionResolver);
    stmFlowStore.setDefaultAutomaticStateComputation(autoState);
    return autoState;
    }

	@Bean GenericExitAction<Orderline> orderlineExitAction(@Qualifier("orderlineFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Orderline> exitAction = new GenericExitAction<Orderline>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader orderlineFlowReader(@Qualifier("orderlineFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean OrderlineHealthChecker orderlineHealthChecker(){
    	return new OrderlineHealthChecker();
    }

    @Bean STMTransitionAction<Orderline> defaultorderlineSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver orderlineTransitionActionResolver(
    @Qualifier("defaultorderlineSTMTransitionAction") STMTransitionAction<Orderline> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction,true);
    }

    @Bean @Autowired StmBodyTypeSelector orderlineBodyTypeSelector(
    @Qualifier("orderlineActionsInfoProvider") STMActionsInfoProvider orderlineInfoProvider,
    @Qualifier("orderlineTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(orderlineInfoProvider,stmTransitionActionResolver);
    }


    @Bean @Autowired STMTransitionAction<Orderline> orderlineBaseTransitionAction(
        @Qualifier("orderlineTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
        @Qualifier("orderlineActivityChecker") ActivityChecker activityChecker,
        @Qualifier("orderlineFlowStore") STMFlowStoreImpl stmFlowStore){
        BaseTransitionAction<Orderline> baseTransitionAction = new BaseTransitionAction<>(stmTransitionActionResolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker orderlineActivityChecker(@Qualifier("orderlineFlowStore") STMFlowStoreImpl stmFlowStore){
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete activitiesCompletionCheck(@Qualifier("orderlineActivityChecker") ActivityChecker activityChecker){
        return new AreActivitiesComplete(activityChecker);
    }

    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "orderline" + eventId + "Action" for the method name. (orderline is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/orderline/orderline-states.xml


    @Bean ItemShipOrderlineAction
            orderlineItemShipAction(){
        return new ItemShipOrderlineAction();
    }


    @Bean ItemPackOrderlineAction
            orderlineItemPackAction(){
        return new ItemPackOrderlineAction();
    }

    @Bean ShipmentOrderlineAction
            orderlineShipmentAction(){
        return new ShipmentOrderlineAction();
    }


    @Bean SaveOrderOrderlineAction
            orderlineSaveOrderAction(){
        return new SaveOrderOrderlineAction();
    }

    @Bean ItemCommitOrderlineAction
            orderlineItemCommitAction(){
        return new ItemCommitOrderlineAction();
    }

    @Bean PayOrderlineAction
            orderlinePayAction(){
        return new PayOrderlineAction();
    }


    @Bean ModifyPriceOrderlineAction
            orderlineModifyPriceAction(){
        return new ModifyPriceOrderlineAction();
    }

    @Bean OrderConfirmOrderlineAction
            orderlineOrderConfirmAction(){
        return new OrderConfirmOrderlineAction();
    }

    @Bean ModifyCustomerOrderlineAction
            orderlineModifyCustomerAction(){
        return new ModifyCustomerOrderlineAction();
    }

    @Bean OlModifyQtyOrderlineAction
            orderlineOlModifyQtyAction(){
        return new OlModifyQtyOrderlineAction();
    }

    @Bean DeleteOrderLineOrderlineAction
            orderlineDeleteOrderLineAction(){
        return new DeleteOrderLineOrderlineAction();
    }

    @Bean ReloadOrderlineAction
            orderlineReloadAction(){
        return new ReloadOrderlineAction();
    }

    @Bean OlDecrementQtyOrderlineAction
            orderlineOlDecrementQtyAction(){
        return new OlDecrementQtyOrderlineAction();
    }

    @Bean LoadOrderOrderlineAction
            orderlineLoadOrderAction(){
        return new LoadOrderOrderlineAction();
    }

    @Bean ItemPickOrderlineAction
            orderlineItemPickAction(){
        return new ItemPickOrderlineAction();
    }

    @Bean StorePickUpOrderlineAction
            orderlineStorePickUpAction(){
        return new StorePickUpOrderlineAction();
    }

    @Bean OrderShipTypeOrderlineAction
            orderlineOrderShipTypeAction(){
        return new OrderShipTypeOrderlineAction();
    }

    @Bean OlIncrementQtyOrderlineAction
            orderlineOlIncrementQtyAction(){
        return new OlIncrementQtyOrderlineAction();
    }


    @Bean ConfigProviderImpl orderlineConfigProvider() {
        return new ConfigProviderImpl();
    }

    @Bean ConfigBasedEnablementStrategy orderlineConfigBasedEnablementStrategy(
        @Qualifier("orderlineConfigProvider") ConfigProvider configProvider,
        @Qualifier("orderlineFlowStore") STMFlowStoreImpl stmFlowStore) {
        ConfigBasedEnablementStrategy enablementStrategy = new ConfigBasedEnablementStrategy(configProvider,PREFIX_FOR_PROPERTIES);
        stmFlowStore.setEnablementStrategy(enablementStrategy);
        return enablementStrategy;
    }


    @Bean @Autowired Function<ChenileExchange, String[]> orderlineEventAuthoritiesSupplier(
        @Qualifier("orderlineActionsInfoProvider") STMActionsInfoProvider orderlineInfoProvider)
                    throws Exception{
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(orderlineInfoProvider);
        return builder.build();
    }


    @Bean FULFILLEDOrderlinePostSaveHook
        orderlineFULFILLEDPostSaveHook(){
            return new FULFILLEDOrderlinePostSaveHook();
    }

    @Bean CANCELLEDOrderlinePostSaveHook
        orderlineCANCELLEDPostSaveHook(){
            return new CANCELLEDOrderlinePostSaveHook();
    }

    @Bean PENDING_STORE_FULFILLMENTOrderlinePostSaveHook
        orderlinePENDING_STORE_FULFILLMENTPostSaveHook(){
            return new PENDING_STORE_FULFILLMENTOrderlinePostSaveHook();
    }

    @Bean PENDING_BILLINGOrderlinePostSaveHook
        orderlinePENDING_BILLINGPostSaveHook(){
            return new PENDING_BILLINGOrderlinePostSaveHook();
    }

    @Bean SAVEDOrderlinePostSaveHook
        orderlineSAVEDPostSaveHook(){
            return new SAVEDOrderlinePostSaveHook();
    }

    @Bean VOIDOrderlinePostSaveHook
        orderlineVOIDPostSaveHook(){
            return new VOIDOrderlinePostSaveHook();
    }

    @Bean PENDING_SHIPMENT_FULFILLMENTOrderlinePostSaveHook
        orderlinePENDING_SHIPMENT_FULFILLMENTPostSaveHook(){
            return new PENDING_SHIPMENT_FULFILLMENTOrderlinePostSaveHook();
    }

    @Bean OPENEDOrderlinePostSaveHook
        orderlineOPENEDPostSaveHook(){
            return new OPENEDOrderlinePostSaveHook();
    }

}
