package com.handmade.ecommerce.pricingruledefinition.configuration;

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
import com.handmade.ecommerce.pricing.model.PricingRuleDefinition;
import com.handmade.ecommerce.pricingruledefinition.service.cmds.*;
import com.handmade.ecommerce.pricingruledefinition.service.healthcheck.PricingRuleDefinitionHealthChecker;
import com.handmade.ecommerce.pricingruledefinition.service.store.PricingRuleDefinitionEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PricingRuleDefinitionConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/pricingruledefinition/pricingruledefinition-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "PricingRuleDefinition";
    public static final String PREFIX_FOR_RESOLVER = "pricingruledefinition";

    @Bean BeanFactoryAdapter pricingruledefinitionBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl pricingruledefinitionFlowStore(
            @Qualifier("pricingruledefinitionBeanFactoryAdapter") BeanFactoryAdapter pricingruledefinitionBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(pricingruledefinitionBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<PricingRuleDefinition> pricingruledefinitionEntityStm(@Qualifier("pricingruledefinitionFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<PricingRuleDefinition> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider pricingruledefinitionActionsInfoProvider(@Qualifier("pricingruledefinitionFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("pricingruledefinition",provider);
        return provider;
	}
	
	@Bean EntityStore<PricingRuleDefinition> pricingruledefinitionEntityStore() {
		return new PricingRuleDefinitionEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<PricingRuleDefinition> _pricingruledefinitionStateEntityService_(
			@Qualifier("pricingruledefinitionEntityStm") STM<PricingRuleDefinition> stm,
			@Qualifier("pricingruledefinitionActionsInfoProvider") STMActionsInfoProvider pricingruledefinitionInfoProvider,
			@Qualifier("pricingruledefinitionEntityStore") EntityStore<PricingRuleDefinition> entityStore){
		return new StateEntityServiceImpl<>(stm, pricingruledefinitionInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<PricingRuleDefinition> pricingruledefinitionEntryAction(@Qualifier("pricingruledefinitionEntityStore") EntityStore<PricingRuleDefinition> entityStore,
			@Qualifier("pricingruledefinitionActionsInfoProvider") STMActionsInfoProvider pricingruledefinitionInfoProvider,
            @Qualifier("pricingruledefinitionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<PricingRuleDefinition> entryAction =  new GenericEntryAction<PricingRuleDefinition>(entityStore,pricingruledefinitionInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<PricingRuleDefinition> pricingruledefinitionExitAction(@Qualifier("pricingruledefinitionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<PricingRuleDefinition> exitAction = new GenericExitAction<PricingRuleDefinition>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader pricingruledefinitionFlowReader(@Qualifier("pricingruledefinitionFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PricingRuleDefinitionHealthChecker pricingruledefinitionHealthChecker(){
    	return new PricingRuleDefinitionHealthChecker();
    }

    @Bean STMTransitionAction<PricingRuleDefinition> defaultpricingruledefinitionSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver pricingruledefinitionTransitionActionResolver(
    @Qualifier("defaultpricingruledefinitionSTMTransitionAction") STMTransitionAction<PricingRuleDefinition> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector pricingruledefinitionBodyTypeSelector(
    @Qualifier("pricingruledefinitionActionsInfoProvider") STMActionsInfoProvider pricingruledefinitionInfoProvider,
    @Qualifier("pricingruledefinitionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(pricingruledefinitionInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<PricingRuleDefinition> pricingruledefinitionBaseTransitionAction(
        @Qualifier("pricingruledefinitionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "pricingruledefinition" + eventId for the method name. (pricingruledefinition is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/pricingruledefinition/pricingruledefinition-states.xml

    @Bean DeprecatePricingRuleDefinitionAction
            pricingruledefinitionDeprecate(){
        return new DeprecatePricingRuleDefinitionAction();
    }
    @Bean DeactivatePricingRuleDefinitionAction
            pricingruledefinitionDeactivate(){
        return new DeactivatePricingRuleDefinitionAction();
    }
    @Bean SubmitPricingRuleDefinitionAction
            pricingruledefinitionSubmit(){
        return new SubmitPricingRuleDefinitionAction();
    }
    @Bean DeprecatePricingRuleDefinitionAction
            pricingruledefinitionDeprecate(){
        return new DeprecatePricingRuleDefinitionAction();
    }
    @Bean ActivatePricingRuleDefinitionAction
            pricingruledefinitionActivate(){
        return new ActivatePricingRuleDefinitionAction();
    }
    @Bean ApprovePricingRuleDefinitionAction
            pricingruledefinitionApprove(){
        return new ApprovePricingRuleDefinitionAction();
    }
    @Bean RejectPricingRuleDefinitionAction
            pricingruledefinitionReject(){
        return new RejectPricingRuleDefinitionAction();
    }
    @Bean ActivatePricingRuleDefinitionAction
            pricingruledefinitionActivate(){
        return new ActivatePricingRuleDefinitionAction();
    }


}
