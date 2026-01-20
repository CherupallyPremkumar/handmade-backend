package com.handmade.ecommerce.pricerule.configuration;

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
import com.handmade.ecommerce.offer.model.PriceRule;
import com.handmade.ecommerce.pricerule.service.cmds.*;
import com.handmade.ecommerce.pricerule.service.healthcheck.PriceRuleHealthChecker;
import com.handmade.ecommerce.pricerule.service.store.PriceRuleEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PriceRuleConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/pricerule/pricerule-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "PriceRule";
    public static final String PREFIX_FOR_RESOLVER = "pricerule";

    @Bean BeanFactoryAdapter priceruleBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl priceruleFlowStore(
            @Qualifier("priceruleBeanFactoryAdapter") BeanFactoryAdapter priceruleBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(priceruleBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<PriceRule> priceruleEntityStm(@Qualifier("priceruleFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<PriceRule> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider priceruleActionsInfoProvider(@Qualifier("priceruleFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("pricerule",provider);
        return provider;
	}
	
	@Bean EntityStore<PriceRule> priceruleEntityStore() {
		return new PriceRuleEntityStore();
	}
	
	@Bean StateEntityServiceImpl<PriceRule> _priceruleStateEntityService_(
			@Qualifier("priceruleEntityStm") STM<PriceRule> stm,
			@Qualifier("priceruleActionsInfoProvider") STMActionsInfoProvider priceruleInfoProvider,
			@Qualifier("priceruleEntityStore") EntityStore<PriceRule> entityStore){
		return new StateEntityServiceImpl<>(stm, priceruleInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<PriceRule> priceruleEntryAction(@Qualifier("priceruleEntityStore") EntityStore<PriceRule> entityStore,
			@Qualifier("priceruleActionsInfoProvider") STMActionsInfoProvider priceruleInfoProvider,
            @Qualifier("priceruleFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<PriceRule> entryAction =  new GenericEntryAction<PriceRule>(entityStore,priceruleInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<PriceRule> priceruleExitAction(@Qualifier("priceruleFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<PriceRule> exitAction = new GenericExitAction<PriceRule>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader priceruleFlowReader(@Qualifier("priceruleFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PriceRuleHealthChecker priceruleHealthChecker(){
    	return new PriceRuleHealthChecker();
    }

    @Bean STMTransitionAction<PriceRule> defaultpriceruleSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver priceruleTransitionActionResolver(
    @Qualifier("defaultpriceruleSTMTransitionAction") STMTransitionAction<PriceRule> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector priceruleBodyTypeSelector(
    @Qualifier("priceruleActionsInfoProvider") STMActionsInfoProvider priceruleInfoProvider,
    @Qualifier("priceruleTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(priceruleInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<PriceRule> priceruleBaseTransitionAction(
        @Qualifier("priceruleTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "pricerule" + eventId for the method name. (pricerule is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/pricerule/pricerule-states.xml


    @Bean DeactivatePriceRuleAction
            deactivatePriceRuleAction(){
        return new DeactivatePriceRuleAction();
    }
    @Bean SubmitPriceRuleAction
            submitPriceRuleAction(){
        return new SubmitPriceRuleAction();
    }
    @Bean DeprecatePriceRuleAction
            deprecatePriceRuleAction(){
        return new DeprecatePriceRuleAction();
    }

    @Bean ApprovePriceRuleAction
            approvePriceRuleAction(){
        return new ApprovePriceRuleAction();
    }
    @Bean RejectPriceRuleAction
            rejectPriceRuleAction(){
        return new RejectPriceRuleAction();
    }
    @Bean ActivatePriceRuleAction
            activatePriceRuleActione(){
        return new ActivatePriceRuleAction();
    }


}
