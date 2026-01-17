package com.handmade.ecommerce.policydefinition.configuration;

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
import com.handmade.ecommerce.policy.model.PolicyDefinition;
import com.handmade.ecommerce.policydefinition.service.cmds.*;
import com.handmade.ecommerce.policydefinition.service.healthcheck.PolicyDefinitionHealthChecker;
import com.handmade.ecommerce.policydefinition.service.store.PolicyDefinitionEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PolicyDefinitionConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/policydefinition/policydefinition-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "PolicyDefinition";
    public static final String PREFIX_FOR_RESOLVER = "policydefinition";

    @Bean BeanFactoryAdapter policydefinitionBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl policydefinitionFlowStore(
            @Qualifier("policydefinitionBeanFactoryAdapter") BeanFactoryAdapter policydefinitionBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(policydefinitionBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<PolicyDefinition> policydefinitionEntityStm(@Qualifier("policydefinitionFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<PolicyDefinition> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider policydefinitionActionsInfoProvider(@Qualifier("policydefinitionFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("policydefinition",provider);
        return provider;
	}
	
	@Bean EntityStore<PolicyDefinition> policydefinitionEntityStore() {
		return new PolicyDefinitionEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<PolicyDefinition> _policydefinitionStateEntityService_(
			@Qualifier("policydefinitionEntityStm") STM<PolicyDefinition> stm,
			@Qualifier("policydefinitionActionsInfoProvider") STMActionsInfoProvider policydefinitionInfoProvider,
			@Qualifier("policydefinitionEntityStore") EntityStore<PolicyDefinition> entityStore){
		return new StateEntityServiceImpl<>(stm, policydefinitionInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<PolicyDefinition> policydefinitionEntryAction(@Qualifier("policydefinitionEntityStore") EntityStore<PolicyDefinition> entityStore,
			@Qualifier("policydefinitionActionsInfoProvider") STMActionsInfoProvider policydefinitionInfoProvider,
            @Qualifier("policydefinitionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<PolicyDefinition> entryAction =  new GenericEntryAction<PolicyDefinition>(entityStore,policydefinitionInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<PolicyDefinition> policydefinitionExitAction(@Qualifier("policydefinitionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<PolicyDefinition> exitAction = new GenericExitAction<PolicyDefinition>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader policydefinitionFlowReader(@Qualifier("policydefinitionFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PolicyDefinitionHealthChecker policydefinitionHealthChecker(){
    	return new PolicyDefinitionHealthChecker();
    }

    @Bean STMTransitionAction<PolicyDefinition> defaultpolicydefinitionSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver policydefinitionTransitionActionResolver(
    @Qualifier("defaultpolicydefinitionSTMTransitionAction") STMTransitionAction<PolicyDefinition> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector policydefinitionBodyTypeSelector(
    @Qualifier("policydefinitionActionsInfoProvider") STMActionsInfoProvider policydefinitionInfoProvider,
    @Qualifier("policydefinitionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(policydefinitionInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<PolicyDefinition> policydefinitionBaseTransitionAction(
        @Qualifier("policydefinitionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "policydefinition" + eventId for the method name. (policydefinition is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/policydefinition/policydefinition-states.xml

    @Bean SuspendPolicyDefinitionAction
            policydefinitionSuspend(){
        return new SuspendPolicyDefinitionAction();
    }
    @Bean DeprecatePolicyDefinitionAction
            policydefinitionDeprecate(){
        return new DeprecatePolicyDefinitionAction();
    }
    @Bean SubmitPolicyDefinitionAction
            policydefinitionSubmit(){
        return new SubmitPolicyDefinitionAction();
    }
    @Bean DeprecatePolicyDefinitionAction
            policydefinitionDeprecate(){
        return new DeprecatePolicyDefinitionAction();
    }
    @Bean RepublishPolicyDefinitionAction
            policydefinitionRepublish(){
        return new RepublishPolicyDefinitionAction();
    }
    @Bean ApprovePolicyDefinitionAction
            policydefinitionApprove(){
        return new ApprovePolicyDefinitionAction();
    }
    @Bean RejectPolicyDefinitionAction
            policydefinitionReject(){
        return new RejectPolicyDefinitionAction();
    }
    @Bean PublishPolicyDefinitionAction
            policydefinitionPublish(){
        return new PublishPolicyDefinitionAction();
    }


}
