package com.handmade.ecommerce.limitcounter.configuration;

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
import com.handmade.ecommerce.limit.model.LimitCounter;
import com.handmade.ecommerce.limitcounter.service.cmds.*;
import com.handmade.ecommerce.limitcounter.service.healthcheck.LimitCounterHealthChecker;
import com.handmade.ecommerce.limitcounter.service.store.LimitCounterEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class LimitCounterConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/limitcounter/limitcounter-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "LimitCounter";
    public static final String PREFIX_FOR_RESOLVER = "limitcounter";

    @Bean BeanFactoryAdapter limitcounterBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl limitcounterFlowStore(
            @Qualifier("limitcounterBeanFactoryAdapter") BeanFactoryAdapter limitcounterBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(limitcounterBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<LimitCounter> limitcounterEntityStm(@Qualifier("limitcounterFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<LimitCounter> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider limitcounterActionsInfoProvider(@Qualifier("limitcounterFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("limitcounter",provider);
        return provider;
	}
	
	@Bean EntityStore<LimitCounter> limitcounterEntityStore() {
		return new LimitCounterEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<LimitCounter> _limitcounterStateEntityService_(
			@Qualifier("limitcounterEntityStm") STM<LimitCounter> stm,
			@Qualifier("limitcounterActionsInfoProvider") STMActionsInfoProvider limitcounterInfoProvider,
			@Qualifier("limitcounterEntityStore") EntityStore<LimitCounter> entityStore){
		return new StateEntityServiceImpl<>(stm, limitcounterInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<LimitCounter> limitcounterEntryAction(@Qualifier("limitcounterEntityStore") EntityStore<LimitCounter> entityStore,
			@Qualifier("limitcounterActionsInfoProvider") STMActionsInfoProvider limitcounterInfoProvider,
            @Qualifier("limitcounterFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<LimitCounter> entryAction =  new GenericEntryAction<LimitCounter>(entityStore,limitcounterInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<LimitCounter> limitcounterExitAction(@Qualifier("limitcounterFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<LimitCounter> exitAction = new GenericExitAction<LimitCounter>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader limitcounterFlowReader(@Qualifier("limitcounterFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean LimitCounterHealthChecker limitcounterHealthChecker(){
    	return new LimitCounterHealthChecker();
    }

    @Bean STMTransitionAction<LimitCounter> defaultlimitcounterSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver limitcounterTransitionActionResolver(
    @Qualifier("defaultlimitcounterSTMTransitionAction") STMTransitionAction<LimitCounter> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector limitcounterBodyTypeSelector(
    @Qualifier("limitcounterActionsInfoProvider") STMActionsInfoProvider limitcounterInfoProvider,
    @Qualifier("limitcounterTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(limitcounterInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<LimitCounter> limitcounterBaseTransitionAction(
        @Qualifier("limitcounterTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "limitcounter" + eventId for the method name. (limitcounter is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/limitcounter/limitcounter-states.xml

    @Bean BreachLimitCounterAction
            limitcounterBreach(){
        return new BreachLimitCounterAction();
    }
    @Bean ResetLimitCounterAction
            limitcounterReset(){
        return new ResetLimitCounterAction();
    }
    @Bean ActivateLimitCounterAction
            limitcounterActivate(){
        return new ActivateLimitCounterAction();
    }
    @Bean ResetLimitCounterAction
            limitcounterReset(){
        return new ResetLimitCounterAction();
    }


}
