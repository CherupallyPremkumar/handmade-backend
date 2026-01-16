package com.handmade.ecommerce.support.configuration;

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
import com.handmade.ecommerce.support.model.Support;
import com.handmade.ecommerce.support.service.cmds.*;
import com.handmade.ecommerce.support.service.healthcheck.SupportHealthChecker;
import com.handmade.ecommerce.support.service.store.SupportEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class SupportConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/support/support-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Support";
    public static final String PREFIX_FOR_RESOLVER = "support";

    @Bean BeanFactoryAdapter supportBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl supportFlowStore(
            @Qualifier("supportBeanFactoryAdapter") BeanFactoryAdapter supportBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(supportBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Support> supportEntityStm(@Qualifier("supportFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Support> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider supportActionsInfoProvider(@Qualifier("supportFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("support",provider);
        return provider;
	}
	
	@Bean EntityStore<Support> supportEntityStore() {
		return new SupportEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Support> _supportStateEntityService_(
			@Qualifier("supportEntityStm") STM<Support> stm,
			@Qualifier("supportActionsInfoProvider") STMActionsInfoProvider supportInfoProvider,
			@Qualifier("supportEntityStore") EntityStore<Support> entityStore){
		return new StateEntityServiceImpl<>(stm, supportInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Support> supportEntryAction(@Qualifier("supportEntityStore") EntityStore<Support> entityStore,
			@Qualifier("supportActionsInfoProvider") STMActionsInfoProvider supportInfoProvider,
            @Qualifier("supportFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Support> entryAction =  new GenericEntryAction<Support>(entityStore,supportInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Support> supportExitAction(@Qualifier("supportFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Support> exitAction = new GenericExitAction<Support>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader supportFlowReader(@Qualifier("supportFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean SupportHealthChecker supportHealthChecker(){
    	return new SupportHealthChecker();
    }

    @Bean STMTransitionAction<Support> defaultsupportSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver supportTransitionActionResolver(
    @Qualifier("defaultsupportSTMTransitionAction") STMTransitionAction<Support> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector supportBodyTypeSelector(
    @Qualifier("supportActionsInfoProvider") STMActionsInfoProvider supportInfoProvider,
    @Qualifier("supportTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(supportInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Support> supportBaseTransitionAction(
        @Qualifier("supportTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "support" + eventId for the method name. (support is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/support/support-states.xml

    @Bean ResolveSupportAction
            supportResolve(){
        return new ResolveSupportAction();
    }
    @Bean RespondSupportAction
            supportRespond(){
        return new RespondSupportAction();
    }
    @Bean ResolveSupportAction
            supportResolve(){
        return new ResolveSupportAction();
    }
    @Bean AssignSupportAction
            supportAssign(){
        return new AssignSupportAction();
    }
    @Bean ReopenSupportAction
            supportReopen(){
        return new ReopenSupportAction();
    }


}
