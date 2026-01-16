package com.handmade.ecommerce.logistics.configuration;

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
import com.handmade.ecommerce.logistics.model.Logistics;
import com.handmade.ecommerce.logistics.service.cmds.*;
import com.handmade.ecommerce.logistics.service.healthcheck.LogisticsHealthChecker;
import com.handmade.ecommerce.logistics.service.store.LogisticsEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class LogisticsConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/logistics/logistics-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Logistics";
    public static final String PREFIX_FOR_RESOLVER = "logistics";

    @Bean BeanFactoryAdapter logisticsBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl logisticsFlowStore(
            @Qualifier("logisticsBeanFactoryAdapter") BeanFactoryAdapter logisticsBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(logisticsBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Logistics> logisticsEntityStm(@Qualifier("logisticsFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Logistics> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider logisticsActionsInfoProvider(@Qualifier("logisticsFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("logistics",provider);
        return provider;
	}
	
	@Bean EntityStore<Logistics> logisticsEntityStore() {
		return new LogisticsEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Logistics> _logisticsStateEntityService_(
			@Qualifier("logisticsEntityStm") STM<Logistics> stm,
			@Qualifier("logisticsActionsInfoProvider") STMActionsInfoProvider logisticsInfoProvider,
			@Qualifier("logisticsEntityStore") EntityStore<Logistics> entityStore){
		return new StateEntityServiceImpl<>(stm, logisticsInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Logistics> logisticsEntryAction(@Qualifier("logisticsEntityStore") EntityStore<Logistics> entityStore,
			@Qualifier("logisticsActionsInfoProvider") STMActionsInfoProvider logisticsInfoProvider,
            @Qualifier("logisticsFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Logistics> entryAction =  new GenericEntryAction<Logistics>(entityStore,logisticsInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Logistics> logisticsExitAction(@Qualifier("logisticsFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Logistics> exitAction = new GenericExitAction<Logistics>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader logisticsFlowReader(@Qualifier("logisticsFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean LogisticsHealthChecker logisticsHealthChecker(){
    	return new LogisticsHealthChecker();
    }

    @Bean STMTransitionAction<Logistics> defaultlogisticsSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver logisticsTransitionActionResolver(
    @Qualifier("defaultlogisticsSTMTransitionAction") STMTransitionAction<Logistics> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector logisticsBodyTypeSelector(
    @Qualifier("logisticsActionsInfoProvider") STMActionsInfoProvider logisticsInfoProvider,
    @Qualifier("logisticsTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(logisticsInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Logistics> logisticsBaseTransitionAction(
        @Qualifier("logisticsTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "logistics" + eventId for the method name. (logistics is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/logistics/logistics-states.xml

    @Bean MarkDeliveredLogisticsAction
            logisticsMarkDelivered(){
        return new MarkDeliveredLogisticsAction();
    }
    @Bean MarkFailedLogisticsAction
            logisticsMarkFailed(){
        return new MarkFailedLogisticsAction();
    }
    @Bean RescheduleLogisticsAction
            logisticsReschedule(){
        return new RescheduleLogisticsAction();
    }
    @Bean StartAttemptLogisticsAction
            logisticsStartAttempt(){
        return new StartAttemptLogisticsAction();
    }


}
