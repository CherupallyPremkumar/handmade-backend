package com.handmade.ecommerce.auditprocess.configuration;

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
import com.handmade.ecommerce.governance.model.AuditProcess;
import com.handmade.ecommerce.auditprocess.service.cmds.*;
import com.handmade.ecommerce.auditprocess.service.healthcheck.AuditProcessHealthChecker;
import com.handmade.ecommerce.auditprocess.service.store.AuditProcessEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class AuditProcessConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/auditprocess/auditprocess-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "AuditProcess";
    public static final String PREFIX_FOR_RESOLVER = "auditprocess";

    @Bean BeanFactoryAdapter auditprocessBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl auditprocessFlowStore(
            @Qualifier("auditprocessBeanFactoryAdapter") BeanFactoryAdapter auditprocessBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(auditprocessBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<AuditProcess> auditprocessEntityStm(@Qualifier("auditprocessFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<AuditProcess> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider auditprocessActionsInfoProvider(@Qualifier("auditprocessFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("auditprocess",provider);
        return provider;
	}
	
	@Bean EntityStore<AuditProcess> auditprocessEntityStore() {
		return new AuditProcessEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<AuditProcess> _auditprocessStateEntityService_(
			@Qualifier("auditprocessEntityStm") STM<AuditProcess> stm,
			@Qualifier("auditprocessActionsInfoProvider") STMActionsInfoProvider auditprocessInfoProvider,
			@Qualifier("auditprocessEntityStore") EntityStore<AuditProcess> entityStore){
		return new StateEntityServiceImpl<>(stm, auditprocessInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<AuditProcess> auditprocessEntryAction(@Qualifier("auditprocessEntityStore") EntityStore<AuditProcess> entityStore,
			@Qualifier("auditprocessActionsInfoProvider") STMActionsInfoProvider auditprocessInfoProvider,
            @Qualifier("auditprocessFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<AuditProcess> entryAction =  new GenericEntryAction<AuditProcess>(entityStore,auditprocessInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<AuditProcess> auditprocessExitAction(@Qualifier("auditprocessFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<AuditProcess> exitAction = new GenericExitAction<AuditProcess>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader auditprocessFlowReader(@Qualifier("auditprocessFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean AuditProcessHealthChecker auditprocessHealthChecker(){
    	return new AuditProcessHealthChecker();
    }

    @Bean STMTransitionAction<AuditProcess> defaultauditprocessSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver auditprocessTransitionActionResolver(
    @Qualifier("defaultauditprocessSTMTransitionAction") STMTransitionAction<AuditProcess> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector auditprocessBodyTypeSelector(
    @Qualifier("auditprocessActionsInfoProvider") STMActionsInfoProvider auditprocessInfoProvider,
    @Qualifier("auditprocessTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(auditprocessInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<AuditProcess> auditprocessBaseTransitionAction(
        @Qualifier("auditprocessTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "auditprocess" + eventId for the method name. (auditprocess is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/auditprocess/auditprocess-states.xml

    @Bean FailAuditProcessAction
            failAuditProcessAction(){
        return new FailAuditProcessAction();
    }
    @Bean CompleteAuditProcessAction
            completeAuditProcessAction(){
        return new CompleteAuditProcessAction();
    }
    @Bean StartAuditProcessAction
            startAuditProcessAction(){
        return new StartAuditProcessAction();
    }


}
