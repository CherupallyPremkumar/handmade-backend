package com.handmade.ecommerce.selleronboardingstep.configuration;

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
import com.handmade.ecommerce.onboarding.model.SellerOnboardingStep;
import com.handmade.ecommerce.selleronboardingstep.service.cmds.*;
import com.handmade.ecommerce.selleronboardingstep.service.healthcheck.SellerOnboardingStepHealthChecker;
import com.handmade.ecommerce.selleronboardingstep.service.store.SellerOnboardingStepEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class SellerOnboardingStepConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/selleronboardingstep/selleronboardingstep-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "SellerOnboardingStep";
    public static final String PREFIX_FOR_RESOLVER = "selleronboardingstep";

    @Bean BeanFactoryAdapter selleronboardingstepBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl selleronboardingstepFlowStore(
            @Qualifier("selleronboardingstepBeanFactoryAdapter") BeanFactoryAdapter selleronboardingstepBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(selleronboardingstepBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<SellerOnboardingStep> selleronboardingstepEntityStm(@Qualifier("selleronboardingstepFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<SellerOnboardingStep> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider selleronboardingstepActionsInfoProvider(@Qualifier("selleronboardingstepFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("selleronboardingstep",provider);
        return provider;
	}
	
	@Bean EntityStore<SellerOnboardingStep> selleronboardingstepEntityStore() {
		return new SellerOnboardingStepEntityStore();
	}
	
	@Bean StateEntityServiceImpl<SellerOnboardingStep> _selleronboardingstepStateEntityService_(
			@Qualifier("selleronboardingstepEntityStm") STM<SellerOnboardingStep> stm,
			@Qualifier("selleronboardingstepActionsInfoProvider") STMActionsInfoProvider selleronboardingstepInfoProvider,
			@Qualifier("selleronboardingstepEntityStore") EntityStore<SellerOnboardingStep> entityStore){
		return new StateEntityServiceImpl<>(stm, selleronboardingstepInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<SellerOnboardingStep> selleronboardingstepEntryAction(@Qualifier("selleronboardingstepEntityStore") EntityStore<SellerOnboardingStep> entityStore,
			@Qualifier("selleronboardingstepActionsInfoProvider") STMActionsInfoProvider selleronboardingstepInfoProvider,
            @Qualifier("selleronboardingstepFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<SellerOnboardingStep> entryAction =  new GenericEntryAction<SellerOnboardingStep>(entityStore,selleronboardingstepInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<SellerOnboardingStep> selleronboardingstepExitAction(@Qualifier("selleronboardingstepFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<SellerOnboardingStep> exitAction = new GenericExitAction<SellerOnboardingStep>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader selleronboardingstepFlowReader(@Qualifier("selleronboardingstepFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean SellerOnboardingStepHealthChecker selleronboardingstepHealthChecker(){
    	return new SellerOnboardingStepHealthChecker();
    }

    @Bean STMTransitionAction<SellerOnboardingStep> defaultselleronboardingstepSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver selleronboardingstepTransitionActionResolver(
    @Qualifier("defaultselleronboardingstepSTMTransitionAction") STMTransitionAction<SellerOnboardingStep> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector selleronboardingstepBodyTypeSelector(
    @Qualifier("selleronboardingstepActionsInfoProvider") STMActionsInfoProvider selleronboardingstepInfoProvider,
    @Qualifier("selleronboardingstepTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(selleronboardingstepInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<SellerOnboardingStep> selleronboardingstepBaseTransitionAction(
        @Qualifier("selleronboardingstepTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "selleronboardingstep" + eventId for the method name. (selleronboardingstep is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/selleronboardingstep/selleronboardingstep-states.xml


    @Bean CompleteSellerOnboardingStepAction
            completeSellerOnboardingStepAction(){
        return new CompleteSellerOnboardingStepAction();
    }
    @Bean StartSellerOnboardingStepAction
            startSellerOnboardingStepAction(){
        return new StartSellerOnboardingStepAction();
    }
    @Bean SkipSellerOnboardingStepAction
            skipSellerOnboardingStepAction(){
        return new SkipSellerOnboardingStepAction();
    }


}
