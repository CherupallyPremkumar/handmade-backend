package com.handmade.ecommerce.notificationqueue.configuration;

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
import com.handmade.ecommerce.notification.model.NotificationQueue;
import com.handmade.ecommerce.notificationqueue.service.cmds.*;
import com.handmade.ecommerce.notificationqueue.service.healthcheck.NotificationQueueHealthChecker;
import com.handmade.ecommerce.notificationqueue.service.store.NotificationQueueEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class NotificationQueueConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/notificationqueue/notificationqueue-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "NotificationQueue";
    public static final String PREFIX_FOR_RESOLVER = "notificationqueue";

    @Bean BeanFactoryAdapter notificationqueueBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl notificationqueueFlowStore(
            @Qualifier("notificationqueueBeanFactoryAdapter") BeanFactoryAdapter notificationqueueBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(notificationqueueBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<NotificationQueue> notificationqueueEntityStm(@Qualifier("notificationqueueFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<NotificationQueue> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider notificationqueueActionsInfoProvider(@Qualifier("notificationqueueFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("notificationqueue",provider);
        return provider;
	}
	
	@Bean EntityStore<NotificationQueue> notificationqueueEntityStore() {
		return new NotificationQueueEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<NotificationQueue> _notificationqueueStateEntityService_(
			@Qualifier("notificationqueueEntityStm") STM<NotificationQueue> stm,
			@Qualifier("notificationqueueActionsInfoProvider") STMActionsInfoProvider notificationqueueInfoProvider,
			@Qualifier("notificationqueueEntityStore") EntityStore<NotificationQueue> entityStore){
		return new StateEntityServiceImpl<>(stm, notificationqueueInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<NotificationQueue> notificationqueueEntryAction(@Qualifier("notificationqueueEntityStore") EntityStore<NotificationQueue> entityStore,
			@Qualifier("notificationqueueActionsInfoProvider") STMActionsInfoProvider notificationqueueInfoProvider,
            @Qualifier("notificationqueueFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<NotificationQueue> entryAction =  new GenericEntryAction<NotificationQueue>(entityStore,notificationqueueInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<NotificationQueue> notificationqueueExitAction(@Qualifier("notificationqueueFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<NotificationQueue> exitAction = new GenericExitAction<NotificationQueue>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader notificationqueueFlowReader(@Qualifier("notificationqueueFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean NotificationQueueHealthChecker notificationqueueHealthChecker(){
    	return new NotificationQueueHealthChecker();
    }

    @Bean STMTransitionAction<NotificationQueue> defaultnotificationqueueSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver notificationqueueTransitionActionResolver(
    @Qualifier("defaultnotificationqueueSTMTransitionAction") STMTransitionAction<NotificationQueue> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector notificationqueueBodyTypeSelector(
    @Qualifier("notificationqueueActionsInfoProvider") STMActionsInfoProvider notificationqueueInfoProvider,
    @Qualifier("notificationqueueTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(notificationqueueInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<NotificationQueue> notificationqueueBaseTransitionAction(
        @Qualifier("notificationqueueTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "notificationqueue" + eventId for the method name. (notificationqueue is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/notificationqueue/notificationqueue-states.xml

    @Bean FailNotificationQueueAction
            notificationqueueFail(){
        return new FailNotificationQueueAction();
    }
    @Bean SucceedNotificationQueueAction
            notificationqueueSucceed(){
        return new SucceedNotificationQueueAction();
    }
    @Bean AbandonNotificationQueueAction
            notificationqueueAbandon(){
        return new AbandonNotificationQueueAction();
    }
    @Bean RetryNotificationQueueAction
            notificationqueueRetry(){
        return new RetryNotificationQueueAction();
    }
    @Bean CancelNotificationQueueAction
            notificationqueueCancel(){
        return new CancelNotificationQueueAction();
    }
    @Bean SendNotificationQueueAction
            notificationqueueSend(){
        return new SendNotificationQueueAction();
    }


}
