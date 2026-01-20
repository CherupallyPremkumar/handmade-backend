package com.handmade.ecommerce.limitdefinition.configuration;

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
import com.handmade.ecommerce.limit.model.LimitDefinition;
import com.handmade.ecommerce.limitdefinition.service.cmds.*;
import com.handmade.ecommerce.limitdefinition.service.healthcheck.LimitDefinitionHealthChecker;
import com.handmade.ecommerce.limitdefinition.service.store.LimitDefinitionEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class LimitDefinitionConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/limitdefinition/limitdefinition-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "LimitDefinition";
    public static final String PREFIX_FOR_RESOLVER = "limitdefinition";

    @Bean BeanFactoryAdapter limitdefinitionBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl limitdefinitionFlowStore(
            @Qualifier("limitdefinitionBeanFactoryAdapter") BeanFactoryAdapter limitdefinitionBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(limitdefinitionBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<LimitDefinition> limitdefinitionEntityStm(@Qualifier("limitdefinitionFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<LimitDefinition> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider limitdefinitionActionsInfoProvider(@Qualifier("limitdefinitionFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("limitdefinition",provider);
        return provider;
	}
	
	@Bean EntityStore<LimitDefinition> limitdefinitionEntityStore() {
		return new LimitDefinitionEntityStore();
	}
	
	@Bean StateEntityServiceImpl<LimitDefinition> _limitdefinitionStateEntityService_(
			@Qualifier("limitdefinitionEntityStm") STM<LimitDefinition> stm,
			@Qualifier("limitdefinitionActionsInfoProvider") STMActionsInfoProvider limitdefinitionInfoProvider,
			@Qualifier("limitdefinitionEntityStore") EntityStore<LimitDefinition> entityStore){
		return new StateEntityServiceImpl<>(stm, limitdefinitionInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<LimitDefinition> limitdefinitionEntryAction(@Qualifier("limitdefinitionEntityStore") EntityStore<LimitDefinition> entityStore,
			@Qualifier("limitdefinitionActionsInfoProvider") STMActionsInfoProvider limitdefinitionInfoProvider,
            @Qualifier("limitdefinitionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<LimitDefinition> entryAction =  new GenericEntryAction<LimitDefinition>(entityStore,limitdefinitionInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<LimitDefinition> limitdefinitionExitAction(@Qualifier("limitdefinitionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<LimitDefinition> exitAction = new GenericExitAction<LimitDefinition>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader limitdefinitionFlowReader(@Qualifier("limitdefinitionFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean LimitDefinitionHealthChecker limitdefinitionHealthChecker(){
    	return new LimitDefinitionHealthChecker();
    }

    @Bean STMTransitionAction<LimitDefinition> defaultlimitdefinitionSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver limitdefinitionTransitionActionResolver(
    @Qualifier("defaultlimitdefinitionSTMTransitionAction") STMTransitionAction<LimitDefinition> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector limitdefinitionBodyTypeSelector(
    @Qualifier("limitdefinitionActionsInfoProvider") STMActionsInfoProvider limitdefinitionInfoProvider,
    @Qualifier("limitdefinitionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(limitdefinitionInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<LimitDefinition> limitdefinitionBaseTransitionAction(
        @Qualifier("limitdefinitionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "limitdefinition" + eventId for the method name. (limitdefinition is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/limitdefinition/limitdefinition-states.xml

    @Bean SuspendLimitDefinitionAction
            suspendLimitDefinitionAction(){
        return new SuspendLimitDefinitionAction();
    }
    @Bean DeprecateLimitDefinitionAction
            deprecateLimitDefinitionAction(){
        return new DeprecateLimitDefinitionAction();
    }
    @Bean ActivateLimitDefinitionAction
            activateLimitDefinitionAction(){
        return new ActivateLimitDefinitionAction();
    }
    @Bean ReactivateLimitDefinitionAction
            reactivateLimitDefinitionAction(){
        return new ReactivateLimitDefinitionAction();
    }


}
