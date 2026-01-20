package com.handmade.ecommerce.pricealert.configuration;

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
import com.handmade.ecommerce.pricing.model.PriceAlert;
import com.handmade.ecommerce.pricealert.service.cmds.*;
import com.handmade.ecommerce.pricealert.service.healthcheck.PriceAlertHealthChecker;
import com.handmade.ecommerce.pricealert.service.store.PriceAlertEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PriceAlertConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/pricealert/pricealert-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "PriceAlert";
    public static final String PREFIX_FOR_RESOLVER = "pricealert";

    @Bean BeanFactoryAdapter pricealertBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl pricealertFlowStore(
            @Qualifier("pricealertBeanFactoryAdapter") BeanFactoryAdapter pricealertBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(pricealertBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<PriceAlert> pricealertEntityStm(@Qualifier("pricealertFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<PriceAlert> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider pricealertActionsInfoProvider(@Qualifier("pricealertFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("pricealert",provider);
        return provider;
	}
	
	@Bean EntityStore<PriceAlert> pricealertEntityStore() {
		return new PriceAlertEntityStore();
	}
	
	@Bean StateEntityServiceImpl<PriceAlert> _pricealertStateEntityService_(
			@Qualifier("pricealertEntityStm") STM<PriceAlert> stm,
			@Qualifier("pricealertActionsInfoProvider") STMActionsInfoProvider pricealertInfoProvider,
			@Qualifier("pricealertEntityStore") EntityStore<PriceAlert> entityStore){
		return new StateEntityServiceImpl<>(stm, pricealertInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<PriceAlert> pricealertEntryAction(@Qualifier("pricealertEntityStore") EntityStore<PriceAlert> entityStore,
			@Qualifier("pricealertActionsInfoProvider") STMActionsInfoProvider pricealertInfoProvider,
            @Qualifier("pricealertFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<PriceAlert> entryAction =  new GenericEntryAction<PriceAlert>(entityStore,pricealertInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<PriceAlert> pricealertExitAction(@Qualifier("pricealertFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<PriceAlert> exitAction = new GenericExitAction<PriceAlert>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader pricealertFlowReader(@Qualifier("pricealertFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PriceAlertHealthChecker pricealertHealthChecker(){
    	return new PriceAlertHealthChecker();
    }

    @Bean STMTransitionAction<PriceAlert> defaultpricealertSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver pricealertTransitionActionResolver(
    @Qualifier("defaultpricealertSTMTransitionAction") STMTransitionAction<PriceAlert> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector pricealertBodyTypeSelector(
    @Qualifier("pricealertActionsInfoProvider") STMActionsInfoProvider pricealertInfoProvider,
    @Qualifier("pricealertTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(pricealertInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<PriceAlert> pricealertBaseTransitionAction(
        @Qualifier("pricealertTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "pricealert" + eventId for the method name. (pricealert is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/pricealert/pricealert-states.xml

    @Bean ResumePriceAlertAction
            pricealertResume(){
        return new ResumePriceAlertAction();
    }
    @Bean CancelPriceAlertAction
            pricealertCancel(){
        return new CancelPriceAlertAction();
    }
    @Bean ExpirePriceAlertAction
            pricealertExpire(){
        return new ExpirePriceAlertAction();
    }
    @Bean TriggerPriceAlertAction
            pricealertTrigger(){
        return new TriggerPriceAlertAction();
    }
    @Bean PausePriceAlertAction
            pricealertPause(){
        return new PausePriceAlertAction();
    }
    @Bean NotifyPriceAlertAction
            pricealertNotify(){
        return new NotifyPriceAlertAction();
    }
    @Bean AcknowledgePriceAlertAction
            pricealertAcknowledge(){
        return new AcknowledgePriceAlertAction();
    }


}
