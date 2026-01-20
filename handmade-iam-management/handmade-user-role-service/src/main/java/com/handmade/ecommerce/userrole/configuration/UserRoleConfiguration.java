package com.handmade.ecommerce.userrole.configuration;

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
import com.handmade.ecommerce.iam.model.UserRole;
import com.handmade.ecommerce.userrole.service.cmds.*;
import com.handmade.ecommerce.userrole.service.healthcheck.UserRoleHealthChecker;
import com.handmade.ecommerce.userrole.service.store.UserRoleEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class UserRoleConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/userrole/userrole-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "UserRole";
    public static final String PREFIX_FOR_RESOLVER = "userrole";

    @Bean BeanFactoryAdapter userroleBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl userroleFlowStore(
            @Qualifier("userroleBeanFactoryAdapter") BeanFactoryAdapter userroleBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(userroleBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<UserRole> userroleEntityStm(@Qualifier("userroleFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<UserRole> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider userroleActionsInfoProvider(@Qualifier("userroleFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("userrole",provider);
        return provider;
	}
	
	@Bean EntityStore<UserRole> userroleEntityStore() {
		return new UserRoleEntityStore();
	}
	
	@Bean StateEntityServiceImpl<UserRole> _userroleStateEntityService_(
			@Qualifier("userroleEntityStm") STM<UserRole> stm,
			@Qualifier("userroleActionsInfoProvider") STMActionsInfoProvider userroleInfoProvider,
			@Qualifier("userroleEntityStore") EntityStore<UserRole> entityStore){
		return new StateEntityServiceImpl<>(stm, userroleInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<UserRole> userroleEntryAction(@Qualifier("userroleEntityStore") EntityStore<UserRole> entityStore,
			@Qualifier("userroleActionsInfoProvider") STMActionsInfoProvider userroleInfoProvider,
            @Qualifier("userroleFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<UserRole> entryAction =  new GenericEntryAction<UserRole>(entityStore,userroleInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<UserRole> userroleExitAction(@Qualifier("userroleFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<UserRole> exitAction = new GenericExitAction<UserRole>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader userroleFlowReader(@Qualifier("userroleFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean UserRoleHealthChecker userroleHealthChecker(){
    	return new UserRoleHealthChecker();
    }

    @Bean STMTransitionAction<UserRole> defaultuserroleSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver userroleTransitionActionResolver(
    @Qualifier("defaultuserroleSTMTransitionAction") STMTransitionAction<UserRole> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector userroleBodyTypeSelector(
    @Qualifier("userroleActionsInfoProvider") STMActionsInfoProvider userroleInfoProvider,
    @Qualifier("userroleTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(userroleInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<UserRole> userroleBaseTransitionAction(
        @Qualifier("userroleTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "userrole" + eventId for the method name. (userrole is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/userrole/userrole-states.xml

    @Bean SuspendUserRoleAction
            suspendUserRoleAction(){
        return new SuspendUserRoleAction();
    }
    @Bean RevokeUserRoleAction
            revokeUserRoleAction(){
        return new RevokeUserRoleAction();
    }
    @Bean ReactivateUserRoleAction
            reactivateUserRoleAction(){
        return new ReactivateUserRoleAction();
    }

    @Bean RejectUserRoleAction
            rejectUserRoleAction(){
        return new RejectUserRoleAction();
    }
    @Bean ActivateUserRoleAction
            activateUserRoleAction(){
        return new ActivateUserRoleAction();
    }


}
