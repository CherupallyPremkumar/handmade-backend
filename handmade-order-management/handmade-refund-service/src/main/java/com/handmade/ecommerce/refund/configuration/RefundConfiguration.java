package com.handmade.ecommerce.refund.configuration;

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
import com.handmade.ecommerce.order.model.Refund;
import com.handmade.ecommerce.refund.service.cmds.*;
import com.handmade.ecommerce.refund.service.healthcheck.RefundHealthChecker;
import com.handmade.ecommerce.refund.service.store.RefundEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class RefundConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/refund/refund-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Refund";
    public static final String PREFIX_FOR_RESOLVER = "refund";

    @Bean BeanFactoryAdapter refundBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl refundFlowStore(
            @Qualifier("refundBeanFactoryAdapter") BeanFactoryAdapter refundBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(refundBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Refund> refundEntityStm(@Qualifier("refundFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Refund> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider refundActionsInfoProvider(@Qualifier("refundFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("refund",provider);
        return provider;
	}
	
	@Bean EntityStore<Refund> refundEntityStore() {
		return new RefundEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Refund> _refundStateEntityService_(
			@Qualifier("refundEntityStm") STM<Refund> stm,
			@Qualifier("refundActionsInfoProvider") STMActionsInfoProvider refundInfoProvider,
			@Qualifier("refundEntityStore") EntityStore<Refund> entityStore){
		return new StateEntityServiceImpl<>(stm, refundInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Refund> refundEntryAction(@Qualifier("refundEntityStore") EntityStore<Refund> entityStore,
			@Qualifier("refundActionsInfoProvider") STMActionsInfoProvider refundInfoProvider,
            @Qualifier("refundFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Refund> entryAction =  new GenericEntryAction<Refund>(entityStore,refundInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Refund> refundExitAction(@Qualifier("refundFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Refund> exitAction = new GenericExitAction<Refund>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader refundFlowReader(@Qualifier("refundFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean RefundHealthChecker refundHealthChecker(){
    	return new RefundHealthChecker();
    }

    @Bean STMTransitionAction<Refund> defaultrefundSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver refundTransitionActionResolver(
    @Qualifier("defaultrefundSTMTransitionAction") STMTransitionAction<Refund> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector refundBodyTypeSelector(
    @Qualifier("refundActionsInfoProvider") STMActionsInfoProvider refundInfoProvider,
    @Qualifier("refundTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(refundInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Refund> refundBaseTransitionAction(
        @Qualifier("refundTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "refund" + eventId for the method name. (refund is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/refund/refund-states.xml

    @Bean RetryRefundAction
            refundRetry(){
        return new RetryRefundAction();
    }
    @Bean FailRefundAction
            refundFail(){
        return new FailRefundAction();
    }
    @Bean CompleteRefundAction
            refundComplete(){
        return new CompleteRefundAction();
    }
    @Bean CancelRefundAction
            refundCancel(){
        return new CancelRefundAction();
    }
    @Bean SubmitRefundAction
            refundSubmit(){
        return new SubmitRefundAction();
    }
    @Bean ApproveRefundAction
            refundApprove(){
        return new ApproveRefundAction();
    }
    @Bean RejectRefundAction
            refundReject(){
        return new RejectRefundAction();
    }
    @Bean ProcessRefundAction
            refundProcess(){
        return new ProcessRefundAction();
    }


}
