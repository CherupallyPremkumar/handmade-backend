package com.handmade.ecommerce.notificationlog.configuration;

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
import com.handmade.ecommerce.notification.model.NotificationLog;
import com.handmade.ecommerce.notificationlog.service.cmds.*;
import com.handmade.ecommerce.notificationlog.service.healthcheck.NotificationLogHealthChecker;
import com.handmade.ecommerce.notificationlog.service.store.NotificationLogEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class NotificationLogConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/notificationlog/notificationlog-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "NotificationLog";
    public static final String PREFIX_FOR_RESOLVER = "notificationlog";

    @Bean BeanFactoryAdapter notificationlogBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl notificationlogFlowStore(
            @Qualifier("notificationlogBeanFactoryAdapter") BeanFactoryAdapter notificationlogBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(notificationlogBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<NotificationLog> notificationlogEntityStm(@Qualifier("notificationlogFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<NotificationLog> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider notificationlogActionsInfoProvider(@Qualifier("notificationlogFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("notificationlog",provider);
        return provider;
	}
	
	@Bean EntityStore<NotificationLog> notificationlogEntityStore() {
		return new NotificationLogEntityStore();
	}
	
	@Bean StateEntityServiceImpl<NotificationLog> _notificationlogStateEntityService_(
			@Qualifier("notificationlogEntityStm") STM<NotificationLog> stm,
			@Qualifier("notificationlogActionsInfoProvider") STMActionsInfoProvider notificationlogInfoProvider,
			@Qualifier("notificationlogEntityStore") EntityStore<NotificationLog> entityStore){
		return new StateEntityServiceImpl<>(stm, notificationlogInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<NotificationLog> notificationlogEntryAction(@Qualifier("notificationlogEntityStore") EntityStore<NotificationLog> entityStore,
			@Qualifier("notificationlogActionsInfoProvider") STMActionsInfoProvider notificationlogInfoProvider,
            @Qualifier("notificationlogFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<NotificationLog> entryAction =  new GenericEntryAction<NotificationLog>(entityStore,notificationlogInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<NotificationLog> notificationlogExitAction(@Qualifier("notificationlogFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<NotificationLog> exitAction = new GenericExitAction<NotificationLog>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader notificationlogFlowReader(@Qualifier("notificationlogFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean NotificationLogHealthChecker notificationlogHealthChecker(){
    	return new NotificationLogHealthChecker();
    }

    @Bean STMTransitionAction<NotificationLog> defaultnotificationlogSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver notificationlogTransitionActionResolver(
    @Qualifier("defaultnotificationlogSTMTransitionAction") STMTransitionAction<NotificationLog> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector notificationlogBodyTypeSelector(
    @Qualifier("notificationlogActionsInfoProvider") STMActionsInfoProvider notificationlogInfoProvider,
    @Qualifier("notificationlogTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(notificationlogInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<NotificationLog> notificationlogBaseTransitionAction(
        @Qualifier("notificationlogTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "notificationlog" + eventId for the method name. (notificationlog is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/notificationlog/notificationlog-states.xml


    @Bean OpenNotificationLogAction
            openNotificationLogAction(){
        return new OpenNotificationLogAction();
    }
    @Bean FailNotificationLogAction
            failNotificationLogAction(){
        return new FailNotificationLogAction();
    }
    @Bean BounceNotificationLogAction
            bounceNotificationLogAction(){
        return new BounceNotificationLogAction();
    }
    @Bean DeliverNotificationLogAction
            deliverNotificationLogAction(){
        return new DeliverNotificationLogAction();
    }
    @Bean ClickNotificationLogAction
            clickNotificationLogAction(){
        return new ClickNotificationLogAction();
    }


}
