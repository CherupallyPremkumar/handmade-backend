package com.handmade.ecommerce.payment.configuration;

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
import com.handmade.ecommerce.payment.model.Payment;
import com.handmade.ecommerce.payment.service.cmds.*;
import com.handmade.ecommerce.payment.service.healthcheck.PaymentHealthChecker;
import com.handmade.ecommerce.payment.service.store.PaymentEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.stmcmds.StmAuthoritiesBuilder;
import java.util.function.Function;
import org.chenile.core.context.ChenileExchange;
import org.chenile.stm.State;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.activities.AreActivitiesComplete;
import com.handmade.ecommerce.payment.service.postSaveHooks.*;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PaymentConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/payment/payment-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Payment";
    public static final String PREFIX_FOR_RESOLVER = "payment";

    @Bean BeanFactoryAdapter paymentBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl paymentFlowStore(
            @Qualifier("paymentBeanFactoryAdapter") BeanFactoryAdapter paymentBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(paymentBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Payment> paymentEntityStm(@Qualifier("paymentFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Payment> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider paymentActionsInfoProvider(@Qualifier("paymentFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("payment",provider);
        return provider;
	}
	
	@Bean EntityStore<Payment> paymentEntityStore() {
		return new PaymentEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Payment> _paymentStateEntityService_(
			@Qualifier("paymentEntityStm") STM<Payment> stm,
			@Qualifier("paymentActionsInfoProvider") STMActionsInfoProvider paymentInfoProvider,
			@Qualifier("paymentEntityStore") EntityStore<Payment> entityStore){
		return new StateEntityServiceImpl<>(stm, paymentInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 


    @Bean @Autowired DefaultPostSaveHook<Payment> paymentDefaultPostSaveHook(
    @Qualifier("paymentTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
    DefaultPostSaveHook<Payment> postSaveHook = new DefaultPostSaveHook<>(stmTransitionActionResolver);
    return postSaveHook;
    }

    @Bean @Autowired GenericEntryAction<Payment> paymentEntryAction(@Qualifier("paymentEntityStore") EntityStore<Payment> entityStore,
    @Qualifier("paymentActionsInfoProvider") STMActionsInfoProvider paymentInfoProvider,
    @Qualifier("paymentFlowStore") STMFlowStoreImpl stmFlowStore,
    @Qualifier("paymentDefaultPostSaveHook") DefaultPostSaveHook<Payment> postSaveHook)  {
    GenericEntryAction<Payment> entryAction =  new GenericEntryAction<Payment>(entityStore,paymentInfoProvider,postSaveHook);
    stmFlowStore.setEntryAction(entryAction);
    return entryAction;
    }

    @Bean @Autowired DefaultAutomaticStateComputation<Payment> paymentDefaultAutoState(
    @Qualifier("paymentTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
    @Qualifier("paymentFlowStore") STMFlowStoreImpl stmFlowStore){
    DefaultAutomaticStateComputation<Payment> autoState = new DefaultAutomaticStateComputation<>(stmTransitionActionResolver);
    stmFlowStore.setDefaultAutomaticStateComputation(autoState);
    return autoState;
    }

	@Bean GenericExitAction<Payment> paymentExitAction(@Qualifier("paymentFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Payment> exitAction = new GenericExitAction<Payment>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader paymentFlowReader(@Qualifier("paymentFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PaymentHealthChecker paymentHealthChecker(){
    	return new PaymentHealthChecker();
    }

    @Bean STMTransitionAction<Payment> defaultpaymentSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver paymentTransitionActionResolver(
    @Qualifier("defaultpaymentSTMTransitionAction") STMTransitionAction<Payment> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction,true);
    }

    @Bean @Autowired StmBodyTypeSelector paymentBodyTypeSelector(
    @Qualifier("paymentActionsInfoProvider") STMActionsInfoProvider paymentInfoProvider,
    @Qualifier("paymentTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(paymentInfoProvider,stmTransitionActionResolver);
    }


    @Bean @Autowired STMTransitionAction<Payment> paymentBaseTransitionAction(
        @Qualifier("paymentTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
        @Qualifier("paymentActivityChecker") ActivityChecker activityChecker,
        @Qualifier("paymentFlowStore") STMFlowStoreImpl stmFlowStore){
        BaseTransitionAction<Payment> baseTransitionAction = new BaseTransitionAction<>(stmTransitionActionResolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker paymentActivityChecker(@Qualifier("paymentFlowStore") STMFlowStoreImpl stmFlowStore){
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete activitiesCompletionCheck(@Qualifier("paymentActivityChecker") ActivityChecker activityChecker){
        return new AreActivitiesComplete(activityChecker);
    }

    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "payment" + eventId + "Action" for the method name. (payment is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/payment/payment-states.xml


    @Bean SuspendPaymentAction
            paymentSuspendAction(){
        return new SuspendPaymentAction();
    }

    @Bean ActivatePaymentAction
            paymentActivateAction(){
        return new ActivatePaymentAction();
    }

    @Bean DeactivatePaymentAction
            paymentDeactivateAction(){
        return new DeactivatePaymentAction();
    }


    @Bean ConfigProviderImpl paymentConfigProvider() {
        return new ConfigProviderImpl();
    }

    @Bean ConfigBasedEnablementStrategy paymentConfigBasedEnablementStrategy(
        @Qualifier("paymentConfigProvider") ConfigProvider configProvider,
        @Qualifier("paymentFlowStore") STMFlowStoreImpl stmFlowStore) {
        ConfigBasedEnablementStrategy enablementStrategy = new ConfigBasedEnablementStrategy(configProvider,PREFIX_FOR_PROPERTIES);
        stmFlowStore.setEnablementStrategy(enablementStrategy);
        return enablementStrategy;
    }


    @Bean @Autowired Function<ChenileExchange, String[]> paymentEventAuthoritiesSupplier(
        @Qualifier("paymentActionsInfoProvider") STMActionsInfoProvider paymentInfoProvider)
                    throws Exception{
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(paymentInfoProvider);
        return builder.build();
    }


    @Bean CREATEDPaymentPostSaveHook
        paymentCREATEDPostSaveHook(){
            return new CREATEDPaymentPostSaveHook();
    }

    @Bean ACTIVEPaymentPostSaveHook
        paymentACTIVEPostSaveHook(){
            return new ACTIVEPaymentPostSaveHook();
    }

    @Bean INACTIVEPaymentPostSaveHook
        paymentINACTIVEPostSaveHook(){
            return new INACTIVEPaymentPostSaveHook();
    }

    @Bean SUSPENDEDPaymentPostSaveHook
        paymentSUSPENDEDPostSaveHook(){
            return new SUSPENDEDPaymentPostSaveHook();
    }

}
