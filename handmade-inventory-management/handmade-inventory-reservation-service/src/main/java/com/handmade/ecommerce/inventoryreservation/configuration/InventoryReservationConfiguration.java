package com.handmade.ecommerce.inventoryreservation.configuration;

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
import com.handmade.ecommerce.inventory.model.InventoryReservation;
import com.handmade.ecommerce.inventoryreservation.service.cmds.*;
import com.handmade.ecommerce.inventoryreservation.service.healthcheck.InventoryReservationHealthChecker;
import com.handmade.ecommerce.inventoryreservation.service.store.InventoryReservationEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class InventoryReservationConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/inventoryreservation/inventoryreservation-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "InventoryReservation";
    public static final String PREFIX_FOR_RESOLVER = "inventoryreservation";

    @Bean BeanFactoryAdapter inventoryreservationBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl inventoryreservationFlowStore(
            @Qualifier("inventoryreservationBeanFactoryAdapter") BeanFactoryAdapter inventoryreservationBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(inventoryreservationBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<InventoryReservation> inventoryreservationEntityStm(@Qualifier("inventoryreservationFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<InventoryReservation> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider inventoryreservationActionsInfoProvider(@Qualifier("inventoryreservationFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("inventoryreservation",provider);
        return provider;
	}
	
	@Bean EntityStore<InventoryReservation> inventoryreservationEntityStore() {
		return new InventoryReservationEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<InventoryReservation> _inventoryreservationStateEntityService_(
			@Qualifier("inventoryreservationEntityStm") STM<InventoryReservation> stm,
			@Qualifier("inventoryreservationActionsInfoProvider") STMActionsInfoProvider inventoryreservationInfoProvider,
			@Qualifier("inventoryreservationEntityStore") EntityStore<InventoryReservation> entityStore){
		return new StateEntityServiceImpl<>(stm, inventoryreservationInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<InventoryReservation> inventoryreservationEntryAction(@Qualifier("inventoryreservationEntityStore") EntityStore<InventoryReservation> entityStore,
			@Qualifier("inventoryreservationActionsInfoProvider") STMActionsInfoProvider inventoryreservationInfoProvider,
            @Qualifier("inventoryreservationFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<InventoryReservation> entryAction =  new GenericEntryAction<InventoryReservation>(entityStore,inventoryreservationInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<InventoryReservation> inventoryreservationExitAction(@Qualifier("inventoryreservationFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<InventoryReservation> exitAction = new GenericExitAction<InventoryReservation>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader inventoryreservationFlowReader(@Qualifier("inventoryreservationFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean InventoryReservationHealthChecker inventoryreservationHealthChecker(){
    	return new InventoryReservationHealthChecker();
    }

    @Bean STMTransitionAction<InventoryReservation> defaultinventoryreservationSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver inventoryreservationTransitionActionResolver(
    @Qualifier("defaultinventoryreservationSTMTransitionAction") STMTransitionAction<InventoryReservation> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector inventoryreservationBodyTypeSelector(
    @Qualifier("inventoryreservationActionsInfoProvider") STMActionsInfoProvider inventoryreservationInfoProvider,
    @Qualifier("inventoryreservationTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(inventoryreservationInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<InventoryReservation> inventoryreservationBaseTransitionAction(
        @Qualifier("inventoryreservationTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "inventoryreservation" + eventId for the method name. (inventoryreservation is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/inventoryreservation/inventoryreservation-states.xml

    @Bean FulfillInventoryReservationAction
            inventoryreservationFulfill(){
        return new FulfillInventoryReservationAction();
    }
    @Bean ExpireInventoryReservationAction
            inventoryreservationExpire(){
        return new ExpireInventoryReservationAction();
    }
    @Bean ConfirmInventoryReservationAction
            inventoryreservationConfirm(){
        return new ConfirmInventoryReservationAction();
    }
    @Bean CancelInventoryReservationAction
            inventoryreservationCancel(){
        return new CancelInventoryReservationAction();
    }


}
