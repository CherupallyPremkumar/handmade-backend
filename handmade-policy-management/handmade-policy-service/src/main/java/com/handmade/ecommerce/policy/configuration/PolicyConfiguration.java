package com.handmade.ecommerce.policy.configuration;

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
import com.handmade.ecommerce.policy.model.Policy;
import com.handmade.ecommerce.policy.service.cmds.*;
import com.handmade.ecommerce.policy.service.healthcheck.PolicyHealthChecker;
import com.handmade.ecommerce.policy.service.store.PolicyEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PolicyConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/policy/policy-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Policy";
    public static final String PREFIX_FOR_RESOLVER = "policy";

    @Bean BeanFactoryAdapter policyBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl policyFlowStore(
            @Qualifier("policyBeanFactoryAdapter") BeanFactoryAdapter policyBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(policyBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Policy> policyEntityStm(@Qualifier("policyFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Policy> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider policyActionsInfoProvider(@Qualifier("policyFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("policy",provider);
        return provider;
	}
	
	@Bean EntityStore<Policy> policyEntityStore() {
		return new PolicyEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Policy> _policyStateEntityService_(
			@Qualifier("policyEntityStm") STM<Policy> stm,
			@Qualifier("policyActionsInfoProvider") STMActionsInfoProvider policyInfoProvider,
			@Qualifier("policyEntityStore") EntityStore<Policy> entityStore){
		return new StateEntityServiceImpl<>(stm, policyInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Policy> policyEntryAction(@Qualifier("policyEntityStore") EntityStore<Policy> entityStore,
			@Qualifier("policyActionsInfoProvider") STMActionsInfoProvider policyInfoProvider,
            @Qualifier("policyFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Policy> entryAction =  new GenericEntryAction<Policy>(entityStore,policyInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Policy> policyExitAction(@Qualifier("policyFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Policy> exitAction = new GenericExitAction<Policy>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader policyFlowReader(@Qualifier("policyFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PolicyHealthChecker policyHealthChecker(){
    	return new PolicyHealthChecker();
    }

    @Bean STMTransitionAction<Policy> defaultpolicySTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver policyTransitionActionResolver(
    @Qualifier("defaultpolicySTMTransitionAction") STMTransitionAction<Policy> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector policyBodyTypeSelector(
    @Qualifier("policyActionsInfoProvider") STMActionsInfoProvider policyInfoProvider,
    @Qualifier("policyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(policyInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Policy> policyBaseTransitionAction(
        @Qualifier("policyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "policy" + eventId for the method name. (policy is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/policy/policy-states.xml

    @Bean ActivatePolicyPolicyAction
            policyActivatePolicy(){
        return new ActivatePolicyPolicyAction();
    }
    @Bean DeprecatePolicyPolicyAction
            policyDeprecatePolicy(){
        return new DeprecatePolicyPolicyAction();
    }
    @Bean SubmitReviewPolicyAction
            policySubmitReview(){
        return new SubmitReviewPolicyAction();
    }
    @Bean ApprovePolicyPolicyAction
            policyApprovePolicy(){
        return new ApprovePolicyPolicyAction();
    }
    @Bean RejectPolicyPolicyAction
            policyRejectPolicy(){
        return new RejectPolicyPolicyAction();
    }


}
