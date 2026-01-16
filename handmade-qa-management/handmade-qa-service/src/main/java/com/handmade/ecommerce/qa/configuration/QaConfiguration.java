package com.handmade.ecommerce.qa.configuration;

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
import com.handmade.ecommerce.qa.model.Qa;
import com.handmade.ecommerce.qa.service.cmds.*;
import com.handmade.ecommerce.qa.service.healthcheck.QaHealthChecker;
import com.handmade.ecommerce.qa.service.store.QaEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class QaConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/qa/qa-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Qa";
    public static final String PREFIX_FOR_RESOLVER = "qa";

    @Bean BeanFactoryAdapter qaBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl qaFlowStore(
            @Qualifier("qaBeanFactoryAdapter") BeanFactoryAdapter qaBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(qaBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Qa> qaEntityStm(@Qualifier("qaFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Qa> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider qaActionsInfoProvider(@Qualifier("qaFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("qa",provider);
        return provider;
	}
	
	@Bean EntityStore<Qa> qaEntityStore() {
		return new QaEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Qa> _qaStateEntityService_(
			@Qualifier("qaEntityStm") STM<Qa> stm,
			@Qualifier("qaActionsInfoProvider") STMActionsInfoProvider qaInfoProvider,
			@Qualifier("qaEntityStore") EntityStore<Qa> entityStore){
		return new StateEntityServiceImpl<>(stm, qaInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Qa> qaEntryAction(@Qualifier("qaEntityStore") EntityStore<Qa> entityStore,
			@Qualifier("qaActionsInfoProvider") STMActionsInfoProvider qaInfoProvider,
            @Qualifier("qaFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Qa> entryAction =  new GenericEntryAction<Qa>(entityStore,qaInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Qa> qaExitAction(@Qualifier("qaFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Qa> exitAction = new GenericExitAction<Qa>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader qaFlowReader(@Qualifier("qaFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean QaHealthChecker qaHealthChecker(){
    	return new QaHealthChecker();
    }

    @Bean STMTransitionAction<Qa> defaultqaSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver qaTransitionActionResolver(
    @Qualifier("defaultqaSTMTransitionAction") STMTransitionAction<Qa> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector qaBodyTypeSelector(
    @Qualifier("qaActionsInfoProvider") STMActionsInfoProvider qaInfoProvider,
    @Qualifier("qaTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(qaInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Qa> qaBaseTransitionAction(
        @Qualifier("qaTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "qa" + eventId for the method name. (qa is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/qa/qa-states.xml

    @Bean ReopenQaAction
            qaReopen(){
        return new ReopenQaAction();
    }
    @Bean RejectQaAction
            qaReject(){
        return new RejectQaAction();
    }
    @Bean ApproveQaAction
            qaApprove(){
        return new ApproveQaAction();
    }
    @Bean RejectQaAction
            qaReject(){
        return new RejectQaAction();
    }
    @Bean RejectQaAction
            qaReject(){
        return new RejectQaAction();
    }
    @Bean CloseQaAction
            qaClose(){
        return new CloseQaAction();
    }
    @Bean RestoreQaAction
            qaRestore(){
        return new RestoreQaAction();
    }


}
