package com.handmade.ecommerce.selleronboardingcase.configuration;

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
import com.handmade.ecommerce.onboarding.model.SellerOnboardingCase;
import com.handmade.ecommerce.selleronboardingcase.service.cmds.*;
import com.handmade.ecommerce.selleronboardingcase.service.healthcheck.SellerOnboardingCaseHealthChecker;
import com.handmade.ecommerce.selleronboardingcase.service.store.SellerOnboardingCaseEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class SellerOnboardingCaseConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/selleronboardingcase/selleronboardingcase-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "SellerOnboardingCase";
    public static final String PREFIX_FOR_RESOLVER = "selleronboardingcase";

    @Bean BeanFactoryAdapter selleronboardingcaseBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl selleronboardingcaseFlowStore(
            @Qualifier("selleronboardingcaseBeanFactoryAdapter") BeanFactoryAdapter selleronboardingcaseBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(selleronboardingcaseBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<SellerOnboardingCase> selleronboardingcaseEntityStm(@Qualifier("selleronboardingcaseFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<SellerOnboardingCase> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider selleronboardingcaseActionsInfoProvider(@Qualifier("selleronboardingcaseFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("selleronboardingcase",provider);
        return provider;
	}
	
	@Bean EntityStore<SellerOnboardingCase> selleronboardingcaseEntityStore() {
		return new SellerOnboardingCaseEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<SellerOnboardingCase> _selleronboardingcaseStateEntityService_(
			@Qualifier("selleronboardingcaseEntityStm") STM<SellerOnboardingCase> stm,
			@Qualifier("selleronboardingcaseActionsInfoProvider") STMActionsInfoProvider selleronboardingcaseInfoProvider,
			@Qualifier("selleronboardingcaseEntityStore") EntityStore<SellerOnboardingCase> entityStore){
		return new StateEntityServiceImpl<>(stm, selleronboardingcaseInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<SellerOnboardingCase> selleronboardingcaseEntryAction(@Qualifier("selleronboardingcaseEntityStore") EntityStore<SellerOnboardingCase> entityStore,
			@Qualifier("selleronboardingcaseActionsInfoProvider") STMActionsInfoProvider selleronboardingcaseInfoProvider,
            @Qualifier("selleronboardingcaseFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<SellerOnboardingCase> entryAction =  new GenericEntryAction<SellerOnboardingCase>(entityStore,selleronboardingcaseInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<SellerOnboardingCase> selleronboardingcaseExitAction(@Qualifier("selleronboardingcaseFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<SellerOnboardingCase> exitAction = new GenericExitAction<SellerOnboardingCase>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader selleronboardingcaseFlowReader(@Qualifier("selleronboardingcaseFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean SellerOnboardingCaseHealthChecker selleronboardingcaseHealthChecker(){
    	return new SellerOnboardingCaseHealthChecker();
    }

    @Bean STMTransitionAction<SellerOnboardingCase> defaultselleronboardingcaseSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver selleronboardingcaseTransitionActionResolver(
    @Qualifier("defaultselleronboardingcaseSTMTransitionAction") STMTransitionAction<SellerOnboardingCase> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector selleronboardingcaseBodyTypeSelector(
    @Qualifier("selleronboardingcaseActionsInfoProvider") STMActionsInfoProvider selleronboardingcaseInfoProvider,
    @Qualifier("selleronboardingcaseTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(selleronboardingcaseInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<SellerOnboardingCase> selleronboardingcaseBaseTransitionAction(
        @Qualifier("selleronboardingcaseTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "selleronboardingcase" + eventId for the method name. (selleronboardingcase is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/selleronboardingcase/selleronboardingcase-states.xml

    @Bean CancelSellerOnboardingCaseAction
            selleronboardingcaseCancel(){
        return new CancelSellerOnboardingCaseAction();
    }
    @Bean SubmitSellerOnboardingCaseAction
            selleronboardingcaseSubmit(){
        return new SubmitSellerOnboardingCaseAction();
    }
    @Bean CancelSellerOnboardingCaseAction
            selleronboardingcaseCancel(){
        return new CancelSellerOnboardingCaseAction();
    }
    @Bean StartSellerOnboardingCaseAction
            selleronboardingcaseStart(){
        return new StartSellerOnboardingCaseAction();
    }
    @Bean ReviewSellerOnboardingCaseAction
            selleronboardingcaseReview(){
        return new ReviewSellerOnboardingCaseAction();
    }
    @Bean CancelSellerOnboardingCaseAction
            selleronboardingcaseCancel(){
        return new CancelSellerOnboardingCaseAction();
    }
    @Bean ProvideInfoSellerOnboardingCaseAction
            selleronboardingcaseProvideInfo(){
        return new ProvideInfoSellerOnboardingCaseAction();
    }
    @Bean ActivateSellerOnboardingCaseAction
            selleronboardingcaseActivate(){
        return new ActivateSellerOnboardingCaseAction();
    }
    @Bean ApproveSellerOnboardingCaseAction
            selleronboardingcaseApprove(){
        return new ApproveSellerOnboardingCaseAction();
    }
    @Bean RejectSellerOnboardingCaseAction
            selleronboardingcaseReject(){
        return new RejectSellerOnboardingCaseAction();
    }
    @Bean RequestInfoSellerOnboardingCaseAction
            selleronboardingcaseRequestInfo(){
        return new RequestInfoSellerOnboardingCaseAction();
    }


}
