package com.handmade.ecommerce.fraudcase.configuration;

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
import com.handmade.ecommerce.risk.model.FraudCase;
import com.handmade.ecommerce.fraudcase.service.cmds.*;
import com.handmade.ecommerce.fraudcase.service.healthcheck.FraudCaseHealthChecker;
import com.handmade.ecommerce.fraudcase.service.store.FraudCaseEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class FraudCaseConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/fraudcase/fraudcase-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "FraudCase";
    public static final String PREFIX_FOR_RESOLVER = "fraudcase";

    @Bean BeanFactoryAdapter fraudcaseBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl fraudcaseFlowStore(
            @Qualifier("fraudcaseBeanFactoryAdapter") BeanFactoryAdapter fraudcaseBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(fraudcaseBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<FraudCase> fraudcaseEntityStm(@Qualifier("fraudcaseFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<FraudCase> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider fraudcaseActionsInfoProvider(@Qualifier("fraudcaseFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("fraudcase",provider);
        return provider;
	}
	
	@Bean EntityStore<FraudCase> fraudcaseEntityStore() {
		return new FraudCaseEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<FraudCase> _fraudcaseStateEntityService_(
			@Qualifier("fraudcaseEntityStm") STM<FraudCase> stm,
			@Qualifier("fraudcaseActionsInfoProvider") STMActionsInfoProvider fraudcaseInfoProvider,
			@Qualifier("fraudcaseEntityStore") EntityStore<FraudCase> entityStore){
		return new StateEntityServiceImpl<>(stm, fraudcaseInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<FraudCase> fraudcaseEntryAction(@Qualifier("fraudcaseEntityStore") EntityStore<FraudCase> entityStore,
			@Qualifier("fraudcaseActionsInfoProvider") STMActionsInfoProvider fraudcaseInfoProvider,
            @Qualifier("fraudcaseFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<FraudCase> entryAction =  new GenericEntryAction<FraudCase>(entityStore,fraudcaseInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<FraudCase> fraudcaseExitAction(@Qualifier("fraudcaseFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<FraudCase> exitAction = new GenericExitAction<FraudCase>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader fraudcaseFlowReader(@Qualifier("fraudcaseFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean FraudCaseHealthChecker fraudcaseHealthChecker(){
    	return new FraudCaseHealthChecker();
    }

    @Bean STMTransitionAction<FraudCase> defaultfraudcaseSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver fraudcaseTransitionActionResolver(
    @Qualifier("defaultfraudcaseSTMTransitionAction") STMTransitionAction<FraudCase> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector fraudcaseBodyTypeSelector(
    @Qualifier("fraudcaseActionsInfoProvider") STMActionsInfoProvider fraudcaseInfoProvider,
    @Qualifier("fraudcaseTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(fraudcaseInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<FraudCase> fraudcaseBaseTransitionAction(
        @Qualifier("fraudcaseTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "fraudcase" + eventId for the method name. (fraudcase is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/fraudcase/fraudcase-states.xml

    @Bean AssignFraudCaseAction
            fraudcaseAssign(){
        return new AssignFraudCaseAction();
    }
    @Bean ResolveFraudCaseAction
            fraudcaseResolve(){
        return new ResolveFraudCaseAction();
    }
    @Bean EscalateFraudCaseAction
            fraudcaseEscalate(){
        return new EscalateFraudCaseAction();
    }
    @Bean CloseFraudCaseAction
            fraudcaseClose(){
        return new CloseFraudCaseAction();
    }
    @Bean InvestigateFraudCaseAction
            fraudcaseInvestigate(){
        return new InvestigateFraudCaseAction();
    }
    @Bean ResolveFraudCaseAction
            fraudcaseResolve(){
        return new ResolveFraudCaseAction();
    }


}
