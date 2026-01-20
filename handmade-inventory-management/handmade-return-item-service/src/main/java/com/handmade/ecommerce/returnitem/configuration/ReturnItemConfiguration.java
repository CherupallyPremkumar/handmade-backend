package com.handmade.ecommerce.returnitem.configuration;

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
import com.handmade.ecommerce.inventory.model.ReturnItem;
import com.handmade.ecommerce.returnitem.service.cmds.*;
import com.handmade.ecommerce.returnitem.service.healthcheck.ReturnItemHealthChecker;
import com.handmade.ecommerce.returnitem.service.store.ReturnItemEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class ReturnItemConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/returnitem/returnitem-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "ReturnItem";
    public static final String PREFIX_FOR_RESOLVER = "returnitem";

    @Bean BeanFactoryAdapter returnitemBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl returnitemFlowStore(
            @Qualifier("returnitemBeanFactoryAdapter") BeanFactoryAdapter returnitemBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(returnitemBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<ReturnItem> returnitemEntityStm(@Qualifier("returnitemFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<ReturnItem> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider returnitemActionsInfoProvider(@Qualifier("returnitemFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("returnitem",provider);
        return provider;
	}
	
	@Bean EntityStore<ReturnItem> returnitemEntityStore() {
		return new ReturnItemEntityStore();
	}
	
	@Bean StateEntityServiceImpl<ReturnItem> _returnitemStateEntityService_(
			@Qualifier("returnitemEntityStm") STM<ReturnItem> stm,
			@Qualifier("returnitemActionsInfoProvider") STMActionsInfoProvider returnitemInfoProvider,
			@Qualifier("returnitemEntityStore") EntityStore<ReturnItem> entityStore){
		return new StateEntityServiceImpl<>(stm, returnitemInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<ReturnItem> returnitemEntryAction(@Qualifier("returnitemEntityStore") EntityStore<ReturnItem> entityStore,
			@Qualifier("returnitemActionsInfoProvider") STMActionsInfoProvider returnitemInfoProvider,
            @Qualifier("returnitemFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<ReturnItem> entryAction =  new GenericEntryAction<ReturnItem>(entityStore,returnitemInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<ReturnItem> returnitemExitAction(@Qualifier("returnitemFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<ReturnItem> exitAction = new GenericExitAction<ReturnItem>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader returnitemFlowReader(@Qualifier("returnitemFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean ReturnItemHealthChecker returnitemHealthChecker(){
    	return new ReturnItemHealthChecker();
    }

    @Bean STMTransitionAction<ReturnItem> defaultreturnitemSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver returnitemTransitionActionResolver(
    @Qualifier("defaultreturnitemSTMTransitionAction") STMTransitionAction<ReturnItem> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector returnitemBodyTypeSelector(
    @Qualifier("returnitemActionsInfoProvider") STMActionsInfoProvider returnitemInfoProvider,
    @Qualifier("returnitemTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(returnitemInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<ReturnItem> returnitemBaseTransitionAction(
        @Qualifier("returnitemTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "returnitem" + eventId for the method name. (returnitem is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/returnitem/returnitem-states.xml

    @Bean InspectReturnItemAction
            returnitemInspect(){
        return new InspectReturnItemAction();
    }
    @Bean ArriveReturnItemAction
            returnitemArrive(){
        return new ArriveReturnItemAction();
    }
    @Bean RestockReturnItemAction
            returnitemRestock(){
        return new RestockReturnItemAction();
    }
    @Bean DisposeReturnItemAction
            returnitemDispose(){
        return new DisposeReturnItemAction();
    }
    @Bean ApproveReturnItemAction
            returnitemApprove(){
        return new ApproveReturnItemAction();
    }
    @Bean RejectReturnItemAction
            returnitemReject(){
        return new RejectReturnItemAction();
    }
    @Bean ReceiveReturnItemAction
            returnitemReceive(){
        return new ReceiveReturnItemAction();
    }


}
