package com.handmade.ecommerce.paymentorder.configuration;

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
import com.handmade.ecommerce.payment.model.PaymentOrder;
import com.handmade.ecommerce.paymentorder.service.cmds.*;
import com.handmade.ecommerce.paymentorder.service.healthcheck.PaymentOrderHealthChecker;
import com.handmade.ecommerce.paymentorder.service.store.PaymentOrderEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PaymentOrderConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/paymentorder/paymentorder-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "PaymentOrder";
    public static final String PREFIX_FOR_RESOLVER = "paymentorder";

    @Bean BeanFactoryAdapter paymentorderBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl paymentorderFlowStore(
            @Qualifier("paymentorderBeanFactoryAdapter") BeanFactoryAdapter paymentorderBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(paymentorderBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<PaymentOrder> paymentorderEntityStm(@Qualifier("paymentorderFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<PaymentOrder> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider paymentorderActionsInfoProvider(@Qualifier("paymentorderFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("paymentorder",provider);
        return provider;
	}
	
	@Bean EntityStore<PaymentOrder> paymentorderEntityStore() {
		return new PaymentOrderEntityStore();
	}
	
	@Bean StateEntityServiceImpl<PaymentOrder> _paymentorderStateEntityService_(
			@Qualifier("paymentorderEntityStm") STM<PaymentOrder> stm,
			@Qualifier("paymentorderActionsInfoProvider") STMActionsInfoProvider paymentorderInfoProvider,
			@Qualifier("paymentorderEntityStore") EntityStore<PaymentOrder> entityStore){
		return new StateEntityServiceImpl<>(stm, paymentorderInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<PaymentOrder> paymentorderEntryAction(@Qualifier("paymentorderEntityStore") EntityStore<PaymentOrder> entityStore,
			@Qualifier("paymentorderActionsInfoProvider") STMActionsInfoProvider paymentorderInfoProvider,
            @Qualifier("paymentorderFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<PaymentOrder> entryAction =  new GenericEntryAction<PaymentOrder>(entityStore,paymentorderInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<PaymentOrder> paymentorderExitAction(@Qualifier("paymentorderFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<PaymentOrder> exitAction = new GenericExitAction<PaymentOrder>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader paymentorderFlowReader(@Qualifier("paymentorderFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PaymentOrderHealthChecker paymentorderHealthChecker(){
    	return new PaymentOrderHealthChecker();
    }

    @Bean STMTransitionAction<PaymentOrder> defaultpaymentorderSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver paymentorderTransitionActionResolver(
    @Qualifier("defaultpaymentorderSTMTransitionAction") STMTransitionAction<PaymentOrder> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector paymentorderBodyTypeSelector(
    @Qualifier("paymentorderActionsInfoProvider") STMActionsInfoProvider paymentorderInfoProvider,
    @Qualifier("paymentorderTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(paymentorderInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<PaymentOrder> paymentorderBaseTransitionAction(
        @Qualifier("paymentorderTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "paymentorder" + eventId for the method name. (paymentorder is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/paymentorder/paymentorder-states.xml

    @Bean SettlePaymentOrderAction
            settlePaymentOrderAction(){
        return new SettlePaymentOrderAction();
    }
    @Bean RefundPaymentOrderAction
            refundPaymentOrderAction(){
        return new RefundPaymentOrderAction();
    }
    @Bean InitiatePaymentOrderAction
            initiatePaymentOrderAction(){
        return new InitiatePaymentOrderAction();
    }
    @Bean CancelPaymentOrderAction
            cancelPaymentOrderAction(){
        return new CancelPaymentOrderAction();
    }

    @Bean FailPaymentOrderAction
            failPaymentOrderAction(){
        return new FailPaymentOrderAction();
    }
    @Bean AuthorizePaymentOrderAction
            authorizePaymentOrderAction(){
        return new AuthorizePaymentOrderAction();
    }
    @Bean VoidPaymentOrderAction
            voidPaymentOrderAction(){
        return new VoidPaymentOrderAction();
    }
    @Bean CapturePaymentOrderAction
            capturePaymentOrderAction(){
        return new CapturePaymentOrderAction();
    }


}
