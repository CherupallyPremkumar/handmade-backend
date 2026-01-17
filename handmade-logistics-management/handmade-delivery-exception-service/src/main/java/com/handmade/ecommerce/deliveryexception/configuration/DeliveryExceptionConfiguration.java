package com.handmade.ecommerce.deliveryexception.configuration;

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
import com.handmade.ecommerce.logistics.model.DeliveryException;
import com.handmade.ecommerce.deliveryexception.service.cmds.*;
import com.handmade.ecommerce.deliveryexception.service.healthcheck.DeliveryExceptionHealthChecker;
import com.handmade.ecommerce.deliveryexception.service.store.DeliveryExceptionEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class DeliveryExceptionConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/deliveryexception/deliveryexception-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "DeliveryException";
    public static final String PREFIX_FOR_RESOLVER = "deliveryexception";

    @Bean BeanFactoryAdapter deliveryexceptionBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl deliveryexceptionFlowStore(
            @Qualifier("deliveryexceptionBeanFactoryAdapter") BeanFactoryAdapter deliveryexceptionBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(deliveryexceptionBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<DeliveryException> deliveryexceptionEntityStm(@Qualifier("deliveryexceptionFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<DeliveryException> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider deliveryexceptionActionsInfoProvider(@Qualifier("deliveryexceptionFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("deliveryexception",provider);
        return provider;
	}
	
	@Bean EntityStore<DeliveryException> deliveryexceptionEntityStore() {
		return new DeliveryExceptionEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<DeliveryException> _deliveryexceptionStateEntityService_(
			@Qualifier("deliveryexceptionEntityStm") STM<DeliveryException> stm,
			@Qualifier("deliveryexceptionActionsInfoProvider") STMActionsInfoProvider deliveryexceptionInfoProvider,
			@Qualifier("deliveryexceptionEntityStore") EntityStore<DeliveryException> entityStore){
		return new StateEntityServiceImpl<>(stm, deliveryexceptionInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<DeliveryException> deliveryexceptionEntryAction(@Qualifier("deliveryexceptionEntityStore") EntityStore<DeliveryException> entityStore,
			@Qualifier("deliveryexceptionActionsInfoProvider") STMActionsInfoProvider deliveryexceptionInfoProvider,
            @Qualifier("deliveryexceptionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<DeliveryException> entryAction =  new GenericEntryAction<DeliveryException>(entityStore,deliveryexceptionInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<DeliveryException> deliveryexceptionExitAction(@Qualifier("deliveryexceptionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<DeliveryException> exitAction = new GenericExitAction<DeliveryException>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader deliveryexceptionFlowReader(@Qualifier("deliveryexceptionFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean DeliveryExceptionHealthChecker deliveryexceptionHealthChecker(){
    	return new DeliveryExceptionHealthChecker();
    }

    @Bean STMTransitionAction<DeliveryException> defaultdeliveryexceptionSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver deliveryexceptionTransitionActionResolver(
    @Qualifier("defaultdeliveryexceptionSTMTransitionAction") STMTransitionAction<DeliveryException> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector deliveryexceptionBodyTypeSelector(
    @Qualifier("deliveryexceptionActionsInfoProvider") STMActionsInfoProvider deliveryexceptionInfoProvider,
    @Qualifier("deliveryexceptionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(deliveryexceptionInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<DeliveryException> deliveryexceptionBaseTransitionAction(
        @Qualifier("deliveryexceptionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "deliveryexception" + eventId for the method name. (deliveryexception is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/deliveryexception/deliveryexception-states.xml

    @Bean AcknowledgeDeliveryExceptionAction
            deliveryexceptionAcknowledge(){
        return new AcknowledgeDeliveryExceptionAction();
    }
    @Bean InvestigateDeliveryExceptionAction
            deliveryexceptionInvestigate(){
        return new InvestigateDeliveryExceptionAction();
    }
    @Bean ResolveDeliveryExceptionAction
            deliveryexceptionResolve(){
        return new ResolveDeliveryExceptionAction();
    }
    @Bean EscalateDeliveryExceptionAction
            deliveryexceptionEscalate(){
        return new EscalateDeliveryExceptionAction();
    }
    @Bean ResolveDeliveryExceptionAction
            deliveryexceptionResolve(){
        return new ResolveDeliveryExceptionAction();
    }


}
