package com.handmade.ecommerce.routeplan.configuration;

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
import com.handmade.ecommerce.logistics.model.RoutePlan;
import com.handmade.ecommerce.routeplan.service.cmds.*;
import com.handmade.ecommerce.routeplan.service.healthcheck.RoutePlanHealthChecker;
import com.handmade.ecommerce.routeplan.service.store.RoutePlanEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class RoutePlanConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/routeplan/routeplan-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "RoutePlan";
    public static final String PREFIX_FOR_RESOLVER = "routeplan";

    @Bean BeanFactoryAdapter routeplanBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl routeplanFlowStore(
            @Qualifier("routeplanBeanFactoryAdapter") BeanFactoryAdapter routeplanBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(routeplanBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<RoutePlan> routeplanEntityStm(@Qualifier("routeplanFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<RoutePlan> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider routeplanActionsInfoProvider(@Qualifier("routeplanFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("routeplan",provider);
        return provider;
	}
	
	@Bean EntityStore<RoutePlan> routeplanEntityStore() {
		return new RoutePlanEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<RoutePlan> _routeplanStateEntityService_(
			@Qualifier("routeplanEntityStm") STM<RoutePlan> stm,
			@Qualifier("routeplanActionsInfoProvider") STMActionsInfoProvider routeplanInfoProvider,
			@Qualifier("routeplanEntityStore") EntityStore<RoutePlan> entityStore){
		return new StateEntityServiceImpl<>(stm, routeplanInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<RoutePlan> routeplanEntryAction(@Qualifier("routeplanEntityStore") EntityStore<RoutePlan> entityStore,
			@Qualifier("routeplanActionsInfoProvider") STMActionsInfoProvider routeplanInfoProvider,
            @Qualifier("routeplanFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<RoutePlan> entryAction =  new GenericEntryAction<RoutePlan>(entityStore,routeplanInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<RoutePlan> routeplanExitAction(@Qualifier("routeplanFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<RoutePlan> exitAction = new GenericExitAction<RoutePlan>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader routeplanFlowReader(@Qualifier("routeplanFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean RoutePlanHealthChecker routeplanHealthChecker(){
    	return new RoutePlanHealthChecker();
    }

    @Bean STMTransitionAction<RoutePlan> defaultrouteplanSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver routeplanTransitionActionResolver(
    @Qualifier("defaultrouteplanSTMTransitionAction") STMTransitionAction<RoutePlan> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector routeplanBodyTypeSelector(
    @Qualifier("routeplanActionsInfoProvider") STMActionsInfoProvider routeplanInfoProvider,
    @Qualifier("routeplanTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(routeplanInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<RoutePlan> routeplanBaseTransitionAction(
        @Qualifier("routeplanTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "routeplan" + eventId for the method name. (routeplan is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/routeplan/routeplan-states.xml

    @Bean CompleteRoutePlanAction
            routeplanComplete(){
        return new CompleteRoutePlanAction();
    }
    @Bean CancelRoutePlanAction
            routeplanCancel(){
        return new CancelRoutePlanAction();
    }
    @Bean AssignRoutePlanAction
            routeplanAssign(){
        return new AssignRoutePlanAction();
    }
    @Bean StartRoutePlanAction
            routeplanStart(){
        return new StartRoutePlanAction();
    }


}
