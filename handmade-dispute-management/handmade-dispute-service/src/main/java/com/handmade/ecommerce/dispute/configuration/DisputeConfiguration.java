package com.handmade.ecommerce.dispute.configuration;

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
import com.handmade.ecommerce.dispute.model.Dispute;
import com.handmade.ecommerce.dispute.service.cmds.*;
import com.handmade.ecommerce.dispute.service.healthcheck.DisputeHealthChecker;
import com.handmade.ecommerce.dispute.service.store.DisputeEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class DisputeConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/dispute/dispute-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Dispute";
    public static final String PREFIX_FOR_RESOLVER = "dispute";

    @Bean BeanFactoryAdapter disputeBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl disputeFlowStore(
            @Qualifier("disputeBeanFactoryAdapter") BeanFactoryAdapter disputeBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(disputeBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<Dispute> disputeEntityStm(@Qualifier("disputeFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Dispute> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider disputeActionsInfoProvider(@Qualifier("disputeFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("dispute",provider);
        return provider;
	}
	
	@Bean EntityStore<Dispute> disputeEntityStore() {
		return new DisputeEntityStore();
	}
	
	@Bean StateEntityServiceImpl<Dispute> _disputeStateEntityService_(
			@Qualifier("disputeEntityStm") STM<Dispute> stm,
			@Qualifier("disputeActionsInfoProvider") STMActionsInfoProvider disputeInfoProvider,
			@Qualifier("disputeEntityStore") EntityStore<Dispute> entityStore){
		return new StateEntityServiceImpl<>(stm, disputeInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<Dispute> disputeEntryAction(@Qualifier("disputeEntityStore") EntityStore<Dispute> entityStore,
			@Qualifier("disputeActionsInfoProvider") STMActionsInfoProvider disputeInfoProvider,
            @Qualifier("disputeFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Dispute> entryAction =  new GenericEntryAction<Dispute>(entityStore,disputeInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Dispute> disputeExitAction(@Qualifier("disputeFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Dispute> exitAction = new GenericExitAction<Dispute>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader disputeFlowReader(@Qualifier("disputeFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean DisputeHealthChecker disputeHealthChecker(){
    	return new DisputeHealthChecker();
    }

    @Bean STMTransitionAction<Dispute> defaultdisputeSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver disputeTransitionActionResolver(
    @Qualifier("defaultdisputeSTMTransitionAction") STMTransitionAction<Dispute> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector disputeBodyTypeSelector(
    @Qualifier("disputeActionsInfoProvider") STMActionsInfoProvider disputeInfoProvider,
    @Qualifier("disputeTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(disputeInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<Dispute> disputeBaseTransitionAction(
        @Qualifier("disputeTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "dispute" + eventId for the method name. (dispute is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/dispute/dispute-states.xml

    @Bean ResolveDisputeAction
            resolveDisputeAction(){
        return new ResolveDisputeAction();
    }
    @Bean ReviewDisputeAction
            reviewDisputeAction(){
        return new ReviewDisputeAction();
    }
    @Bean EscalateDisputeAction
            escalateDisputeAction(){
        return new EscalateDisputeAction();
    }

    @Bean CloseDisputeAction
            closeDisputeAction(){
        return new CloseDisputeAction();
    }
    @Bean AssignDisputeAction
           assignDisputeAction(){
        return new AssignDisputeAction();
    }




}
