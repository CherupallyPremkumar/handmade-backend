package com.handmade.ecommerce.pricing.configuration;

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
import com.handmade.ecommerce.pricing.model.Pricing;
import com.handmade.ecommerce.pricing.service.cmds.*;
import com.handmade.ecommerce.pricing.service.healthcheck.PricingHealthChecker;
import com.handmade.ecommerce.pricing.service.store.PricingEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PricingConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/pricing/pricing-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Pricing";
    public static final String PREFIX_FOR_RESOLVER = "pricing";

    @Bean BeanFactoryAdapter pricingBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl pricingFlowStore(
            @Qualifier("pricingBeanFactoryAdapter") BeanFactoryAdapter pricingBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(pricingBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Pricing> pricingEntityStm(@Qualifier("pricingFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Pricing> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider pricingActionsInfoProvider(@Qualifier("pricingFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("pricing",provider);
        return provider;
	}
	
	@Bean EntityStore<Pricing> pricingEntityStore() {
		return new PricingEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Pricing> _pricingStateEntityService_(
			@Qualifier("pricingEntityStm") STM<Pricing> stm,
			@Qualifier("pricingActionsInfoProvider") STMActionsInfoProvider pricingInfoProvider,
			@Qualifier("pricingEntityStore") EntityStore<Pricing> entityStore){
		return new StateEntityServiceImpl<>(stm, pricingInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Pricing> pricingEntryAction(@Qualifier("pricingEntityStore") EntityStore<Pricing> entityStore,
			@Qualifier("pricingActionsInfoProvider") STMActionsInfoProvider pricingInfoProvider,
            @Qualifier("pricingFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Pricing> entryAction =  new GenericEntryAction<Pricing>(entityStore,pricingInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Pricing> pricingExitAction(@Qualifier("pricingFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Pricing> exitAction = new GenericExitAction<Pricing>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader pricingFlowReader(@Qualifier("pricingFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PricingHealthChecker pricingHealthChecker(){
    	return new PricingHealthChecker();
    }

    @Bean STMTransitionAction<Pricing> defaultpricingSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver pricingTransitionActionResolver(
    @Qualifier("defaultpricingSTMTransitionAction") STMTransitionAction<Pricing> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector pricingBodyTypeSelector(
    @Qualifier("pricingActionsInfoProvider") STMActionsInfoProvider pricingInfoProvider,
    @Qualifier("pricingTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(pricingInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Pricing> pricingBaseTransitionAction(
        @Qualifier("pricingTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "pricing" + eventId for the method name. (pricing is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/pricing/pricing-states.xml

    @Bean ResumePricingAction
            pricingResume(){
        return new ResumePricingAction();
    }
    @Bean ArchivePricingAction
            pricingArchive(){
        return new ArchivePricingAction();
    }
    @Bean ArchivePricingAction
            pricingArchive(){
        return new ArchivePricingAction();
    }
    @Bean PausePricingAction
            pricingPause(){
        return new PausePricingAction();
    }
    @Bean ActivatePricingAction
            pricingActivate(){
        return new ActivatePricingAction();
    }


}
