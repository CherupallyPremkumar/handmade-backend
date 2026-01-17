package com.handmade.ecommerce.deliveryattempt.configuration;

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
import com.handmade.ecommerce.logistics.model.DeliveryAttempt;
import com.handmade.ecommerce.deliveryattempt.service.cmds.*;
import com.handmade.ecommerce.deliveryattempt.service.healthcheck.DeliveryAttemptHealthChecker;
import com.handmade.ecommerce.deliveryattempt.service.store.DeliveryAttemptEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class DeliveryAttemptConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/deliveryattempt/deliveryattempt-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "DeliveryAttempt";
    public static final String PREFIX_FOR_RESOLVER = "deliveryattempt";

    @Bean BeanFactoryAdapter deliveryattemptBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl deliveryattemptFlowStore(
            @Qualifier("deliveryattemptBeanFactoryAdapter") BeanFactoryAdapter deliveryattemptBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(deliveryattemptBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<DeliveryAttempt> deliveryattemptEntityStm(@Qualifier("deliveryattemptFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<DeliveryAttempt> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider deliveryattemptActionsInfoProvider(@Qualifier("deliveryattemptFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("deliveryattempt",provider);
        return provider;
	}
	
	@Bean EntityStore<DeliveryAttempt> deliveryattemptEntityStore() {
		return new DeliveryAttemptEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<DeliveryAttempt> _deliveryattemptStateEntityService_(
			@Qualifier("deliveryattemptEntityStm") STM<DeliveryAttempt> stm,
			@Qualifier("deliveryattemptActionsInfoProvider") STMActionsInfoProvider deliveryattemptInfoProvider,
			@Qualifier("deliveryattemptEntityStore") EntityStore<DeliveryAttempt> entityStore){
		return new StateEntityServiceImpl<>(stm, deliveryattemptInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<DeliveryAttempt> deliveryattemptEntryAction(@Qualifier("deliveryattemptEntityStore") EntityStore<DeliveryAttempt> entityStore,
			@Qualifier("deliveryattemptActionsInfoProvider") STMActionsInfoProvider deliveryattemptInfoProvider,
            @Qualifier("deliveryattemptFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<DeliveryAttempt> entryAction =  new GenericEntryAction<DeliveryAttempt>(entityStore,deliveryattemptInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<DeliveryAttempt> deliveryattemptExitAction(@Qualifier("deliveryattemptFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<DeliveryAttempt> exitAction = new GenericExitAction<DeliveryAttempt>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader deliveryattemptFlowReader(@Qualifier("deliveryattemptFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean DeliveryAttemptHealthChecker deliveryattemptHealthChecker(){
    	return new DeliveryAttemptHealthChecker();
    }

    @Bean STMTransitionAction<DeliveryAttempt> defaultdeliveryattemptSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver deliveryattemptTransitionActionResolver(
    @Qualifier("defaultdeliveryattemptSTMTransitionAction") STMTransitionAction<DeliveryAttempt> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector deliveryattemptBodyTypeSelector(
    @Qualifier("deliveryattemptActionsInfoProvider") STMActionsInfoProvider deliveryattemptInfoProvider,
    @Qualifier("deliveryattemptTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(deliveryattemptInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<DeliveryAttempt> deliveryattemptBaseTransitionAction(
        @Qualifier("deliveryattemptTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "deliveryattempt" + eventId for the method name. (deliveryattempt is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/deliveryattempt/deliveryattempt-states.xml

    @Bean FailDeliveryAttemptAction
            deliveryattemptFail(){
        return new FailDeliveryAttemptAction();
    }
    @Bean SucceedDeliveryAttemptAction
            deliveryattemptSucceed(){
        return new SucceedDeliveryAttemptAction();
    }
    @Bean CancelDeliveryAttemptAction
            deliveryattemptCancel(){
        return new CancelDeliveryAttemptAction();
    }
    @Bean StartDeliveryAttemptAction
            deliveryattemptStart(){
        return new StartDeliveryAttemptAction();
    }


}
