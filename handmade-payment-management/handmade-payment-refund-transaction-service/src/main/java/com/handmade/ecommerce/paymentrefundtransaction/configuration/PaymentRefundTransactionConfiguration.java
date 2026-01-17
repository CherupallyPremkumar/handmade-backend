package com.handmade.ecommerce.paymentrefundtransaction.configuration;

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
import com.handmade.ecommerce.payment.model.PaymentRefundTransaction;
import com.handmade.ecommerce.paymentrefundtransaction.service.cmds.*;
import com.handmade.ecommerce.paymentrefundtransaction.service.healthcheck.PaymentRefundTransactionHealthChecker;
import com.handmade.ecommerce.paymentrefundtransaction.service.store.PaymentRefundTransactionEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PaymentRefundTransactionConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/paymentrefundtransaction/paymentrefundtransaction-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "PaymentRefundTransaction";
    public static final String PREFIX_FOR_RESOLVER = "paymentrefundtransaction";

    @Bean BeanFactoryAdapter paymentrefundtransactionBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl paymentrefundtransactionFlowStore(
            @Qualifier("paymentrefundtransactionBeanFactoryAdapter") BeanFactoryAdapter paymentrefundtransactionBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(paymentrefundtransactionBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<PaymentRefundTransaction> paymentrefundtransactionEntityStm(@Qualifier("paymentrefundtransactionFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<PaymentRefundTransaction> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider paymentrefundtransactionActionsInfoProvider(@Qualifier("paymentrefundtransactionFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("paymentrefundtransaction",provider);
        return provider;
	}
	
	@Bean EntityStore<PaymentRefundTransaction> paymentrefundtransactionEntityStore() {
		return new PaymentRefundTransactionEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<PaymentRefundTransaction> _paymentrefundtransactionStateEntityService_(
			@Qualifier("paymentrefundtransactionEntityStm") STM<PaymentRefundTransaction> stm,
			@Qualifier("paymentrefundtransactionActionsInfoProvider") STMActionsInfoProvider paymentrefundtransactionInfoProvider,
			@Qualifier("paymentrefundtransactionEntityStore") EntityStore<PaymentRefundTransaction> entityStore){
		return new StateEntityServiceImpl<>(stm, paymentrefundtransactionInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<PaymentRefundTransaction> paymentrefundtransactionEntryAction(@Qualifier("paymentrefundtransactionEntityStore") EntityStore<PaymentRefundTransaction> entityStore,
			@Qualifier("paymentrefundtransactionActionsInfoProvider") STMActionsInfoProvider paymentrefundtransactionInfoProvider,
            @Qualifier("paymentrefundtransactionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<PaymentRefundTransaction> entryAction =  new GenericEntryAction<PaymentRefundTransaction>(entityStore,paymentrefundtransactionInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<PaymentRefundTransaction> paymentrefundtransactionExitAction(@Qualifier("paymentrefundtransactionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<PaymentRefundTransaction> exitAction = new GenericExitAction<PaymentRefundTransaction>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader paymentrefundtransactionFlowReader(@Qualifier("paymentrefundtransactionFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PaymentRefundTransactionHealthChecker paymentrefundtransactionHealthChecker(){
    	return new PaymentRefundTransactionHealthChecker();
    }

    @Bean STMTransitionAction<PaymentRefundTransaction> defaultpaymentrefundtransactionSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver paymentrefundtransactionTransitionActionResolver(
    @Qualifier("defaultpaymentrefundtransactionSTMTransitionAction") STMTransitionAction<PaymentRefundTransaction> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector paymentrefundtransactionBodyTypeSelector(
    @Qualifier("paymentrefundtransactionActionsInfoProvider") STMActionsInfoProvider paymentrefundtransactionInfoProvider,
    @Qualifier("paymentrefundtransactionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(paymentrefundtransactionInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<PaymentRefundTransaction> paymentrefundtransactionBaseTransitionAction(
        @Qualifier("paymentrefundtransactionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "paymentrefundtransaction" + eventId for the method name. (paymentrefundtransaction is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/paymentrefundtransaction/paymentrefundtransaction-states.xml

    @Bean RetryPaymentRefundTransactionAction
            paymentrefundtransactionRetry(){
        return new RetryPaymentRefundTransactionAction();
    }
    @Bean FailPaymentRefundTransactionAction
            paymentrefundtransactionFail(){
        return new FailPaymentRefundTransactionAction();
    }
    @Bean CompletePaymentRefundTransactionAction
            paymentrefundtransactionComplete(){
        return new CompletePaymentRefundTransactionAction();
    }
    @Bean CancelPaymentRefundTransactionAction
            paymentrefundtransactionCancel(){
        return new CancelPaymentRefundTransactionAction();
    }
    @Bean ProcessPaymentRefundTransactionAction
            paymentrefundtransactionProcess(){
        return new ProcessPaymentRefundTransactionAction();
    }


}
