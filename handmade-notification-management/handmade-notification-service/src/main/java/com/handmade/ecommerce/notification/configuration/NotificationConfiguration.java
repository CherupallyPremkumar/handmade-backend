package com.handmade.ecommerce.notification.configuration;

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
import com.handmade.ecommerce.notification.model.Notification;
import com.handmade.ecommerce.notification.service.cmds.*;
import com.handmade.ecommerce.notification.service.healthcheck.NotificationHealthChecker;
import com.handmade.ecommerce.notification.service.store.NotificationEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class NotificationConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/notification/notification-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Notification";
    public static final String PREFIX_FOR_RESOLVER = "notification";

    @Bean BeanFactoryAdapter notificationBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl notificationFlowStore(
            @Qualifier("notificationBeanFactoryAdapter") BeanFactoryAdapter notificationBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(notificationBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Notification> notificationEntityStm(@Qualifier("notificationFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Notification> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider notificationActionsInfoProvider(@Qualifier("notificationFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("notification",provider);
        return provider;
	}
	
	@Bean EntityStore<Notification> notificationEntityStore() {
		return new NotificationEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Notification> _notificationStateEntityService_(
			@Qualifier("notificationEntityStm") STM<Notification> stm,
			@Qualifier("notificationActionsInfoProvider") STMActionsInfoProvider notificationInfoProvider,
			@Qualifier("notificationEntityStore") EntityStore<Notification> entityStore){
		return new StateEntityServiceImpl<>(stm, notificationInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Notification> notificationEntryAction(@Qualifier("notificationEntityStore") EntityStore<Notification> entityStore,
			@Qualifier("notificationActionsInfoProvider") STMActionsInfoProvider notificationInfoProvider,
            @Qualifier("notificationFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Notification> entryAction =  new GenericEntryAction<Notification>(entityStore,notificationInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Notification> notificationExitAction(@Qualifier("notificationFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Notification> exitAction = new GenericExitAction<Notification>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader notificationFlowReader(@Qualifier("notificationFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean NotificationHealthChecker notificationHealthChecker(){
    	return new NotificationHealthChecker();
    }

    @Bean STMTransitionAction<Notification> defaultnotificationSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver notificationTransitionActionResolver(
    @Qualifier("defaultnotificationSTMTransitionAction") STMTransitionAction<Notification> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector notificationBodyTypeSelector(
    @Qualifier("notificationActionsInfoProvider") STMActionsInfoProvider notificationInfoProvider,
    @Qualifier("notificationTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(notificationInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Notification> notificationBaseTransitionAction(
        @Qualifier("notificationTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "notification" + eventId for the method name. (notification is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/notification/notification-states.xml

    @Bean RetryNotificationNotificationAction
            notificationRetryNotification(){
        return new RetryNotificationNotificationAction();
    }
    @Bean StartProcessingNotificationAction
            notificationStartProcessing(){
        return new StartProcessingNotificationAction();
    }
    @Bean MarkFailedNotificationAction
            notificationMarkFailed(){
        return new MarkFailedNotificationAction();
    }
    @Bean SendNotificationNotificationAction
            notificationSendNotification(){
        return new SendNotificationNotificationAction();
    }
    @Bean MarkDeliveredNotificationAction
            notificationMarkDelivered(){
        return new MarkDeliveredNotificationAction();
    }
    @Bean MarkFailedNotificationAction
            notificationMarkFailed(){
        return new MarkFailedNotificationAction();
    }


}
