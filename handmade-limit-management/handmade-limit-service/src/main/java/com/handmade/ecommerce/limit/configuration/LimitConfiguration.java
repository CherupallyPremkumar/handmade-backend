package com.handmade.ecommerce.limit.configuration;

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
import com.handmade.ecommerce.limit.model.Limit;
import com.handmade.ecommerce.limit.service.cmds.*;
import com.handmade.ecommerce.limit.service.healthcheck.LimitHealthChecker;
import com.handmade.ecommerce.limit.service.store.LimitEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class LimitConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/limit/limit-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Limit";
    public static final String PREFIX_FOR_RESOLVER = "limit";

    @Bean BeanFactoryAdapter limitBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl limitFlowStore(
            @Qualifier("limitBeanFactoryAdapter") BeanFactoryAdapter limitBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(limitBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Limit> limitEntityStm(@Qualifier("limitFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Limit> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider limitActionsInfoProvider(@Qualifier("limitFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("limit",provider);
        return provider;
	}
	
	@Bean EntityStore<Limit> limitEntityStore() {
		return new LimitEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Limit> _limitStateEntityService_(
			@Qualifier("limitEntityStm") STM<Limit> stm,
			@Qualifier("limitActionsInfoProvider") STMActionsInfoProvider limitInfoProvider,
			@Qualifier("limitEntityStore") EntityStore<Limit> entityStore){
		return new StateEntityServiceImpl<>(stm, limitInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Limit> limitEntryAction(@Qualifier("limitEntityStore") EntityStore<Limit> entityStore,
			@Qualifier("limitActionsInfoProvider") STMActionsInfoProvider limitInfoProvider,
            @Qualifier("limitFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Limit> entryAction =  new GenericEntryAction<Limit>(entityStore,limitInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Limit> limitExitAction(@Qualifier("limitFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Limit> exitAction = new GenericExitAction<Limit>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader limitFlowReader(@Qualifier("limitFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean LimitHealthChecker limitHealthChecker(){
    	return new LimitHealthChecker();
    }

    @Bean STMTransitionAction<Limit> defaultlimitSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver limitTransitionActionResolver(
    @Qualifier("defaultlimitSTMTransitionAction") STMTransitionAction<Limit> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector limitBodyTypeSelector(
    @Qualifier("limitActionsInfoProvider") STMActionsInfoProvider limitInfoProvider,
    @Qualifier("limitTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(limitInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Limit> limitBaseTransitionAction(
        @Qualifier("limitTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "limit" + eventId for the method name. (limit is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/limit/limit-states.xml

    @Bean ResumeLimitAction
            limitResume(){
        return new ResumeLimitAction();
    }
    @Bean ArchiveLimitAction
            limitArchive(){
        return new ArchiveLimitAction();
    }
    @Bean ArchiveLimitAction
            limitArchive(){
        return new ArchiveLimitAction();
    }
    @Bean PauseLimitAction
            limitPause(){
        return new PauseLimitAction();
    }
    @Bean ActivateLimitAction
            limitActivate(){
        return new ActivateLimitAction();
    }


}
