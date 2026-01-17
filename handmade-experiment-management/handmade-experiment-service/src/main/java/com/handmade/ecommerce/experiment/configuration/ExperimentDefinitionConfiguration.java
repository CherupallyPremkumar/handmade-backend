package com.handmade.ecommerce.experiment.configuration;

import com.handmade.ecommerce.experiment.model.ExperimentDefinition;
import com.handmade.ecommerce.experiment.service.cmds.*;
import com.handmade.ecommerce.experiment.service.healthcheck.ExperimentDefinitionHealthChecker;
import com.handmade.ecommerce.experiment.service.store.ExperimentDefinitionEntityStore;

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

import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class ExperimentDefinitionConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/experiment/experiment-definition-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "ExperimentDefinition";
    public static final String PREFIX_FOR_RESOLVER = "experimentDefinition";

    @Bean BeanFactoryAdapter experimentDefinitionBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl experimentDefinitionFlowStore(
            @Qualifier("experimentDefinitionBeanFactoryAdapter") BeanFactoryAdapter experimentDefinitionBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(experimentDefinitionBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<ExperimentDefinition> experimentDefinitionEntityStm(@Qualifier("experimentDefinitionFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<ExperimentDefinition> stm = new STMImpl<>();
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider experimentDefinitionActionsInfoProvider(@Qualifier("experimentDefinitionFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("experimentDefinition",provider);
        return provider;
	}
	
	@Bean EntityStore<ExperimentDefinition> experimentDefinitionEntityStore() {
		return new ExperimentDefinitionEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<ExperimentDefinition> _experimentDefinitionStateEntityService_(
			@Qualifier("experimentDefinitionEntityStm") STM<ExperimentDefinition> stm,
			@Qualifier("experimentDefinitionActionsInfoProvider") STMActionsInfoProvider experimentDefinitionInfoProvider,
			@Qualifier("experimentDefinitionEntityStore") EntityStore<ExperimentDefinition> entityStore){
		return new StateEntityServiceImpl<>(stm, experimentDefinitionInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<ExperimentDefinition> experimentDefinitionEntryAction(@Qualifier("experimentDefinitionEntityStore") EntityStore<ExperimentDefinition> entityStore,
			@Qualifier("experimentDefinitionActionsInfoProvider") STMActionsInfoProvider experimentDefinitionInfoProvider,
            @Qualifier("experimentDefinitionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<ExperimentDefinition> entryAction =  new GenericEntryAction<ExperimentDefinition>(entityStore,experimentDefinitionInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<ExperimentDefinition> experimentDefinitionExitAction(@Qualifier("experimentDefinitionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<ExperimentDefinition> exitAction = new GenericExitAction<ExperimentDefinition>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader experimentDefinitionFlowReader(@Qualifier("experimentDefinitionFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean
    ExperimentDefinitionHealthChecker experimentDefinitionHealthChecker(){
    	return new ExperimentDefinitionHealthChecker();
    }

    @Bean STMTransitionAction<ExperimentDefinition> defaultexperimentDefinitionSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver experimentDefinitionTransitionActionResolver(
    @Qualifier("defaultexperimentDefinitionSTMTransitionAction") STMTransitionAction<ExperimentDefinition> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector experimentDefinitionBodyTypeSelector(
    @Qualifier("experimentDefinitionActionsInfoProvider") STMActionsInfoProvider experimentDefinitionInfoProvider,
    @Qualifier("experimentDefinitionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(experimentDefinitionInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<ExperimentDefinition> experimentDefinitionBaseTransitionAction(
        @Qualifier("experimentDefinitionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "experimentDefinition" + eventId for the method name. (experimentDefinition is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/experimentDefinition/experimentDefinition-states.xml

    @Bean
    ResumeExperimentDefinitionAction
            resumeExperimentDefinitionAction(){
        return new ResumeExperimentDefinitionAction();
    }
    @Bean CompleteExperimentDefinitionAction
            completeExperimentDefinitionAction(){
        return new CompleteExperimentDefinitionAction();
    }
    @Bean
    PauseExperimentDefinitionAction
            pauseExperimentDefinitionAction(){
        return new PauseExperimentDefinitionAction();
    }
    @Bean
    ActivateExperimentDefinitionAction
            activateExperimentDefinitionAction(){
        return new ActivateExperimentDefinitionAction();
    }


}
