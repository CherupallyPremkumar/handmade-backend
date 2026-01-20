package com.handmade.ecommerce.inventoryadjustment.configuration;

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
import com.handmade.ecommerce.inventory.model.InventoryAdjustment;
import com.handmade.ecommerce.inventoryadjustment.service.cmds.*;
import com.handmade.ecommerce.inventoryadjustment.service.healthcheck.InventoryAdjustmentHealthChecker;
import com.handmade.ecommerce.inventoryadjustment.service.store.InventoryAdjustmentEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class InventoryAdjustmentConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/inventoryadjustment/inventoryadjustment-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "InventoryAdjustment";
    public static final String PREFIX_FOR_RESOLVER = "inventoryadjustment";

    @Bean BeanFactoryAdapter inventoryadjustmentBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl inventoryadjustmentFlowStore(
            @Qualifier("inventoryadjustmentBeanFactoryAdapter") BeanFactoryAdapter inventoryadjustmentBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(inventoryadjustmentBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<InventoryAdjustment> inventoryadjustmentEntityStm(@Qualifier("inventoryadjustmentFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<InventoryAdjustment> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider inventoryadjustmentActionsInfoProvider(@Qualifier("inventoryadjustmentFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("inventoryadjustment",provider);
        return provider;
	}
	
	@Bean EntityStore<InventoryAdjustment> inventoryadjustmentEntityStore() {
		return new InventoryAdjustmentEntityStore();
	}
	
	@Bean StateEntityServiceImpl<InventoryAdjustment> _inventoryadjustmentStateEntityService_(
			@Qualifier("inventoryadjustmentEntityStm") STM<InventoryAdjustment> stm,
			@Qualifier("inventoryadjustmentActionsInfoProvider") STMActionsInfoProvider inventoryadjustmentInfoProvider,
			@Qualifier("inventoryadjustmentEntityStore") EntityStore<InventoryAdjustment> entityStore){
		return new StateEntityServiceImpl<>(stm, inventoryadjustmentInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<InventoryAdjustment> inventoryadjustmentEntryAction(@Qualifier("inventoryadjustmentEntityStore") EntityStore<InventoryAdjustment> entityStore,
			@Qualifier("inventoryadjustmentActionsInfoProvider") STMActionsInfoProvider inventoryadjustmentInfoProvider,
            @Qualifier("inventoryadjustmentFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<InventoryAdjustment> entryAction =  new GenericEntryAction<InventoryAdjustment>(entityStore,inventoryadjustmentInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<InventoryAdjustment> inventoryadjustmentExitAction(@Qualifier("inventoryadjustmentFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<InventoryAdjustment> exitAction = new GenericExitAction<InventoryAdjustment>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader inventoryadjustmentFlowReader(@Qualifier("inventoryadjustmentFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean InventoryAdjustmentHealthChecker inventoryadjustmentHealthChecker(){
    	return new InventoryAdjustmentHealthChecker();
    }

    @Bean STMTransitionAction<InventoryAdjustment> defaultinventoryadjustmentSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver inventoryadjustmentTransitionActionResolver(
    @Qualifier("defaultinventoryadjustmentSTMTransitionAction") STMTransitionAction<InventoryAdjustment> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector inventoryadjustmentBodyTypeSelector(
    @Qualifier("inventoryadjustmentActionsInfoProvider") STMActionsInfoProvider inventoryadjustmentInfoProvider,
    @Qualifier("inventoryadjustmentTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(inventoryadjustmentInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<InventoryAdjustment> inventoryadjustmentBaseTransitionAction(
        @Qualifier("inventoryadjustmentTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "inventoryadjustment" + eventId for the method name. (inventoryadjustment is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/inventoryadjustment/inventoryadjustment-states.xml

    @Bean ApproveInventoryAdjustmentAction
            inventoryadjustmentApprove(){
        return new ApproveInventoryAdjustmentAction();
    }
    @Bean RejectInventoryAdjustmentAction
            inventoryadjustmentReject(){
        return new RejectInventoryAdjustmentAction();
    }
    @Bean SubmitInventoryAdjustmentAction
            inventoryadjustmentSubmit(){
        return new SubmitInventoryAdjustmentAction();
    }
    @Bean ApplyInventoryAdjustmentAction
            inventoryadjustmentApply(){
        return new ApplyInventoryAdjustmentAction();
    }


}
