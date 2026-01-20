package com.handmade.ecommerce.subscription.configuration;

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
import com.handmade.ecommerce.customer.model.Subscription;
import com.handmade.ecommerce.subscription.service.cmds.*;
import com.handmade.ecommerce.subscription.service.healthcheck.SubscriptionHealthChecker;
import com.handmade.ecommerce.subscription.service.store.SubscriptionEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class SubscriptionConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/subscription/subscription-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Subscription";
    public static final String PREFIX_FOR_RESOLVER = "subscription";

    @Bean BeanFactoryAdapter subscriptionBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl subscriptionFlowStore(
            @Qualifier("subscriptionBeanFactoryAdapter") BeanFactoryAdapter subscriptionBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(subscriptionBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<Subscription> subscriptionEntityStm(@Qualifier("subscriptionFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Subscription> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider subscriptionActionsInfoProvider(@Qualifier("subscriptionFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("subscription",provider);
        return provider;
	}
	
	@Bean EntityStore<Subscription> subscriptionEntityStore() {
		return new SubscriptionEntityStore();
	}
	
	@Bean StateEntityServiceImpl<Subscription> _subscriptionStateEntityService_(
			@Qualifier("subscriptionEntityStm") STM<Subscription> stm,
			@Qualifier("subscriptionActionsInfoProvider") STMActionsInfoProvider subscriptionInfoProvider,
			@Qualifier("subscriptionEntityStore") EntityStore<Subscription> entityStore){
		return new StateEntityServiceImpl<>(stm, subscriptionInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<Subscription> subscriptionEntryAction(@Qualifier("subscriptionEntityStore") EntityStore<Subscription> entityStore,
			@Qualifier("subscriptionActionsInfoProvider") STMActionsInfoProvider subscriptionInfoProvider,
            @Qualifier("subscriptionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Subscription> entryAction =  new GenericEntryAction<Subscription>(entityStore,subscriptionInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Subscription> subscriptionExitAction(@Qualifier("subscriptionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Subscription> exitAction = new GenericExitAction<Subscription>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader subscriptionFlowReader(@Qualifier("subscriptionFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean SubscriptionHealthChecker subscriptionHealthChecker(){
    	return new SubscriptionHealthChecker();
    }

    @Bean STMTransitionAction<Subscription> defaultsubscriptionSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver subscriptionTransitionActionResolver(
    @Qualifier("defaultsubscriptionSTMTransitionAction") STMTransitionAction<Subscription> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector subscriptionBodyTypeSelector(
    @Qualifier("subscriptionActionsInfoProvider") STMActionsInfoProvider subscriptionInfoProvider,
    @Qualifier("subscriptionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(subscriptionInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<Subscription> subscriptionBaseTransitionAction(
        @Qualifier("subscriptionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "subscription" + eventId for the method name. (subscription is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/subscription/subscription-states.xml

    @Bean CancelSubscriptionAction
            subscriptionCancel(){
        return new CancelSubscriptionAction();
    }
    @Bean ExpireSubscriptionAction
            subscriptionExpire(){
        return new ExpireSubscriptionAction();
    }
    @Bean ActivatePaidSubscriptionAction
            subscriptionActivatePaid(){
        return new ActivatePaidSubscriptionAction();
    }
    @Bean StartTrialSubscriptionAction
            subscriptionStartTrial(){
        return new StartTrialSubscriptionAction();
    }
    @Bean EnterGraceSubscriptionAction
            subscriptionEnterGrace(){
        return new EnterGraceSubscriptionAction();
    }
    @Bean RenewSubscriptionAction
            subscriptionRenew(){
        return new RenewSubscriptionAction();
    }


}
