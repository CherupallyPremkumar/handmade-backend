package com.handmade.ecommerce.experiment.configuration;

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
import com.handmade.ecommerce.experiment.model.Experiment;
import com.handmade.ecommerce.experiment.service.cmds.*;
import com.handmade.ecommerce.experiment.service.healthcheck.ExperimentHealthChecker;
import com.handmade.ecommerce.experiment.service.store.ExperimentEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class ExperimentConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/experiment/experiment-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Experiment";
    public static final String PREFIX_FOR_RESOLVER = "experiment";

    @Bean BeanFactoryAdapter experimentBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl experimentFlowStore(
            @Qualifier("experimentBeanFactoryAdapter") BeanFactoryAdapter experimentBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(experimentBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Experiment> experimentEntityStm(@Qualifier("experimentFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Experiment> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider experimentActionsInfoProvider(@Qualifier("experimentFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("experiment",provider);
        return provider;
	}
	
	@Bean EntityStore<Experiment> experimentEntityStore() {
		return new ExperimentEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Experiment> _experimentStateEntityService_(
			@Qualifier("experimentEntityStm") STM<Experiment> stm,
			@Qualifier("experimentActionsInfoProvider") STMActionsInfoProvider experimentInfoProvider,
			@Qualifier("experimentEntityStore") EntityStore<Experiment> entityStore){
		return new StateEntityServiceImpl<>(stm, experimentInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Experiment> experimentEntryAction(@Qualifier("experimentEntityStore") EntityStore<Experiment> entityStore,
			@Qualifier("experimentActionsInfoProvider") STMActionsInfoProvider experimentInfoProvider,
            @Qualifier("experimentFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Experiment> entryAction =  new GenericEntryAction<Experiment>(entityStore,experimentInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Experiment> experimentExitAction(@Qualifier("experimentFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Experiment> exitAction = new GenericExitAction<Experiment>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader experimentFlowReader(@Qualifier("experimentFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean ExperimentHealthChecker experimentHealthChecker(){
    	return new ExperimentHealthChecker();
    }

    @Bean STMTransitionAction<Experiment> defaultexperimentSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver experimentTransitionActionResolver(
    @Qualifier("defaultexperimentSTMTransitionAction") STMTransitionAction<Experiment> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector experimentBodyTypeSelector(
    @Qualifier("experimentActionsInfoProvider") STMActionsInfoProvider experimentInfoProvider,
    @Qualifier("experimentTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(experimentInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Experiment> experimentBaseTransitionAction(
        @Qualifier("experimentTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "experiment" + eventId for the method name. (experiment is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/experiment/experiment-states.xml

    @Bean ResumeExperimentAction
            experimentResume(){
        return new ResumeExperimentAction();
    }
    @Bean CompleteExperimentAction
            experimentComplete(){
        return new CompleteExperimentAction();
    }
    @Bean CompleteExperimentAction
            experimentComplete(){
        return new CompleteExperimentAction();
    }
    @Bean PauseExperimentAction
            experimentPause(){
        return new PauseExperimentAction();
    }
    @Bean ActivateExperimentAction
            experimentActivate(){
        return new ActivateExperimentAction();
    }


}
