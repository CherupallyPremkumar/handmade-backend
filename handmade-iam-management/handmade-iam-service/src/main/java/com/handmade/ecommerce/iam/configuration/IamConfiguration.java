package com.handmade.ecommerce.iam.configuration;

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
import com.handmade.ecommerce.iam.model.Iam;
import com.handmade.ecommerce.iam.service.cmds.*;
import com.handmade.ecommerce.iam.service.healthcheck.IamHealthChecker;
import com.handmade.ecommerce.iam.service.store.IamEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class IamConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/iam/iam-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Iam";
    public static final String PREFIX_FOR_RESOLVER = "iam";

    @Bean BeanFactoryAdapter iamBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl iamFlowStore(
            @Qualifier("iamBeanFactoryAdapter") BeanFactoryAdapter iamBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(iamBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Iam> iamEntityStm(@Qualifier("iamFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Iam> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider iamActionsInfoProvider(@Qualifier("iamFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("iam",provider);
        return provider;
	}
	
	@Bean EntityStore<Iam> iamEntityStore() {
		return new IamEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Iam> _iamStateEntityService_(
			@Qualifier("iamEntityStm") STM<Iam> stm,
			@Qualifier("iamActionsInfoProvider") STMActionsInfoProvider iamInfoProvider,
			@Qualifier("iamEntityStore") EntityStore<Iam> entityStore){
		return new StateEntityServiceImpl<>(stm, iamInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Iam> iamEntryAction(@Qualifier("iamEntityStore") EntityStore<Iam> entityStore,
			@Qualifier("iamActionsInfoProvider") STMActionsInfoProvider iamInfoProvider,
            @Qualifier("iamFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Iam> entryAction =  new GenericEntryAction<Iam>(entityStore,iamInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Iam> iamExitAction(@Qualifier("iamFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Iam> exitAction = new GenericExitAction<Iam>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader iamFlowReader(@Qualifier("iamFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean IamHealthChecker iamHealthChecker(){
    	return new IamHealthChecker();
    }

    @Bean STMTransitionAction<Iam> defaultiamSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver iamTransitionActionResolver(
    @Qualifier("defaultiamSTMTransitionAction") STMTransitionAction<Iam> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector iamBodyTypeSelector(
    @Qualifier("iamActionsInfoProvider") STMActionsInfoProvider iamInfoProvider,
    @Qualifier("iamTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(iamInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Iam> iamBaseTransitionAction(
        @Qualifier("iamTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "iam" + eventId for the method name. (iam is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/iam/iam-states.xml

    @Bean SuspendRoleIamAction
            iamSuspendRole(){
        return new SuspendRoleIamAction();
    }
    @Bean DeactivateRoleIamAction
            iamDeactivateRole(){
        return new DeactivateRoleIamAction();
    }
    @Bean ActivateRoleIamAction
            iamActivateRole(){
        return new ActivateRoleIamAction();
    }
    @Bean DeactivateRoleIamAction
            iamDeactivateRole(){
        return new DeactivateRoleIamAction();
    }
    @Bean ReactivateRoleIamAction
            iamReactivateRole(){
        return new ReactivateRoleIamAction();
    }
    @Bean DeactivateRoleIamAction
            iamDeactivateRole(){
        return new DeactivateRoleIamAction();
    }


}
