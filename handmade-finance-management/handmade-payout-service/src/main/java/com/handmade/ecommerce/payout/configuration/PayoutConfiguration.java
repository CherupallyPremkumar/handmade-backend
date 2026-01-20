package com.handmade.ecommerce.payout.configuration;

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
import com.handmade.ecommerce.finance.model.Payout;
import com.handmade.ecommerce.payout.service.cmds.*;
import com.handmade.ecommerce.payout.service.healthcheck.PayoutHealthChecker;
import com.handmade.ecommerce.payout.service.store.PayoutEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PayoutConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/payout/payout-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Payout";
    public static final String PREFIX_FOR_RESOLVER = "payout";

    @Bean BeanFactoryAdapter payoutBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl payoutFlowStore(
            @Qualifier("payoutBeanFactoryAdapter") BeanFactoryAdapter payoutBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(payoutBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<Payout> payoutEntityStm(@Qualifier("payoutFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Payout> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider payoutActionsInfoProvider(@Qualifier("payoutFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("payout",provider);
        return provider;
	}
	
	@Bean EntityStore<Payout> payoutEntityStore() {
		return new PayoutEntityStore();
	}
	
	@Bean StateEntityServiceImpl<Payout> _payoutStateEntityService_(
			@Qualifier("payoutEntityStm") STM<Payout> stm,
			@Qualifier("payoutActionsInfoProvider") STMActionsInfoProvider payoutInfoProvider,
			@Qualifier("payoutEntityStore") EntityStore<Payout> entityStore){
		return new StateEntityServiceImpl<>(stm, payoutInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<Payout> payoutEntryAction(@Qualifier("payoutEntityStore") EntityStore<Payout> entityStore,
			@Qualifier("payoutActionsInfoProvider") STMActionsInfoProvider payoutInfoProvider,
            @Qualifier("payoutFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Payout> entryAction =  new GenericEntryAction<Payout>(entityStore,payoutInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Payout> payoutExitAction(@Qualifier("payoutFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Payout> exitAction = new GenericExitAction<Payout>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader payoutFlowReader(@Qualifier("payoutFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PayoutHealthChecker payoutHealthChecker(){
    	return new PayoutHealthChecker();
    }

    @Bean STMTransitionAction<Payout> defaultpayoutSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver payoutTransitionActionResolver(
    @Qualifier("defaultpayoutSTMTransitionAction") STMTransitionAction<Payout> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector payoutBodyTypeSelector(
    @Qualifier("payoutActionsInfoProvider") STMActionsInfoProvider payoutInfoProvider,
    @Qualifier("payoutTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(payoutInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<Payout> payoutBaseTransitionAction(
        @Qualifier("payoutTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "payout" + eventId for the method name. (payout is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/payout/payout-states.xml

    @Bean FailPayoutAction
            payoutFail(){
        return new FailPayoutAction();
    }
    @Bean CompletePayoutAction
            payoutComplete(){
        return new CompletePayoutAction();
    }
    @Bean ProcessPayoutAction
            payoutProcess(){
        return new ProcessPayoutAction();
    }


}
