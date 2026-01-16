package com.handmade.ecommerce.finance.configuration;

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
import com.handmade.ecommerce.finance.model.FinanceProfile;
import com.handmade.ecommerce.finance.service.healthcheck.FinanceHealthChecker;
import com.handmade.ecommerce.finance.service.store.FinanceEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class FinanceConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/finance/finance-profile-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Finance";
    public static final String PREFIX_FOR_RESOLVER = "finance";

    @Bean BeanFactoryAdapter financeBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl financeFlowStore(
            @Qualifier("financeBeanFactoryAdapter") BeanFactoryAdapter financeBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(financeBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<FinanceProfile> financeEntityStm(@Qualifier("financeFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<FinanceProfile> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider financeActionsInfoProvider(@Qualifier("financeFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("finance",provider);
        return provider;
	}
	
	@Bean EntityStore<FinanceProfile> financeEntityStore() {
		return new FinanceEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<FinanceProfile> _financeStateEntityService_(
			@Qualifier("financeEntityStm") STM<FinanceProfile> stm,
			@Qualifier("financeActionsInfoProvider") STMActionsInfoProvider financeInfoProvider,
			@Qualifier("financeEntityStore") EntityStore<FinanceProfile> entityStore){
		return new StateEntityServiceImpl<>(stm, financeInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<FinanceProfile> financeEntryAction(@Qualifier("financeEntityStore") EntityStore<FinanceProfile> entityStore,
			@Qualifier("financeActionsInfoProvider") STMActionsInfoProvider financeInfoProvider,
            @Qualifier("financeFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<FinanceProfile> entryAction =  new GenericEntryAction<FinanceProfile>(entityStore,financeInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<FinanceProfile> financeExitAction(@Qualifier("financeFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<FinanceProfile> exitAction = new GenericExitAction<FinanceProfile>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader financeFlowReader(@Qualifier("financeFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean FinanceHealthChecker financeHealthChecker(){
    	return new FinanceHealthChecker();
    }

    @Bean STMTransitionAction<FinanceProfile> defaultfinanceSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver financeTransitionActionResolver(
    @Qualifier("defaultfinanceSTMTransitionAction") STMTransitionAction<FinanceProfile> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector financeBodyTypeSelector(
    @Qualifier("financeActionsInfoProvider") STMActionsInfoProvider financeInfoProvider,
    @Qualifier("financeTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(financeInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<FinanceProfile> financeBaseTransitionAction(
        @Qualifier("financeTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }
}
