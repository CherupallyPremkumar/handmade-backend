package com.handmade.ecommerce.returnrequest.configuration;

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
import com.handmade.ecommerce.order.model.ReturnRequest;
import com.handmade.ecommerce.returnrequest.service.cmds.*;
import com.handmade.ecommerce.returnrequest.service.healthcheck.ReturnRequestHealthChecker;
import com.handmade.ecommerce.returnrequest.service.store.ReturnRequestEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class ReturnRequestConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/returnrequest/returnrequest-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "ReturnRequest";
    public static final String PREFIX_FOR_RESOLVER = "returnrequest";

    @Bean BeanFactoryAdapter returnrequestBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl returnrequestFlowStore(
            @Qualifier("returnrequestBeanFactoryAdapter") BeanFactoryAdapter returnrequestBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(returnrequestBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<ReturnRequest> returnrequestEntityStm(@Qualifier("returnrequestFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<ReturnRequest> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider returnrequestActionsInfoProvider(@Qualifier("returnrequestFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("returnrequest",provider);
        return provider;
	}
	
	@Bean EntityStore<ReturnRequest> returnrequestEntityStore() {
		return new ReturnRequestEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<ReturnRequest> _returnrequestStateEntityService_(
			@Qualifier("returnrequestEntityStm") STM<ReturnRequest> stm,
			@Qualifier("returnrequestActionsInfoProvider") STMActionsInfoProvider returnrequestInfoProvider,
			@Qualifier("returnrequestEntityStore") EntityStore<ReturnRequest> entityStore){
		return new StateEntityServiceImpl<>(stm, returnrequestInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<ReturnRequest> returnrequestEntryAction(@Qualifier("returnrequestEntityStore") EntityStore<ReturnRequest> entityStore,
			@Qualifier("returnrequestActionsInfoProvider") STMActionsInfoProvider returnrequestInfoProvider,
            @Qualifier("returnrequestFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<ReturnRequest> entryAction =  new GenericEntryAction<ReturnRequest>(entityStore,returnrequestInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<ReturnRequest> returnrequestExitAction(@Qualifier("returnrequestFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<ReturnRequest> exitAction = new GenericExitAction<ReturnRequest>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader returnrequestFlowReader(@Qualifier("returnrequestFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean ReturnRequestHealthChecker returnrequestHealthChecker(){
    	return new ReturnRequestHealthChecker();
    }

    @Bean STMTransitionAction<ReturnRequest> defaultreturnrequestSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver returnrequestTransitionActionResolver(
    @Qualifier("defaultreturnrequestSTMTransitionAction") STMTransitionAction<ReturnRequest> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector returnrequestBodyTypeSelector(
    @Qualifier("returnrequestActionsInfoProvider") STMActionsInfoProvider returnrequestInfoProvider,
    @Qualifier("returnrequestTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(returnrequestInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<ReturnRequest> returnrequestBaseTransitionAction(
        @Qualifier("returnrequestTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "returnrequest" + eventId for the method name. (returnrequest is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/returnrequest/returnrequest-states.xml

    @Bean InspectReturnRequestAction
            returnrequestInspect(){
        return new InspectReturnRequestAction();
    }
    @Bean RefundReturnRequestAction
            returnrequestRefund(){
        return new RefundReturnRequestAction();
    }
    @Bean ReceiveReturnRequestAction
            returnrequestReceive(){
        return new ReceiveReturnRequestAction();
    }
    @Bean RejectReturnRequestAction
            returnrequestReject(){
        return new RejectReturnRequestAction();
    }
    @Bean AcceptReturnRequestAction
            returnrequestAccept(){
        return new AcceptReturnRequestAction();
    }
    @Bean CancelReturnRequestAction
            returnrequestCancel(){
        return new CancelReturnRequestAction();
    }
    @Bean ReviewReturnRequestAction
            returnrequestReview(){
        return new ReviewReturnRequestAction();
    }
    @Bean ShipReturnRequestAction
            returnrequestShip(){
        return new ShipReturnRequestAction();
    }
    @Bean ApproveReturnRequestAction
            returnrequestApprove(){
        return new ApproveReturnRequestAction();
    }
    @Bean RejectReturnRequestAction
            returnrequestReject(){
        return new RejectReturnRequestAction();
    }


}
