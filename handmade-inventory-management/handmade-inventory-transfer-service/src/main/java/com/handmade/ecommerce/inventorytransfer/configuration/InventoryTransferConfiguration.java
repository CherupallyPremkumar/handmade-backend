package com.handmade.ecommerce.inventorytransfer.configuration;

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
import com.handmade.ecommerce.inventory.model.InventoryTransfer;
import com.handmade.ecommerce.inventorytransfer.service.cmds.*;
import com.handmade.ecommerce.inventorytransfer.service.healthcheck.InventoryTransferHealthChecker;
import com.handmade.ecommerce.inventorytransfer.service.store.InventoryTransferEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class InventoryTransferConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/inventorytransfer/inventorytransfer-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "InventoryTransfer";
    public static final String PREFIX_FOR_RESOLVER = "inventorytransfer";

    @Bean BeanFactoryAdapter inventorytransferBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl inventorytransferFlowStore(
            @Qualifier("inventorytransferBeanFactoryAdapter") BeanFactoryAdapter inventorytransferBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(inventorytransferBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<InventoryTransfer> inventorytransferEntityStm(@Qualifier("inventorytransferFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<InventoryTransfer> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider inventorytransferActionsInfoProvider(@Qualifier("inventorytransferFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("inventorytransfer",provider);
        return provider;
	}
	
	@Bean EntityStore<InventoryTransfer> inventorytransferEntityStore() {
		return new InventoryTransferEntityStore();
	}
	
	@Bean StateEntityServiceImpl<InventoryTransfer> _inventorytransferStateEntityService_(
			@Qualifier("inventorytransferEntityStm") STM<InventoryTransfer> stm,
			@Qualifier("inventorytransferActionsInfoProvider") STMActionsInfoProvider inventorytransferInfoProvider,
			@Qualifier("inventorytransferEntityStore") EntityStore<InventoryTransfer> entityStore){
		return new StateEntityServiceImpl<>(stm, inventorytransferInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<InventoryTransfer> inventorytransferEntryAction(@Qualifier("inventorytransferEntityStore") EntityStore<InventoryTransfer> entityStore,
			@Qualifier("inventorytransferActionsInfoProvider") STMActionsInfoProvider inventorytransferInfoProvider,
            @Qualifier("inventorytransferFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<InventoryTransfer> entryAction =  new GenericEntryAction<InventoryTransfer>(entityStore,inventorytransferInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<InventoryTransfer> inventorytransferExitAction(@Qualifier("inventorytransferFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<InventoryTransfer> exitAction = new GenericExitAction<InventoryTransfer>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader inventorytransferFlowReader(@Qualifier("inventorytransferFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean InventoryTransferHealthChecker inventorytransferHealthChecker(){
    	return new InventoryTransferHealthChecker();
    }

    @Bean STMTransitionAction<InventoryTransfer> defaultinventorytransferSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver inventorytransferTransitionActionResolver(
    @Qualifier("defaultinventorytransferSTMTransitionAction") STMTransitionAction<InventoryTransfer> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector inventorytransferBodyTypeSelector(
    @Qualifier("inventorytransferActionsInfoProvider") STMActionsInfoProvider inventorytransferInfoProvider,
    @Qualifier("inventorytransferTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(inventorytransferInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<InventoryTransfer> inventorytransferBaseTransitionAction(
        @Qualifier("inventorytransferTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "inventorytransfer" + eventId for the method name. (inventorytransfer is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/inventorytransfer/inventorytransfer-states.xml

    @Bean CancelInventoryTransferAction
            inventorytransferCancel(){
        return new CancelInventoryTransferAction();
    }
    @Bean PickInventoryTransferAction
            inventorytransferPick(){
        return new PickInventoryTransferAction();
    }
    @Bean ReceiveInventoryTransferAction
            inventorytransferReceive(){
        return new ReceiveInventoryTransferAction();
    }
    @Bean PartialReceiveInventoryTransferAction
            inventorytransferPartialReceive(){
        return new PartialReceiveInventoryTransferAction();
    }
    @Bean DispatchInventoryTransferAction
            inventorytransferDispatch(){
        return new DispatchInventoryTransferAction();
    }
    @Bean ReceiveRemainingInventoryTransferAction
            inventorytransferReceiveRemaining(){
        return new ReceiveRemainingInventoryTransferAction();
    }


}
