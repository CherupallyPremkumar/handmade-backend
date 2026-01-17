package com.handmade.ecommerce.settlementbatch.configuration;

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
import com.handmade.ecommerce.settlement.model.SettlementBatch;
import com.handmade.ecommerce.settlementbatch.service.cmds.*;
import com.handmade.ecommerce.settlementbatch.service.healthcheck.SettlementBatchHealthChecker;
import com.handmade.ecommerce.settlementbatch.service.store.SettlementBatchEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class SettlementBatchConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/settlementbatch/settlementbatch-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "SettlementBatch";
    public static final String PREFIX_FOR_RESOLVER = "settlementbatch";

    @Bean BeanFactoryAdapter settlementbatchBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl settlementbatchFlowStore(
            @Qualifier("settlementbatchBeanFactoryAdapter") BeanFactoryAdapter settlementbatchBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(settlementbatchBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<SettlementBatch> settlementbatchEntityStm(@Qualifier("settlementbatchFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<SettlementBatch> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider settlementbatchActionsInfoProvider(@Qualifier("settlementbatchFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("settlementbatch",provider);
        return provider;
	}
	
	@Bean EntityStore<SettlementBatch> settlementbatchEntityStore() {
		return new SettlementBatchEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<SettlementBatch> _settlementbatchStateEntityService_(
			@Qualifier("settlementbatchEntityStm") STM<SettlementBatch> stm,
			@Qualifier("settlementbatchActionsInfoProvider") STMActionsInfoProvider settlementbatchInfoProvider,
			@Qualifier("settlementbatchEntityStore") EntityStore<SettlementBatch> entityStore){
		return new StateEntityServiceImpl<>(stm, settlementbatchInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<SettlementBatch> settlementbatchEntryAction(@Qualifier("settlementbatchEntityStore") EntityStore<SettlementBatch> entityStore,
			@Qualifier("settlementbatchActionsInfoProvider") STMActionsInfoProvider settlementbatchInfoProvider,
            @Qualifier("settlementbatchFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<SettlementBatch> entryAction =  new GenericEntryAction<SettlementBatch>(entityStore,settlementbatchInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<SettlementBatch> settlementbatchExitAction(@Qualifier("settlementbatchFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<SettlementBatch> exitAction = new GenericExitAction<SettlementBatch>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader settlementbatchFlowReader(@Qualifier("settlementbatchFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean SettlementBatchHealthChecker settlementbatchHealthChecker(){
    	return new SettlementBatchHealthChecker();
    }

    @Bean STMTransitionAction<SettlementBatch> defaultsettlementbatchSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver settlementbatchTransitionActionResolver(
    @Qualifier("defaultsettlementbatchSTMTransitionAction") STMTransitionAction<SettlementBatch> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector settlementbatchBodyTypeSelector(
    @Qualifier("settlementbatchActionsInfoProvider") STMActionsInfoProvider settlementbatchInfoProvider,
    @Qualifier("settlementbatchTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(settlementbatchInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<SettlementBatch> settlementbatchBaseTransitionAction(
        @Qualifier("settlementbatchTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "settlementbatch" + eventId for the method name. (settlementbatch is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/settlementbatch/settlementbatch-states.xml

    @Bean ReconcileSettlementBatchAction
            reconcileSettlementBatchAction(){
        return new ReconcileSettlementBatchAction();
    }
    @Bean CancelSettlementBatchAction
            cancelSettlementBatchAction(){
        return new CancelSettlementBatchAction();
    }
    @Bean RetrySettlementBatchAction
            retrySettlementBatchAction(){
        return new RetrySettlementBatchAction();
    }
    @Bean FailSettlementBatchAction
            failSettlementBatchAction(){
        return new FailSettlementBatchAction();
    }
    @Bean CompleteSettlementBatchAction
            completeSettlementBatchAction(){
        return new CompleteSettlementBatchAction();
    }
    @Bean ProcessSettlementBatchAction
            processSettlementBatchAction(){
        return new ProcessSettlementBatchAction();
    }


}
