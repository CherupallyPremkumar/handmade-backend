package com.handmade.ecommerce.risksignal.configuration;

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
import com.handmade.ecommerce.risk.model.RiskSignal;
import com.handmade.ecommerce.risksignal.service.cmds.*;
import com.handmade.ecommerce.risksignal.service.healthcheck.RiskSignalHealthChecker;
import com.handmade.ecommerce.risksignal.service.store.RiskSignalEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class RiskSignalConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/risksignal/risksignal-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "RiskSignal";
    public static final String PREFIX_FOR_RESOLVER = "risksignal";

    @Bean BeanFactoryAdapter risksignalBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl risksignalFlowStore(
            @Qualifier("risksignalBeanFactoryAdapter") BeanFactoryAdapter risksignalBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(risksignalBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<RiskSignal> risksignalEntityStm(@Qualifier("risksignalFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<RiskSignal> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider risksignalActionsInfoProvider(@Qualifier("risksignalFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("risksignal",provider);
        return provider;
	}
	
	@Bean EntityStore<RiskSignal> risksignalEntityStore() {
		return new RiskSignalEntityStore();
	}
	
	@Bean StateEntityServiceImpl<RiskSignal> _risksignalStateEntityService_(
			@Qualifier("risksignalEntityStm") STM<RiskSignal> stm,
			@Qualifier("risksignalActionsInfoProvider") STMActionsInfoProvider risksignalInfoProvider,
			@Qualifier("risksignalEntityStore") EntityStore<RiskSignal> entityStore){
		return new StateEntityServiceImpl<>(stm, risksignalInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<RiskSignal> risksignalEntryAction(@Qualifier("risksignalEntityStore") EntityStore<RiskSignal> entityStore,
			@Qualifier("risksignalActionsInfoProvider") STMActionsInfoProvider risksignalInfoProvider,
            @Qualifier("risksignalFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<RiskSignal> entryAction =  new GenericEntryAction<RiskSignal>(entityStore,risksignalInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<RiskSignal> risksignalExitAction(@Qualifier("risksignalFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<RiskSignal> exitAction = new GenericExitAction<RiskSignal>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader risksignalFlowReader(@Qualifier("risksignalFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean RiskSignalHealthChecker risksignalHealthChecker(){
    	return new RiskSignalHealthChecker();
    }

    @Bean STMTransitionAction<RiskSignal> defaultrisksignalSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver risksignalTransitionActionResolver(
    @Qualifier("defaultrisksignalSTMTransitionAction") STMTransitionAction<RiskSignal> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector risksignalBodyTypeSelector(
    @Qualifier("risksignalActionsInfoProvider") STMActionsInfoProvider risksignalInfoProvider,
    @Qualifier("risksignalTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(risksignalInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<RiskSignal> risksignalBaseTransitionAction(
        @Qualifier("risksignalTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "risksignal" + eventId for the method name. (risksignal is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/risksignal/risksignal-states.xml

    @Bean ReviewRiskSignalAction
            reviewRiskSignalAction(){
        return new ReviewRiskSignalAction();
    }
    @Bean DismissRiskSignalAction
            dismissRiskSignalAction(){
        return new DismissRiskSignalAction();
    }
    @Bean EscalateRiskSignalAction
            escalateRiskSignalAction(){
        return new EscalateRiskSignalAction();
    }
    @Bean ConfirmRiskSignalAction
            confirmRiskSignalAction(){
        return new ConfirmRiskSignalAction();
    }

    @Bean MitigateRiskSignalAction
            mitigateRiskSignalAction(){
        return new MitigateRiskSignalAction();
    }


}
