package com.handmade.ecommerce.compliancepolicy.configuration;

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
import com.handmade.ecommerce.risk.model.CompliancePolicy;
import com.handmade.ecommerce.compliancepolicy.service.cmds.*;
import com.handmade.ecommerce.compliancepolicy.service.healthcheck.CompliancePolicyHealthChecker;
import com.handmade.ecommerce.compliancepolicy.service.store.CompliancePolicyEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class CompliancePolicyConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/compliancepolicy/compliancepolicy-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "CompliancePolicy";
    public static final String PREFIX_FOR_RESOLVER = "compliancepolicy";

    @Bean BeanFactoryAdapter compliancepolicyBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl compliancepolicyFlowStore(
            @Qualifier("compliancepolicyBeanFactoryAdapter") BeanFactoryAdapter compliancepolicyBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(compliancepolicyBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<CompliancePolicy> compliancepolicyEntityStm(@Qualifier("compliancepolicyFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<CompliancePolicy> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider compliancepolicyActionsInfoProvider(@Qualifier("compliancepolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("compliancepolicy",provider);
        return provider;
	}
	
	@Bean EntityStore<CompliancePolicy> compliancepolicyEntityStore() {
		return new CompliancePolicyEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<CompliancePolicy> _compliancepolicyStateEntityService_(
			@Qualifier("compliancepolicyEntityStm") STM<CompliancePolicy> stm,
			@Qualifier("compliancepolicyActionsInfoProvider") STMActionsInfoProvider compliancepolicyInfoProvider,
			@Qualifier("compliancepolicyEntityStore") EntityStore<CompliancePolicy> entityStore){
		return new StateEntityServiceImpl<>(stm, compliancepolicyInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<CompliancePolicy> compliancepolicyEntryAction(@Qualifier("compliancepolicyEntityStore") EntityStore<CompliancePolicy> entityStore,
			@Qualifier("compliancepolicyActionsInfoProvider") STMActionsInfoProvider compliancepolicyInfoProvider,
            @Qualifier("compliancepolicyFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<CompliancePolicy> entryAction =  new GenericEntryAction<CompliancePolicy>(entityStore,compliancepolicyInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<CompliancePolicy> compliancepolicyExitAction(@Qualifier("compliancepolicyFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<CompliancePolicy> exitAction = new GenericExitAction<CompliancePolicy>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader compliancepolicyFlowReader(@Qualifier("compliancepolicyFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean CompliancePolicyHealthChecker compliancepolicyHealthChecker(){
    	return new CompliancePolicyHealthChecker();
    }

    @Bean STMTransitionAction<CompliancePolicy> defaultcompliancepolicySTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver compliancepolicyTransitionActionResolver(
    @Qualifier("defaultcompliancepolicySTMTransitionAction") STMTransitionAction<CompliancePolicy> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector compliancepolicyBodyTypeSelector(
    @Qualifier("compliancepolicyActionsInfoProvider") STMActionsInfoProvider compliancepolicyInfoProvider,
    @Qualifier("compliancepolicyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(compliancepolicyInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<CompliancePolicy> compliancepolicyBaseTransitionAction(
        @Qualifier("compliancepolicyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "compliancepolicy" + eventId for the method name. (compliancepolicy is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/compliancepolicy/compliancepolicy-states.xml

    @Bean SuspendCompliancePolicyAction
            compliancepolicySuspend(){
        return new SuspendCompliancePolicyAction();
    }
    @Bean DeprecateCompliancePolicyAction
            compliancepolicyDeprecate(){
        return new DeprecateCompliancePolicyAction();
    }
    @Bean SubmitCompliancePolicyAction
            compliancepolicySubmit(){
        return new SubmitCompliancePolicyAction();
    }
    @Bean DeprecateCompliancePolicyAction
            compliancepolicyDeprecate(){
        return new DeprecateCompliancePolicyAction();
    }
    @Bean ReactivateCompliancePolicyAction
            compliancepolicyReactivate(){
        return new ReactivateCompliancePolicyAction();
    }
    @Bean ApproveCompliancePolicyAction
            compliancepolicyApprove(){
        return new ApproveCompliancePolicyAction();
    }
    @Bean RejectCompliancePolicyAction
            compliancepolicyReject(){
        return new RejectCompliancePolicyAction();
    }
    @Bean ActivateCompliancePolicyAction
            compliancepolicyActivate(){
        return new ActivateCompliancePolicyAction();
    }


}
