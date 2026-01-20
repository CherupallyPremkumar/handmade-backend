package com.handmade.ecommerce.eventqueue.configuration;

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
import com.handmade.ecommerce.event.model.EventQueue;
import com.handmade.ecommerce.eventqueue.service.cmds.*;
import com.handmade.ecommerce.eventqueue.service.healthcheck.EventQueueHealthChecker;
import com.handmade.ecommerce.eventqueue.service.store.EventQueueEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class EventQueueConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/eventqueue/eventqueue-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "EventQueue";
    public static final String PREFIX_FOR_RESOLVER = "eventqueue";

    @Bean BeanFactoryAdapter eventqueueBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl eventqueueFlowStore(
            @Qualifier("eventqueueBeanFactoryAdapter") BeanFactoryAdapter eventqueueBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(eventqueueBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<EventQueue> eventqueueEntityStm(@Qualifier("eventqueueFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<EventQueue> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider eventqueueActionsInfoProvider(@Qualifier("eventqueueFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("eventqueue",provider);
        return provider;
	}
	
	@Bean EntityStore<EventQueue> eventqueueEntityStore() {
		return new EventQueueEntityStore();
	}
	
	@Bean StateEntityServiceImpl<EventQueue> _eventqueueStateEntityService_(
			@Qualifier("eventqueueEntityStm") STM<EventQueue> stm,
			@Qualifier("eventqueueActionsInfoProvider") STMActionsInfoProvider eventqueueInfoProvider,
			@Qualifier("eventqueueEntityStore") EntityStore<EventQueue> entityStore){
		return new StateEntityServiceImpl<>(stm, eventqueueInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<EventQueue> eventqueueEntryAction(@Qualifier("eventqueueEntityStore") EntityStore<EventQueue> entityStore,
			@Qualifier("eventqueueActionsInfoProvider") STMActionsInfoProvider eventqueueInfoProvider,
            @Qualifier("eventqueueFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<EventQueue> entryAction =  new GenericEntryAction<EventQueue>(entityStore,eventqueueInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<EventQueue> eventqueueExitAction(@Qualifier("eventqueueFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<EventQueue> exitAction = new GenericExitAction<EventQueue>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader eventqueueFlowReader(@Qualifier("eventqueueFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean EventQueueHealthChecker eventqueueHealthChecker(){
    	return new EventQueueHealthChecker();
    }

    @Bean STMTransitionAction<EventQueue> defaulteventqueueSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver eventqueueTransitionActionResolver(
    @Qualifier("defaulteventqueueSTMTransitionAction") STMTransitionAction<EventQueue> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector eventqueueBodyTypeSelector(
    @Qualifier("eventqueueActionsInfoProvider") STMActionsInfoProvider eventqueueInfoProvider,
    @Qualifier("eventqueueTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(eventqueueInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<EventQueue> eventqueueBaseTransitionAction(
        @Qualifier("eventqueueTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "eventqueue" + eventId for the method name. (eventqueue is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/eventqueue/eventqueue-states.xml

    @Bean ProcessEventQueueAction
            processEventQueueAction(){
        return new ProcessEventQueueAction();
    }
    @Bean FailEventQueueAction
            failEventQueueAction(){
        return new FailEventQueueAction();
    }
    @Bean CompleteEventQueueAction
            completeEventQueueAction(){
        return new CompleteEventQueueAction();
    }
    @Bean RetryEventQueueAction
            retryEventQueueAction(){
        return new RetryEventQueueAction();
    }


}
