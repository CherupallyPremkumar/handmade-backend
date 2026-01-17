package com.handmade.ecommerce.etljob.configuration;

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
import com.handmade.ecommerce.integration.model.ETLJob;
import com.handmade.ecommerce.etljob.service.cmds.*;
import com.handmade.ecommerce.etljob.service.healthcheck.ETLJobHealthChecker;
import com.handmade.ecommerce.etljob.service.store.ETLJobEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class ETLJobConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/etljob/etljob-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "ETLJob";
    public static final String PREFIX_FOR_RESOLVER = "etljob";

    @Bean BeanFactoryAdapter etljobBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl etljobFlowStore(
            @Qualifier("etljobBeanFactoryAdapter") BeanFactoryAdapter etljobBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(etljobBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<ETLJob> etljobEntityStm(@Qualifier("etljobFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<ETLJob> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider etljobActionsInfoProvider(@Qualifier("etljobFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("etljob",provider);
        return provider;
	}
	
	@Bean EntityStore<ETLJob> etljobEntityStore() {
		return new ETLJobEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<ETLJob> _etljobStateEntityService_(
			@Qualifier("etljobEntityStm") STM<ETLJob> stm,
			@Qualifier("etljobActionsInfoProvider") STMActionsInfoProvider etljobInfoProvider,
			@Qualifier("etljobEntityStore") EntityStore<ETLJob> entityStore){
		return new StateEntityServiceImpl<>(stm, etljobInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<ETLJob> etljobEntryAction(@Qualifier("etljobEntityStore") EntityStore<ETLJob> entityStore,
			@Qualifier("etljobActionsInfoProvider") STMActionsInfoProvider etljobInfoProvider,
            @Qualifier("etljobFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<ETLJob> entryAction =  new GenericEntryAction<ETLJob>(entityStore,etljobInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<ETLJob> etljobExitAction(@Qualifier("etljobFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<ETLJob> exitAction = new GenericExitAction<ETLJob>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader etljobFlowReader(@Qualifier("etljobFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean ETLJobHealthChecker etljobHealthChecker(){
    	return new ETLJobHealthChecker();
    }

    @Bean STMTransitionAction<ETLJob> defaultetljobSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver etljobTransitionActionResolver(
    @Qualifier("defaultetljobSTMTransitionAction") STMTransitionAction<ETLJob> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector etljobBodyTypeSelector(
    @Qualifier("etljobActionsInfoProvider") STMActionsInfoProvider etljobInfoProvider,
    @Qualifier("etljobTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(etljobInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<ETLJob> etljobBaseTransitionAction(
        @Qualifier("etljobTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "etljob" + eventId for the method name. (etljob is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/etljob/etljob-states.xml

    @Bean RetryETLJobAction
            etljobRetry(){
        return new RetryETLJobAction();
    }
    @Bean FailETLJobAction
            etljobFail(){
        return new FailETLJobAction();
    }
    @Bean CompleteETLJobAction
            etljobComplete(){
        return new CompleteETLJobAction();
    }
    @Bean CancelETLJobAction
            etljobCancel(){
        return new CancelETLJobAction();
    }
    @Bean StartETLJobAction
            etljobStart(){
        return new StartETLJobAction();
    }


}
