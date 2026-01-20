package com.handmade.ecommerce.paymentauthorization.configuration;

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
import com.handmade.ecommerce.payment.model.PaymentAuthorization;
import com.handmade.ecommerce.paymentauthorization.service.cmds.*;
import com.handmade.ecommerce.paymentauthorization.service.healthcheck.PaymentAuthorizationHealthChecker;
import com.handmade.ecommerce.paymentauthorization.service.store.PaymentAuthorizationEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PaymentAuthorizationConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/paymentauthorization/paymentauthorization-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "PaymentAuthorization";
    public static final String PREFIX_FOR_RESOLVER = "paymentauthorization";

    @Bean BeanFactoryAdapter paymentauthorizationBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl paymentauthorizationFlowStore(
            @Qualifier("paymentauthorizationBeanFactoryAdapter") BeanFactoryAdapter paymentauthorizationBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(paymentauthorizationBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<PaymentAuthorization> paymentauthorizationEntityStm(@Qualifier("paymentauthorizationFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<PaymentAuthorization> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider paymentauthorizationActionsInfoProvider(@Qualifier("paymentauthorizationFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("paymentauthorization",provider);
        return provider;
	}
	
	@Bean EntityStore<PaymentAuthorization> paymentauthorizationEntityStore() {
		return new PaymentAuthorizationEntityStore();
	}
	
	@Bean StateEntityServiceImpl<PaymentAuthorization> _paymentauthorizationStateEntityService_(
			@Qualifier("paymentauthorizationEntityStm") STM<PaymentAuthorization> stm,
			@Qualifier("paymentauthorizationActionsInfoProvider") STMActionsInfoProvider paymentauthorizationInfoProvider,
			@Qualifier("paymentauthorizationEntityStore") EntityStore<PaymentAuthorization> entityStore){
		return new StateEntityServiceImpl<>(stm, paymentauthorizationInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<PaymentAuthorization> paymentauthorizationEntryAction(@Qualifier("paymentauthorizationEntityStore") EntityStore<PaymentAuthorization> entityStore,
			@Qualifier("paymentauthorizationActionsInfoProvider") STMActionsInfoProvider paymentauthorizationInfoProvider,
            @Qualifier("paymentauthorizationFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<PaymentAuthorization> entryAction =  new GenericEntryAction<PaymentAuthorization>(entityStore,paymentauthorizationInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<PaymentAuthorization> paymentauthorizationExitAction(@Qualifier("paymentauthorizationFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<PaymentAuthorization> exitAction = new GenericExitAction<PaymentAuthorization>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader paymentauthorizationFlowReader(@Qualifier("paymentauthorizationFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PaymentAuthorizationHealthChecker paymentauthorizationHealthChecker(){
    	return new PaymentAuthorizationHealthChecker();
    }

    @Bean STMTransitionAction<PaymentAuthorization> defaultpaymentauthorizationSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver paymentauthorizationTransitionActionResolver(
    @Qualifier("defaultpaymentauthorizationSTMTransitionAction") STMTransitionAction<PaymentAuthorization> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector paymentauthorizationBodyTypeSelector(
    @Qualifier("paymentauthorizationActionsInfoProvider") STMActionsInfoProvider paymentauthorizationInfoProvider,
    @Qualifier("paymentauthorizationTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(paymentauthorizationInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<PaymentAuthorization> paymentauthorizationBaseTransitionAction(
        @Qualifier("paymentauthorizationTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "paymentauthorization" + eventId for the method name. (paymentauthorization is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/paymentauthorization/paymentauthorization-states.xml

    @Bean VoidPaymentAuthorizationAction
            paymentauthorizationVoid(){
        return new VoidPaymentAuthorizationAction();
    }
    @Bean ExpirePaymentAuthorizationAction
            paymentauthorizationExpire(){
        return new ExpirePaymentAuthorizationAction();
    }
    @Bean CapturePaymentAuthorizationAction
            paymentauthorizationCapture(){
        return new CapturePaymentAuthorizationAction();
    }


}
