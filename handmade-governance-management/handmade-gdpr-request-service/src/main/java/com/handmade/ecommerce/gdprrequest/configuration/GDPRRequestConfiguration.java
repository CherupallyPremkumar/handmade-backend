package com.handmade.ecommerce.gdprrequest.configuration;

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
import com.handmade.ecommerce.governance.model.GDPRRequest;
import com.handmade.ecommerce.gdprrequest.service.cmds.*;
import com.handmade.ecommerce.gdprrequest.service.healthcheck.GDPRRequestHealthChecker;
import com.handmade.ecommerce.gdprrequest.service.store.GDPRRequestEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class GDPRRequestConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/gdprrequest/gdprrequest-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "GDPRRequest";
    public static final String PREFIX_FOR_RESOLVER = "gdprrequest";

    @Bean BeanFactoryAdapter gdprrequestBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl gdprrequestFlowStore(
            @Qualifier("gdprrequestBeanFactoryAdapter") BeanFactoryAdapter gdprrequestBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(gdprrequestBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<GDPRRequest> gdprrequestEntityStm(@Qualifier("gdprrequestFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<GDPRRequest> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider gdprrequestActionsInfoProvider(@Qualifier("gdprrequestFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("gdprrequest",provider);
        return provider;
	}
	
	@Bean EntityStore<GDPRRequest> gdprrequestEntityStore() {
		return new GDPRRequestEntityStore();
	}
	
	@Bean StateEntityServiceImpl<GDPRRequest> _gdprrequestStateEntityService_(
			@Qualifier("gdprrequestEntityStm") STM<GDPRRequest> stm,
			@Qualifier("gdprrequestActionsInfoProvider") STMActionsInfoProvider gdprrequestInfoProvider,
			@Qualifier("gdprrequestEntityStore") EntityStore<GDPRRequest> entityStore){
		return new StateEntityServiceImpl<>(stm, gdprrequestInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<GDPRRequest> gdprrequestEntryAction(@Qualifier("gdprrequestEntityStore") EntityStore<GDPRRequest> entityStore,
			@Qualifier("gdprrequestActionsInfoProvider") STMActionsInfoProvider gdprrequestInfoProvider,
            @Qualifier("gdprrequestFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<GDPRRequest> entryAction =  new GenericEntryAction<GDPRRequest>(entityStore,gdprrequestInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<GDPRRequest> gdprrequestExitAction(@Qualifier("gdprrequestFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<GDPRRequest> exitAction = new GenericExitAction<GDPRRequest>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader gdprrequestFlowReader(@Qualifier("gdprrequestFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean GDPRRequestHealthChecker gdprrequestHealthChecker(){
    	return new GDPRRequestHealthChecker();
    }

    @Bean STMTransitionAction<GDPRRequest> defaultgdprrequestSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver gdprrequestTransitionActionResolver(
    @Qualifier("defaultgdprrequestSTMTransitionAction") STMTransitionAction<GDPRRequest> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector gdprrequestBodyTypeSelector(
    @Qualifier("gdprrequestActionsInfoProvider") STMActionsInfoProvider gdprrequestInfoProvider,
    @Qualifier("gdprrequestTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(gdprrequestInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<GDPRRequest> gdprrequestBaseTransitionAction(
        @Qualifier("gdprrequestTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "gdprrequest" + eventId for the method name. (gdprrequest is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/gdprrequest/gdprrequest-states.xml

    @Bean FailGdprRequestAction
            failGdprRequestAction(){
        return new FailGdprRequestAction();
    }
    @Bean CompleteGdprRequestAction
            completeGdprRequestAction(){
        return new CompleteGdprRequestAction();
    }
    @Bean ReviewGdprRequestAction
            reviewGdprRequestAction(){
        return new ReviewGdprRequestAction();
    }
    @Bean ProcessGdprRequestAction
            processGdprRequestAction(){
        return new ProcessGdprRequestAction();
    }
    @Bean ApproveGdprRequestAction
            approveGdprRequestAction(){
        return new ApproveGdprRequestAction();
    }
    @Bean RejectGdprRequestAction
            rejectGdprRequestAction(){
        return new RejectGdprRequestAction();
    }


}
