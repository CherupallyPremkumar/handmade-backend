package com.handmade.ecommerce.sellerverification.configuration;

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
import com.handmade.ecommerce.onboarding.model.SellerVerification;
import com.handmade.ecommerce.sellerverification.service.cmds.*;
import com.handmade.ecommerce.sellerverification.service.healthcheck.SellerVerificationHealthChecker;
import com.handmade.ecommerce.sellerverification.service.store.SellerVerificationEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class SellerVerificationConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/sellerverification/sellerverification-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "SellerVerification";
    public static final String PREFIX_FOR_RESOLVER = "sellerverification";

    @Bean BeanFactoryAdapter sellerverificationBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl sellerverificationFlowStore(
            @Qualifier("sellerverificationBeanFactoryAdapter") BeanFactoryAdapter sellerverificationBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(sellerverificationBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<SellerVerification> sellerverificationEntityStm(@Qualifier("sellerverificationFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<SellerVerification> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider sellerverificationActionsInfoProvider(@Qualifier("sellerverificationFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("sellerverification",provider);
        return provider;
	}
	
	@Bean EntityStore<SellerVerification> sellerverificationEntityStore() {
		return new SellerVerificationEntityStore();
	}
	
	@Bean StateEntityServiceImpl<SellerVerification> _sellerverificationStateEntityService_(
			@Qualifier("sellerverificationEntityStm") STM<SellerVerification> stm,
			@Qualifier("sellerverificationActionsInfoProvider") STMActionsInfoProvider sellerverificationInfoProvider,
			@Qualifier("sellerverificationEntityStore") EntityStore<SellerVerification> entityStore){
		return new StateEntityServiceImpl<>(stm, sellerverificationInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<SellerVerification> sellerverificationEntryAction(@Qualifier("sellerverificationEntityStore") EntityStore<SellerVerification> entityStore,
			@Qualifier("sellerverificationActionsInfoProvider") STMActionsInfoProvider sellerverificationInfoProvider,
            @Qualifier("sellerverificationFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<SellerVerification> entryAction =  new GenericEntryAction<SellerVerification>(entityStore,sellerverificationInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<SellerVerification> sellerverificationExitAction(@Qualifier("sellerverificationFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<SellerVerification> exitAction = new GenericExitAction<SellerVerification>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader sellerverificationFlowReader(@Qualifier("sellerverificationFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean SellerVerificationHealthChecker sellerverificationHealthChecker(){
    	return new SellerVerificationHealthChecker();
    }

    @Bean STMTransitionAction<SellerVerification> defaultsellerverificationSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver sellerverificationTransitionActionResolver(
    @Qualifier("defaultsellerverificationSTMTransitionAction") STMTransitionAction<SellerVerification> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector sellerverificationBodyTypeSelector(
    @Qualifier("sellerverificationActionsInfoProvider") STMActionsInfoProvider sellerverificationInfoProvider,
    @Qualifier("sellerverificationTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(sellerverificationInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<SellerVerification> sellerverificationBaseTransitionAction(
        @Qualifier("sellerverificationTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "sellerverification" + eventId for the method name. (sellerverification is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/sellerverification/sellerverification-states.xml



    @Bean RequiresReviewSellerVerificationAction
            requiresReviewSellerVerificationAction(){
        return new RequiresReviewSellerVerificationAction();
    }
    @Bean ApproveSellerVerificationAction
            approveSellerVerificationAction(){
        return new ApproveSellerVerificationAction();
    }
    @Bean RejectSellerVerificationAction
            sellerVerificationAction(){
        return new RejectSellerVerificationAction();
    }
    @Bean CancelSellerVerificationAction
            cancelSellerVerificationAction(){
        return new CancelSellerVerificationAction();
    }
    @Bean SubmitSellerVerificationAction
            submitSellerVerificationAction(){
        return new SubmitSellerVerificationAction();
    }
    @Bean VerifySellerVerificationAction
            verifySellerVerificationAction(){
        return new VerifySellerVerificationAction();
    }
    @Bean RetrySellerVerificationAction
            retrySellerVerificationAction(){
        return new RetrySellerVerificationAction();
    }


}
