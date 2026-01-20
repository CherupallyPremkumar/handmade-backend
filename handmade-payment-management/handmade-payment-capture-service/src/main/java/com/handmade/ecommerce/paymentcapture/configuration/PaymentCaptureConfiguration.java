package com.handmade.ecommerce.paymentcapture.configuration;

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
import com.handmade.ecommerce.payment.model.PaymentCapture;
import com.handmade.ecommerce.paymentcapture.service.cmds.*;
import com.handmade.ecommerce.paymentcapture.service.healthcheck.PaymentCaptureHealthChecker;
import com.handmade.ecommerce.paymentcapture.service.store.PaymentCaptureEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PaymentCaptureConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/paymentcapture/paymentcapture-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "PaymentCapture";
    public static final String PREFIX_FOR_RESOLVER = "paymentcapture";

    @Bean BeanFactoryAdapter paymentcaptureBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl paymentcaptureFlowStore(
            @Qualifier("paymentcaptureBeanFactoryAdapter") BeanFactoryAdapter paymentcaptureBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(paymentcaptureBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<PaymentCapture> paymentcaptureEntityStm(@Qualifier("paymentcaptureFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<PaymentCapture> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider paymentcaptureActionsInfoProvider(@Qualifier("paymentcaptureFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("paymentcapture",provider);
        return provider;
	}
	
	@Bean EntityStore<PaymentCapture> paymentcaptureEntityStore() {
		return new PaymentCaptureEntityStore();
	}
	
	@Bean StateEntityServiceImpl<PaymentCapture> _paymentcaptureStateEntityService_(
			@Qualifier("paymentcaptureEntityStm") STM<PaymentCapture> stm,
			@Qualifier("paymentcaptureActionsInfoProvider") STMActionsInfoProvider paymentcaptureInfoProvider,
			@Qualifier("paymentcaptureEntityStore") EntityStore<PaymentCapture> entityStore){
		return new StateEntityServiceImpl<>(stm, paymentcaptureInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<PaymentCapture> paymentcaptureEntryAction(@Qualifier("paymentcaptureEntityStore") EntityStore<PaymentCapture> entityStore,
			@Qualifier("paymentcaptureActionsInfoProvider") STMActionsInfoProvider paymentcaptureInfoProvider,
            @Qualifier("paymentcaptureFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<PaymentCapture> entryAction =  new GenericEntryAction<PaymentCapture>(entityStore,paymentcaptureInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<PaymentCapture> paymentcaptureExitAction(@Qualifier("paymentcaptureFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<PaymentCapture> exitAction = new GenericExitAction<PaymentCapture>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader paymentcaptureFlowReader(@Qualifier("paymentcaptureFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PaymentCaptureHealthChecker paymentcaptureHealthChecker(){
    	return new PaymentCaptureHealthChecker();
    }

    @Bean STMTransitionAction<PaymentCapture> defaultpaymentcaptureSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver paymentcaptureTransitionActionResolver(
    @Qualifier("defaultpaymentcaptureSTMTransitionAction") STMTransitionAction<PaymentCapture> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector paymentcaptureBodyTypeSelector(
    @Qualifier("paymentcaptureActionsInfoProvider") STMActionsInfoProvider paymentcaptureInfoProvider,
    @Qualifier("paymentcaptureTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(paymentcaptureInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<PaymentCapture> paymentcaptureBaseTransitionAction(
        @Qualifier("paymentcaptureTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "paymentcapture" + eventId for the method name. (paymentcapture is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/paymentcapture/paymentcapture-states.xml

    @Bean RetryPaymentCaptureAction
            paymentcaptureRetry(){
        return new RetryPaymentCaptureAction();
    }
    @Bean FailPaymentCaptureAction
            paymentcaptureFail(){
        return new FailPaymentCaptureAction();
    }
    @Bean SucceedPaymentCaptureAction
            paymentcaptureSucceed(){
        return new SucceedPaymentCaptureAction();
    }
    @Bean CancelPaymentCaptureAction
            paymentcaptureCancel(){
        return new CancelPaymentCaptureAction();
    }
    @Bean ProcessPaymentCaptureAction
            paymentcaptureProcess(){
        return new ProcessPaymentCaptureAction();
    }


}
