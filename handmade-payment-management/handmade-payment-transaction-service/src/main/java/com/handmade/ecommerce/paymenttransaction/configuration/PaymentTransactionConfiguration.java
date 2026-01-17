package com.handmade.ecommerce.paymenttransaction.configuration;

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
import com.handmade.ecommerce.payment.model.PaymentTransaction;
import com.handmade.ecommerce.paymenttransaction.service.cmds.*;
import com.handmade.ecommerce.paymenttransaction.service.healthcheck.PaymentTransactionHealthChecker;
import com.handmade.ecommerce.paymenttransaction.service.store.PaymentTransactionEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PaymentTransactionConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/paymenttransaction/paymenttransaction-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "PaymentTransaction";
    public static final String PREFIX_FOR_RESOLVER = "paymenttransaction";

    @Bean BeanFactoryAdapter paymenttransactionBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl paymenttransactionFlowStore(
            @Qualifier("paymenttransactionBeanFactoryAdapter") BeanFactoryAdapter paymenttransactionBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(paymenttransactionBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<PaymentTransaction> paymenttransactionEntityStm(@Qualifier("paymenttransactionFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<PaymentTransaction> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider paymenttransactionActionsInfoProvider(@Qualifier("paymenttransactionFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("paymenttransaction",provider);
        return provider;
	}
	
	@Bean EntityStore<PaymentTransaction> paymenttransactionEntityStore() {
		return new PaymentTransactionEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<PaymentTransaction> _paymenttransactionStateEntityService_(
			@Qualifier("paymenttransactionEntityStm") STM<PaymentTransaction> stm,
			@Qualifier("paymenttransactionActionsInfoProvider") STMActionsInfoProvider paymenttransactionInfoProvider,
			@Qualifier("paymenttransactionEntityStore") EntityStore<PaymentTransaction> entityStore){
		return new StateEntityServiceImpl<>(stm, paymenttransactionInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<PaymentTransaction> paymenttransactionEntryAction(@Qualifier("paymenttransactionEntityStore") EntityStore<PaymentTransaction> entityStore,
			@Qualifier("paymenttransactionActionsInfoProvider") STMActionsInfoProvider paymenttransactionInfoProvider,
            @Qualifier("paymenttransactionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<PaymentTransaction> entryAction =  new GenericEntryAction<PaymentTransaction>(entityStore,paymenttransactionInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<PaymentTransaction> paymenttransactionExitAction(@Qualifier("paymenttransactionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<PaymentTransaction> exitAction = new GenericExitAction<PaymentTransaction>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader paymenttransactionFlowReader(@Qualifier("paymenttransactionFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PaymentTransactionHealthChecker paymenttransactionHealthChecker(){
    	return new PaymentTransactionHealthChecker();
    }

    @Bean STMTransitionAction<PaymentTransaction> defaultpaymenttransactionSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver paymenttransactionTransitionActionResolver(
    @Qualifier("defaultpaymenttransactionSTMTransitionAction") STMTransitionAction<PaymentTransaction> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector paymenttransactionBodyTypeSelector(
    @Qualifier("paymenttransactionActionsInfoProvider") STMActionsInfoProvider paymenttransactionInfoProvider,
    @Qualifier("paymenttransactionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(paymenttransactionInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<PaymentTransaction> paymenttransactionBaseTransitionAction(
        @Qualifier("paymenttransactionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "paymenttransaction" + eventId for the method name. (paymenttransaction is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/paymenttransaction/paymenttransaction-states.xml

    @Bean RetryPaymentTransactionAction
            paymenttransactionRetry(){
        return new RetryPaymentTransactionAction();
    }
    @Bean FailPaymentTransactionAction
            paymenttransactionFail(){
        return new FailPaymentTransactionAction();
    }
    @Bean SucceedPaymentTransactionAction
            paymenttransactionSucceed(){
        return new SucceedPaymentTransactionAction();
    }
    @Bean CancelPaymentTransactionAction
            paymenttransactionCancel(){
        return new CancelPaymentTransactionAction();
    }
    @Bean ProcessPaymentTransactionAction
            paymenttransactionProcess(){
        return new ProcessPaymentTransactionAction();
    }


}
