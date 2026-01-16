package com.handmade.ecommerce.risk.configuration;

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
import com.handmade.ecommerce.risk.model.Risk;
import com.handmade.ecommerce.risk.service.cmds.*;
import com.handmade.ecommerce.risk.service.healthcheck.RiskHealthChecker;
import com.handmade.ecommerce.risk.service.store.RiskEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class RiskConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/risk/risk-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Risk";
    public static final String PREFIX_FOR_RESOLVER = "risk";

    @Bean BeanFactoryAdapter riskBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl riskFlowStore(
            @Qualifier("riskBeanFactoryAdapter") BeanFactoryAdapter riskBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(riskBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Risk> riskEntityStm(@Qualifier("riskFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Risk> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider riskActionsInfoProvider(@Qualifier("riskFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("risk",provider);
        return provider;
	}
	
	@Bean EntityStore<Risk> riskEntityStore() {
		return new RiskEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Risk> _riskStateEntityService_(
			@Qualifier("riskEntityStm") STM<Risk> stm,
			@Qualifier("riskActionsInfoProvider") STMActionsInfoProvider riskInfoProvider,
			@Qualifier("riskEntityStore") EntityStore<Risk> entityStore){
		return new StateEntityServiceImpl<>(stm, riskInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Risk> riskEntryAction(@Qualifier("riskEntityStore") EntityStore<Risk> entityStore,
			@Qualifier("riskActionsInfoProvider") STMActionsInfoProvider riskInfoProvider,
            @Qualifier("riskFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Risk> entryAction =  new GenericEntryAction<Risk>(entityStore,riskInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Risk> riskExitAction(@Qualifier("riskFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Risk> exitAction = new GenericExitAction<Risk>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader riskFlowReader(@Qualifier("riskFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean RiskHealthChecker riskHealthChecker(){
    	return new RiskHealthChecker();
    }

    @Bean STMTransitionAction<Risk> defaultriskSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver riskTransitionActionResolver(
    @Qualifier("defaultriskSTMTransitionAction") STMTransitionAction<Risk> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector riskBodyTypeSelector(
    @Qualifier("riskActionsInfoProvider") STMActionsInfoProvider riskInfoProvider,
    @Qualifier("riskTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(riskInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Risk> riskBaseTransitionAction(
        @Qualifier("riskTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "risk" + eventId for the method name. (risk is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/risk/risk-states.xml

    @Bean AnalyzeSignalRiskAction
            riskAnalyzeSignal(){
        return new AnalyzeSignalRiskAction();
    }
    @Bean SuppressSignalRiskAction
            riskSuppressSignal(){
        return new SuppressSignalRiskAction();
    }
    @Bean EscalateSignalRiskAction
            riskEscalateSignal(){
        return new EscalateSignalRiskAction();
    }
    @Bean ResolveSignalRiskAction
            riskResolveSignal(){
        return new ResolveSignalRiskAction();
    }
    @Bean SuppressSignalRiskAction
            riskSuppressSignal(){
        return new SuppressSignalRiskAction();
    }
    @Bean EscalateSignalRiskAction
            riskEscalateSignal(){
        return new EscalateSignalRiskAction();
    }
    @Bean MarkFalsePositiveRiskAction
            riskMarkFalsePositive(){
        return new MarkFalsePositiveRiskAction();
    }
    @Bean ConfirmRiskRiskAction
            riskConfirmRisk(){
        return new ConfirmRiskRiskAction();
    }
    @Bean AnalyzeSignalRiskAction
            riskAnalyzeSignal(){
        return new AnalyzeSignalRiskAction();
    }
    @Bean ResolveSignalRiskAction
            riskResolveSignal(){
        return new ResolveSignalRiskAction();
    }


}
