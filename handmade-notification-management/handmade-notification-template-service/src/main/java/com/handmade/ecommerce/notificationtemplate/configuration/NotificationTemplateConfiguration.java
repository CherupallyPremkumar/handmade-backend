package com.handmade.ecommerce.notificationtemplate.configuration;

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
import com.handmade.ecommerce.notification.model.NotificationTemplate;
import com.handmade.ecommerce.notificationtemplate.service.cmds.*;
import com.handmade.ecommerce.notificationtemplate.service.healthcheck.NotificationTemplateHealthChecker;
import com.handmade.ecommerce.notificationtemplate.service.store.NotificationTemplateEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class NotificationTemplateConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/notificationtemplate/notificationtemplate-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "NotificationTemplate";
    public static final String PREFIX_FOR_RESOLVER = "notificationtemplate";

    @Bean BeanFactoryAdapter notificationtemplateBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl notificationtemplateFlowStore(
            @Qualifier("notificationtemplateBeanFactoryAdapter") BeanFactoryAdapter notificationtemplateBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(notificationtemplateBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<NotificationTemplate> notificationtemplateEntityStm(@Qualifier("notificationtemplateFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<NotificationTemplate> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider notificationtemplateActionsInfoProvider(@Qualifier("notificationtemplateFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("notificationtemplate",provider);
        return provider;
	}
	
	@Bean EntityStore<NotificationTemplate> notificationtemplateEntityStore() {
		return new NotificationTemplateEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<NotificationTemplate> _notificationtemplateStateEntityService_(
			@Qualifier("notificationtemplateEntityStm") STM<NotificationTemplate> stm,
			@Qualifier("notificationtemplateActionsInfoProvider") STMActionsInfoProvider notificationtemplateInfoProvider,
			@Qualifier("notificationtemplateEntityStore") EntityStore<NotificationTemplate> entityStore){
		return new StateEntityServiceImpl<>(stm, notificationtemplateInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<NotificationTemplate> notificationtemplateEntryAction(@Qualifier("notificationtemplateEntityStore") EntityStore<NotificationTemplate> entityStore,
			@Qualifier("notificationtemplateActionsInfoProvider") STMActionsInfoProvider notificationtemplateInfoProvider,
            @Qualifier("notificationtemplateFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<NotificationTemplate> entryAction =  new GenericEntryAction<NotificationTemplate>(entityStore,notificationtemplateInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<NotificationTemplate> notificationtemplateExitAction(@Qualifier("notificationtemplateFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<NotificationTemplate> exitAction = new GenericExitAction<NotificationTemplate>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader notificationtemplateFlowReader(@Qualifier("notificationtemplateFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean NotificationTemplateHealthChecker notificationtemplateHealthChecker(){
    	return new NotificationTemplateHealthChecker();
    }

    @Bean STMTransitionAction<NotificationTemplate> defaultnotificationtemplateSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver notificationtemplateTransitionActionResolver(
    @Qualifier("defaultnotificationtemplateSTMTransitionAction") STMTransitionAction<NotificationTemplate> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector notificationtemplateBodyTypeSelector(
    @Qualifier("notificationtemplateActionsInfoProvider") STMActionsInfoProvider notificationtemplateInfoProvider,
    @Qualifier("notificationtemplateTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(notificationtemplateInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<NotificationTemplate> notificationtemplateBaseTransitionAction(
        @Qualifier("notificationtemplateTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "notificationtemplate" + eventId for the method name. (notificationtemplate is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/notificationtemplate/notificationtemplate-states.xml

    @Bean DeprecateNotificationTemplateAction
            notificationtemplateDeprecate(){
        return new DeprecateNotificationTemplateAction();
    }
    @Bean DeactivateNotificationTemplateAction
            notificationtemplateDeactivate(){
        return new DeactivateNotificationTemplateAction();
    }
    @Bean SubmitNotificationTemplateAction
            notificationtemplateSubmit(){
        return new SubmitNotificationTemplateAction();
    }
    @Bean DeprecateNotificationTemplateAction
            notificationtemplateDeprecate(){
        return new DeprecateNotificationTemplateAction();
    }
    @Bean ActivateNotificationTemplateAction
            notificationtemplateActivate(){
        return new ActivateNotificationTemplateAction();
    }
    @Bean ApproveNotificationTemplateAction
            notificationtemplateApprove(){
        return new ApproveNotificationTemplateAction();
    }
    @Bean RejectNotificationTemplateAction
            notificationtemplateReject(){
        return new RejectNotificationTemplateAction();
    }
    @Bean ActivateNotificationTemplateAction
            notificationtemplateActivate(){
        return new ActivateNotificationTemplateAction();
    }


}
