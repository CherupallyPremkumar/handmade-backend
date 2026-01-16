package com.handmade.ecommerce.onboarding.configuration;

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
import com.handmade.ecommerce.onboarding.model.Onboarding;
import com.handmade.ecommerce.onboarding.service.cmds.*;
import com.handmade.ecommerce.onboarding.service.healthcheck.OnboardingHealthChecker;
import com.handmade.ecommerce.onboarding.service.store.OnboardingEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class OnboardingConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/onboarding/onboarding-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Onboarding";
    public static final String PREFIX_FOR_RESOLVER = "onboarding";

    @Bean BeanFactoryAdapter onboardingBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl onboardingFlowStore(
            @Qualifier("onboardingBeanFactoryAdapter") BeanFactoryAdapter onboardingBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(onboardingBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Onboarding> onboardingEntityStm(@Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Onboarding> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider onboardingActionsInfoProvider(@Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("onboarding",provider);
        return provider;
	}
	
	@Bean EntityStore<Onboarding> onboardingEntityStore() {
		return new OnboardingEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Onboarding> _onboardingStateEntityService_(
			@Qualifier("onboardingEntityStm") STM<Onboarding> stm,
			@Qualifier("onboardingActionsInfoProvider") STMActionsInfoProvider onboardingInfoProvider,
			@Qualifier("onboardingEntityStore") EntityStore<Onboarding> entityStore){
		return new StateEntityServiceImpl<>(stm, onboardingInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Onboarding> onboardingEntryAction(@Qualifier("onboardingEntityStore") EntityStore<Onboarding> entityStore,
			@Qualifier("onboardingActionsInfoProvider") STMActionsInfoProvider onboardingInfoProvider,
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Onboarding> entryAction =  new GenericEntryAction<Onboarding>(entityStore,onboardingInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Onboarding> onboardingExitAction(@Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Onboarding> exitAction = new GenericExitAction<Onboarding>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader onboardingFlowReader(@Qualifier("onboardingFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean OnboardingHealthChecker onboardingHealthChecker(){
    	return new OnboardingHealthChecker();
    }

    @Bean STMTransitionAction<Onboarding> defaultonboardingSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver onboardingTransitionActionResolver(
    @Qualifier("defaultonboardingSTMTransitionAction") STMTransitionAction<Onboarding> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector onboardingBodyTypeSelector(
    @Qualifier("onboardingActionsInfoProvider") STMActionsInfoProvider onboardingInfoProvider,
    @Qualifier("onboardingTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(onboardingInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Onboarding> onboardingBaseTransitionAction(
        @Qualifier("onboardingTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "onboarding" + eventId for the method name. (onboarding is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/onboarding/onboarding-states.xml

    @Bean RejectOnboardingAction
            onboardingReject(){
        return new RejectOnboardingAction();
    }
    @Bean PolicyFlaggedOnboardingAction
            onboardingPolicyFlagged(){
        return new PolicyFlaggedOnboardingAction();
    }
    @Bean PolicyApprovedOnboardingAction
            onboardingPolicyApproved(){
        return new PolicyApprovedOnboardingAction();
    }
    @Bean GoLiveConfirmedOnboardingAction
            onboardingGoLiveConfirmed(){
        return new GoLiveConfirmedOnboardingAction();
    }
    @Bean KycRejectedOnboardingAction
            onboardingKycRejected(){
        return new KycRejectedOnboardingAction();
    }
    @Bean SubmitDocsOnboardingAction
            onboardingSubmitDocs(){
        return new SubmitDocsOnboardingAction();
    }
    @Bean KycApprovedOnboardingAction
            onboardingKycApproved(){
        return new KycApprovedOnboardingAction();
    }
    @Bean StartPolicyEvalOnboardingAction
            onboardingStartPolicyEval(){
        return new StartPolicyEvalOnboardingAction();
    }
    @Bean RejectOnboardingAction
            onboardingReject(){
        return new RejectOnboardingAction();
    }
    @Bean ManualApproveOnboardingAction
            onboardingManualApprove(){
        return new ManualApproveOnboardingAction();
    }
    @Bean FinanceProfileCreatedOnboardingAction
            onboardingFinanceProfileCreated(){
        return new FinanceProfileCreatedOnboardingAction();
    }
    @Bean RejectOnboardingAction
            onboardingReject(){
        return new RejectOnboardingAction();
    }
    @Bean KycStartedOnboardingAction
            onboardingKycStarted(){
        return new KycStartedOnboardingAction();
    }


}
