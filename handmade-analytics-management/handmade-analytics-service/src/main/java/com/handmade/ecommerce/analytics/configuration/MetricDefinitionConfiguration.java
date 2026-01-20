package com.handmade.ecommerce.analytics.configuration;

import com.handmade.ecommerce.analytics.configuration.dao.MetricDefinitionRepository;
import com.handmade.ecommerce.analytics.service.healthcheck.MetricDefinitionHealthChecker;
import com.handmade.ecommerce.analytics.service.store.MetricDefinitionEntityStore;
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
import com.handmade.ecommerce.analytics.model.MetricDefinition;
import com.handmade.ecommerce.analytics.service.cmds.*;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class MetricDefinitionConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/analytics/analytics-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "MetricDefinition";
    public static final String PREFIX_FOR_RESOLVER = "analytics";

    @Bean BeanFactoryAdapter analyticsBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl analyticsFlowStore(
            @Qualifier("analyticsBeanFactoryAdapter") BeanFactoryAdapter analyticsBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(analyticsBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<MetricDefinition> analyticsEntityStm(@Qualifier("analyticsFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<MetricDefinition> stm = new STMImpl<>();
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider analyticsActionsInfoProvider(@Qualifier("analyticsFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("analytics",provider);
        return provider;
	}
	
	@Bean
    EntityStore<MetricDefinition> analyticsEntityStore(MetricDefinitionRepository metricDefinitionRepository) {
		return new MetricDefinitionEntityStore(metricDefinitionRepository);
	}
	
	@Bean StateEntityServiceImpl<MetricDefinition> _analyticsStateEntityService_(
			@Qualifier("analyticsEntityStm") STM<MetricDefinition> stm,
			@Qualifier("analyticsActionsInfoProvider") STMActionsInfoProvider analyticsInfoProvider,
			@Qualifier("analyticsEntityStore") EntityStore<MetricDefinition> entityStore){
		return new StateEntityServiceImpl<>(stm, analyticsInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<MetricDefinition> analyticsEntryAction(@Qualifier("analyticsEntityStore") EntityStore<MetricDefinition> entityStore,
			@Qualifier("analyticsActionsInfoProvider") STMActionsInfoProvider analyticsInfoProvider,
            @Qualifier("analyticsFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<MetricDefinition> entryAction =  new GenericEntryAction<MetricDefinition>(entityStore,analyticsInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<MetricDefinition> analyticsExitAction(@Qualifier("analyticsFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<MetricDefinition> exitAction = new GenericExitAction<MetricDefinition>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader analyticsFlowReader(@Qualifier("analyticsFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean
    MetricDefinitionHealthChecker analyticsHealthChecker(){
    	return new MetricDefinitionHealthChecker();
    }

    @Bean STMTransitionAction<MetricDefinition> defaultanalyticsSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver analyticsTransitionActionResolver(
    @Qualifier("defaultanalyticsSTMTransitionAction") STMTransitionAction<MetricDefinition> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector analyticsBodyTypeSelector(
    @Qualifier("analyticsActionsInfoProvider") STMActionsInfoProvider analyticsInfoProvider,
    @Qualifier("analyticsTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(analyticsInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<MetricDefinition> analyticsBaseTransitionAction(
        @Qualifier("analyticsTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "analytics" + eventId for the method name. (analytics is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/analytics/analytics-states.xml

    @Bean ResumeMetricDefinitionAction
            resumeMetricDefinitionAction(){
        return new ResumeMetricDefinitionAction();
    }
    @Bean PauseMetricDefinitionAction
           pauseMetricDefinitionAction(){
        return new PauseMetricDefinitionAction();
    }
    @Bean UpdateThresholdMetricDefinitionAction
    updateThresholdMetricDefinitionAction(){
        return new UpdateThresholdMetricDefinitionAction();
    }
    @Bean ActivateMetricDefinitionAction
            activateMetricDefinitionAction(){
        return new ActivateMetricDefinitionAction();
    }
    @Bean ArchiveMetricDefinitionAction
    archiveMetricDefinitionAction(){
        return new ArchiveMetricDefinitionAction();
    }


}
