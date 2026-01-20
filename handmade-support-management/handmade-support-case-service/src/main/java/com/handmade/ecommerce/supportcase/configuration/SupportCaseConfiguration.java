package com.handmade.ecommerce.supportcase.configuration;

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
import com.handmade.ecommerce.support.model.SupportCase;
import com.handmade.ecommerce.supportcase.service.cmds.*;
import com.handmade.ecommerce.supportcase.service.healthcheck.SupportCaseHealthChecker;
import com.handmade.ecommerce.supportcase.service.store.SupportCaseEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class SupportCaseConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/supportcase/supportcase-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "SupportCase";
    public static final String PREFIX_FOR_RESOLVER = "supportcase";

    @Bean BeanFactoryAdapter supportcaseBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl supportcaseFlowStore(
            @Qualifier("supportcaseBeanFactoryAdapter") BeanFactoryAdapter supportcaseBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(supportcaseBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<SupportCase> supportcaseEntityStm(@Qualifier("supportcaseFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<SupportCase> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider supportcaseActionsInfoProvider(@Qualifier("supportcaseFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("supportcase",provider);
        return provider;
	}
	
	@Bean EntityStore<SupportCase> supportcaseEntityStore() {
		return new SupportCaseEntityStore();
	}
	
	@Bean StateEntityServiceImpl<SupportCase> _supportcaseStateEntityService_(
			@Qualifier("supportcaseEntityStm") STM<SupportCase> stm,
			@Qualifier("supportcaseActionsInfoProvider") STMActionsInfoProvider supportcaseInfoProvider,
			@Qualifier("supportcaseEntityStore") EntityStore<SupportCase> entityStore){
		return new StateEntityServiceImpl<>(stm, supportcaseInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<SupportCase> supportcaseEntryAction(@Qualifier("supportcaseEntityStore") EntityStore<SupportCase> entityStore,
			@Qualifier("supportcaseActionsInfoProvider") STMActionsInfoProvider supportcaseInfoProvider,
            @Qualifier("supportcaseFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<SupportCase> entryAction =  new GenericEntryAction<SupportCase>(entityStore,supportcaseInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<SupportCase> supportcaseExitAction(@Qualifier("supportcaseFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<SupportCase> exitAction = new GenericExitAction<SupportCase>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader supportcaseFlowReader(@Qualifier("supportcaseFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean SupportCaseHealthChecker supportcaseHealthChecker(){
    	return new SupportCaseHealthChecker();
    }

    @Bean STMTransitionAction<SupportCase> defaultsupportcaseSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver supportcaseTransitionActionResolver(
    @Qualifier("defaultsupportcaseSTMTransitionAction") STMTransitionAction<SupportCase> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector supportcaseBodyTypeSelector(
    @Qualifier("supportcaseActionsInfoProvider") STMActionsInfoProvider supportcaseInfoProvider,
    @Qualifier("supportcaseTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(supportcaseInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<SupportCase> supportcaseBaseTransitionAction(
        @Qualifier("supportcaseTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "supportcase" + eventId for the method name. (supportcase is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/supportcase/supportcase-states.xml

    @Bean ResolveSupportCaseAction
            resolveSupportCaseAction(){
        return new ResolveSupportCaseAction();
    }
    @Bean AwaitCustomerSupportCaseAction
            awaitCustomerSupportCaseAction(){
        return new AwaitCustomerSupportCaseAction();
    }
    @Bean EscalateSupportCaseAction
            escalateSupportCaseAction(){
        return new EscalateSupportCaseAction();
    }
    @Bean ReopenSupportCaseAction
            reopenSupportCaseAction(){
        return new ReopenSupportCaseAction();
    }
    @Bean CloseSupportCaseAction
            closeSupportCaseAction(){
        return new CloseSupportCaseAction();
    }
    @Bean RespondSupportCaseAction
            respondSupportCaseAction(){
        return new RespondSupportCaseAction();
    }
    @Bean ReassignSupportCaseAction
            reassignSupportCaseAction(){
        return new ReassignSupportCaseAction();
    }

    @Bean AssignSupportCaseAction
            assignSupportCaseAction(){
        return new AssignSupportCaseAction();
    }
    @Bean InvestigateSupportCaseAction
            investigateSupportCaseAction(){
        return new InvestigateSupportCaseAction();
    }


}
