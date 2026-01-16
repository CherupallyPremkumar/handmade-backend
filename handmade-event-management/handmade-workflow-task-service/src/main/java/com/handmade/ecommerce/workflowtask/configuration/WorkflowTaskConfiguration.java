package com.handmade.ecommerce.workflowtask.configuration;

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
import com.handmade.ecommerce.event.model.WorkflowTask;
import com.handmade.ecommerce.workflowtask.service.cmds.*;
import com.handmade.ecommerce.workflowtask.service.healthcheck.WorkflowTaskHealthChecker;
import com.handmade.ecommerce.workflowtask.service.store.WorkflowTaskEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class WorkflowTaskConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/workflowtask/workflowtask-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "WorkflowTask";
    public static final String PREFIX_FOR_RESOLVER = "workflowtask";

    @Bean BeanFactoryAdapter workflowtaskBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl workflowtaskFlowStore(
            @Qualifier("workflowtaskBeanFactoryAdapter") BeanFactoryAdapter workflowtaskBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(workflowtaskBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<WorkflowTask> workflowtaskEntityStm(@Qualifier("workflowtaskFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<WorkflowTask> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider workflowtaskActionsInfoProvider(@Qualifier("workflowtaskFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("workflowtask",provider);
        return provider;
	}
	
	@Bean EntityStore<WorkflowTask> workflowtaskEntityStore() {
		return new WorkflowTaskEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<WorkflowTask> _workflowtaskStateEntityService_(
			@Qualifier("workflowtaskEntityStm") STM<WorkflowTask> stm,
			@Qualifier("workflowtaskActionsInfoProvider") STMActionsInfoProvider workflowtaskInfoProvider,
			@Qualifier("workflowtaskEntityStore") EntityStore<WorkflowTask> entityStore){
		return new StateEntityServiceImpl<>(stm, workflowtaskInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<WorkflowTask> workflowtaskEntryAction(@Qualifier("workflowtaskEntityStore") EntityStore<WorkflowTask> entityStore,
			@Qualifier("workflowtaskActionsInfoProvider") STMActionsInfoProvider workflowtaskInfoProvider,
            @Qualifier("workflowtaskFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<WorkflowTask> entryAction =  new GenericEntryAction<WorkflowTask>(entityStore,workflowtaskInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<WorkflowTask> workflowtaskExitAction(@Qualifier("workflowtaskFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<WorkflowTask> exitAction = new GenericExitAction<WorkflowTask>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader workflowtaskFlowReader(@Qualifier("workflowtaskFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean WorkflowTaskHealthChecker workflowtaskHealthChecker(){
    	return new WorkflowTaskHealthChecker();
    }

    @Bean STMTransitionAction<WorkflowTask> defaultworkflowtaskSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver workflowtaskTransitionActionResolver(
    @Qualifier("defaultworkflowtaskSTMTransitionAction") STMTransitionAction<WorkflowTask> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector workflowtaskBodyTypeSelector(
    @Qualifier("workflowtaskActionsInfoProvider") STMActionsInfoProvider workflowtaskInfoProvider,
    @Qualifier("workflowtaskTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(workflowtaskInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<WorkflowTask> workflowtaskBaseTransitionAction(
        @Qualifier("workflowtaskTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "workflowtask" + eventId for the method name. (workflowtask is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/workflowtask/workflowtask-states.xml

    @Bean FailWorkflowTaskAction
            workflowtaskFail(){
        return new FailWorkflowTaskAction();
    }
    @Bean CompleteWorkflowTaskAction
            workflowtaskComplete(){
        return new CompleteWorkflowTaskAction();
    }


}
