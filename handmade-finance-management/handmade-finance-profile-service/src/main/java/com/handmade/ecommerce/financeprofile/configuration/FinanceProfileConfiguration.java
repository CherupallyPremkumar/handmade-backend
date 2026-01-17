package com.handmade.ecommerce.financeprofile.configuration;

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
import com.handmade.ecommerce.financeprofile.service.cmds.*;
import com.handmade.ecommerce.financeprofile.service.healthcheck.FinanceProfileHealthChecker;
import com.handmade.ecommerce.financeprofile.service.store.FinanceProfileEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class FinanceProfileConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/financeprofile/financeprofile-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "FinanceProfile";
    public static final String PREFIX_FOR_RESOLVER = "financeprofile";

    @Bean BeanFactoryAdapter financeprofileBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl financeprofileFlowStore(
            @Qualifier("financeprofileBeanFactoryAdapter") BeanFactoryAdapter financeprofileBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(financeprofileBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<FinanceProfile> financeprofileEntityStm(@Qualifier("financeprofileFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<FinanceProfile> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider financeprofileActionsInfoProvider(@Qualifier("financeprofileFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("financeprofile",provider);
        return provider;
	}
	
	@Bean EntityStore<FinanceProfile> financeprofileEntityStore() {
		return new FinanceProfileEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<FinanceProfile> _financeprofileStateEntityService_(
			@Qualifier("financeprofileEntityStm") STM<FinanceProfile> stm,
			@Qualifier("financeprofileActionsInfoProvider") STMActionsInfoProvider financeprofileInfoProvider,
			@Qualifier("financeprofileEntityStore") EntityStore<FinanceProfile> entityStore){
		return new StateEntityServiceImpl<>(stm, financeprofileInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<FinanceProfile> financeprofileEntryAction(@Qualifier("financeprofileEntityStore") EntityStore<FinanceProfile> entityStore,
			@Qualifier("financeprofileActionsInfoProvider") STMActionsInfoProvider financeprofileInfoProvider,
            @Qualifier("financeprofileFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<FinanceProfile> entryAction =  new GenericEntryAction<FinanceProfile>(entityStore,financeprofileInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<FinanceProfile> financeprofileExitAction(@Qualifier("financeprofileFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<FinanceProfile> exitAction = new GenericExitAction<FinanceProfile>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader financeprofileFlowReader(@Qualifier("financeprofileFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean FinanceProfileHealthChecker financeprofileHealthChecker(){
    	return new FinanceProfileHealthChecker();
    }

    @Bean STMTransitionAction<FinanceProfile> defaultfinanceprofileSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver financeprofileTransitionActionResolver(
    @Qualifier("defaultfinanceprofileSTMTransitionAction") STMTransitionAction<FinanceProfile> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector financeprofileBodyTypeSelector(
    @Qualifier("financeprofileActionsInfoProvider") STMActionsInfoProvider financeprofileInfoProvider,
    @Qualifier("financeprofileTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(financeprofileInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<FinanceProfile> financeprofileBaseTransitionAction(
        @Qualifier("financeprofileTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "financeprofile" + eventId for the method name. (financeprofile is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/financeprofile/financeprofile-states.xml

    @Bean SuspendFinanceProfileAction
            suspendFinanceProfileAction(){
        return new SuspendFinanceProfileAction();
    }
    @Bean ReactivateFinanceProfileAction
    reactivateFinanceProfileAction(){
        return new ReactivateFinanceProfileAction();
    }
    @Bean RejectFinanceProfileAction
    rejectFinanceProfileAction(){
        return new RejectFinanceProfileAction();
    }
    @Bean VerifyFinanceProfileAction
    verifyFinanceProfileAction(){
        return new VerifyFinanceProfileAction();
    }
    @Bean ActivateFinanceProfileAction
    activateFinanceProfileAction(){
        return new ActivateFinanceProfileAction();
    }


}
