package com.handmade.ecommerce.cyclecount.configuration;

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
import com.handmade.ecommerce.inventory.model.CycleCount;
import com.handmade.ecommerce.cyclecount.service.cmds.*;
import com.handmade.ecommerce.cyclecount.service.healthcheck.CycleCountHealthChecker;
import com.handmade.ecommerce.cyclecount.service.store.CycleCountEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class CycleCountConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/cyclecount/cyclecount-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "CycleCount";
    public static final String PREFIX_FOR_RESOLVER = "cyclecount";

    @Bean BeanFactoryAdapter cyclecountBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl cyclecountFlowStore(
            @Qualifier("cyclecountBeanFactoryAdapter") BeanFactoryAdapter cyclecountBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(cyclecountBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<CycleCount> cyclecountEntityStm(@Qualifier("cyclecountFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<CycleCount> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider cyclecountActionsInfoProvider(@Qualifier("cyclecountFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("cyclecount",provider);
        return provider;
	}
	
	@Bean EntityStore<CycleCount> cyclecountEntityStore() {
		return new CycleCountEntityStore();
	}
	
	@Bean StateEntityServiceImpl<CycleCount> _cyclecountStateEntityService_(
			@Qualifier("cyclecountEntityStm") STM<CycleCount> stm,
			@Qualifier("cyclecountActionsInfoProvider") STMActionsInfoProvider cyclecountInfoProvider,
			@Qualifier("cyclecountEntityStore") EntityStore<CycleCount> entityStore){
		return new StateEntityServiceImpl<>(stm, cyclecountInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<CycleCount> cyclecountEntryAction(@Qualifier("cyclecountEntityStore") EntityStore<CycleCount> entityStore,
			@Qualifier("cyclecountActionsInfoProvider") STMActionsInfoProvider cyclecountInfoProvider,
            @Qualifier("cyclecountFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<CycleCount> entryAction =  new GenericEntryAction<CycleCount>(entityStore,cyclecountInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<CycleCount> cyclecountExitAction(@Qualifier("cyclecountFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<CycleCount> exitAction = new GenericExitAction<CycleCount>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader cyclecountFlowReader(@Qualifier("cyclecountFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean CycleCountHealthChecker cyclecountHealthChecker(){
    	return new CycleCountHealthChecker();
    }

    @Bean STMTransitionAction<CycleCount> defaultcyclecountSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver cyclecountTransitionActionResolver(
    @Qualifier("defaultcyclecountSTMTransitionAction") STMTransitionAction<CycleCount> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector cyclecountBodyTypeSelector(
    @Qualifier("cyclecountActionsInfoProvider") STMActionsInfoProvider cyclecountInfoProvider,
    @Qualifier("cyclecountTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(cyclecountInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<CycleCount> cyclecountBaseTransitionAction(
        @Qualifier("cyclecountTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "cyclecount" + eventId for the method name. (cyclecount is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/cyclecount/cyclecount-states.xml

    @Bean CompleteCycleCountAction
            completeCycleCountAction(){
        return new CompleteCycleCountAction();
    }
    @Bean DetectVarianceCycleCountAction
            detectVarianceCycleCountAction(){
        return new DetectVarianceCycleCountAction();
    }
    @Bean ApproveCycleCountAction
            approveCycleCountAction(){
        return new ApproveCycleCountAction();
    }
    @Bean ApproveVarianceCycleCountAction
            approveVarianceCycleCountAction(){
        return new ApproveVarianceCycleCountAction();
    }
    @Bean AdjustCycleCountAction
            adjustCycleCountAction(){
        return new AdjustCycleCountAction();
    }
    @Bean StartCycleCountAction
            startCycleCountAction(){
        return new StartCycleCountAction();
    }


}
