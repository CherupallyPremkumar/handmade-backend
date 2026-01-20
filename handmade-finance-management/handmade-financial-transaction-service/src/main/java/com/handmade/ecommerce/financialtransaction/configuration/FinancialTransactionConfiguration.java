package com.handmade.ecommerce.financialtransaction.configuration;

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
import com.handmade.ecommerce.finance.model.FinancialTransaction;
import com.handmade.ecommerce.financialtransaction.service.cmds.*;
import com.handmade.ecommerce.financialtransaction.service.healthcheck.FinancialTransactionHealthChecker;
import com.handmade.ecommerce.financialtransaction.service.store.FinancialTransactionEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class FinancialTransactionConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/financialtransaction/financialtransaction-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "FinancialTransaction";
    public static final String PREFIX_FOR_RESOLVER = "financialtransaction";

    @Bean BeanFactoryAdapter financialtransactionBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl financialtransactionFlowStore(
            @Qualifier("financialtransactionBeanFactoryAdapter") BeanFactoryAdapter financialtransactionBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(financialtransactionBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<FinancialTransaction> financialtransactionEntityStm(@Qualifier("financialtransactionFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<FinancialTransaction> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider financialtransactionActionsInfoProvider(@Qualifier("financialtransactionFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("financialtransaction",provider);
        return provider;
	}
	
	@Bean EntityStore<FinancialTransaction> financialtransactionEntityStore() {
		return new FinancialTransactionEntityStore();
	}
	
	@Bean StateEntityServiceImpl<FinancialTransaction> _financialtransactionStateEntityService_(
			@Qualifier("financialtransactionEntityStm") STM<FinancialTransaction> stm,
			@Qualifier("financialtransactionActionsInfoProvider") STMActionsInfoProvider financialtransactionInfoProvider,
			@Qualifier("financialtransactionEntityStore") EntityStore<FinancialTransaction> entityStore){
		return new StateEntityServiceImpl<>(stm, financialtransactionInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<FinancialTransaction> financialtransactionEntryAction(@Qualifier("financialtransactionEntityStore") EntityStore<FinancialTransaction> entityStore,
			@Qualifier("financialtransactionActionsInfoProvider") STMActionsInfoProvider financialtransactionInfoProvider,
            @Qualifier("financialtransactionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<FinancialTransaction> entryAction =  new GenericEntryAction<FinancialTransaction>(entityStore,financialtransactionInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<FinancialTransaction> financialtransactionExitAction(@Qualifier("financialtransactionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<FinancialTransaction> exitAction = new GenericExitAction<FinancialTransaction>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader financialtransactionFlowReader(@Qualifier("financialtransactionFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean FinancialTransactionHealthChecker financialtransactionHealthChecker(){
    	return new FinancialTransactionHealthChecker();
    }

    @Bean STMTransitionAction<FinancialTransaction> defaultfinancialtransactionSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver financialtransactionTransitionActionResolver(
    @Qualifier("defaultfinancialtransactionSTMTransitionAction") STMTransitionAction<FinancialTransaction> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector financialtransactionBodyTypeSelector(
    @Qualifier("financialtransactionActionsInfoProvider") STMActionsInfoProvider financialtransactionInfoProvider,
    @Qualifier("financialtransactionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(financialtransactionInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<FinancialTransaction> financialtransactionBaseTransitionAction(
        @Qualifier("financialtransactionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "financialtransaction" + eventId for the method name. (financialtransaction is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/financialtransaction/financialtransaction-states.xml

    @Bean FailFinancialTransactionAction
            failFinancialTransactionAction(){
        return new FailFinancialTransactionAction();
    }
    @Bean CompleteFinancialTransactionAction
            completeFinancialTransactionAction(){
        return new CompleteFinancialTransactionAction();
    }
    @Bean ProcessFinancialTransactionAction
	processFinancialTransactionAction(){
        return new ProcessFinancialTransactionAction();
    }


}
