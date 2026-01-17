package com.handmade.ecommerce.role.configuration;

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
import com.handmade.ecommerce.iam.model.Role;
import com.handmade.ecommerce.role.service.cmds.*;
import com.handmade.ecommerce.role.service.healthcheck.RoleHealthChecker;
import com.handmade.ecommerce.role.service.store.RoleEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class RoleConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/role/role-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Role";
    public static final String PREFIX_FOR_RESOLVER = "role";

    @Bean BeanFactoryAdapter roleBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl roleFlowStore(
            @Qualifier("roleBeanFactoryAdapter") BeanFactoryAdapter roleBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(roleBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Role> roleEntityStm(@Qualifier("roleFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Role> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider roleActionsInfoProvider(@Qualifier("roleFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("role",provider);
        return provider;
	}
	
	@Bean EntityStore<Role> roleEntityStore() {
		return new RoleEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Role> _roleStateEntityService_(
			@Qualifier("roleEntityStm") STM<Role> stm,
			@Qualifier("roleActionsInfoProvider") STMActionsInfoProvider roleInfoProvider,
			@Qualifier("roleEntityStore") EntityStore<Role> entityStore){
		return new StateEntityServiceImpl<>(stm, roleInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Role> roleEntryAction(@Qualifier("roleEntityStore") EntityStore<Role> entityStore,
			@Qualifier("roleActionsInfoProvider") STMActionsInfoProvider roleInfoProvider,
            @Qualifier("roleFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Role> entryAction =  new GenericEntryAction<Role>(entityStore,roleInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Role> roleExitAction(@Qualifier("roleFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Role> exitAction = new GenericExitAction<Role>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader roleFlowReader(@Qualifier("roleFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean RoleHealthChecker roleHealthChecker(){
    	return new RoleHealthChecker();
    }

    @Bean STMTransitionAction<Role> defaultroleSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver roleTransitionActionResolver(
    @Qualifier("defaultroleSTMTransitionAction") STMTransitionAction<Role> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector roleBodyTypeSelector(
    @Qualifier("roleActionsInfoProvider") STMActionsInfoProvider roleInfoProvider,
    @Qualifier("roleTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(roleInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Role> roleBaseTransitionAction(
        @Qualifier("roleTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "role" + eventId for the method name. (role is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/role/role-states.xml

    @Bean DeprecateRoleAction
            roleDeprecate(){
        return new DeprecateRoleAction();
    }
    @Bean ActivateRoleAction
            roleActivate(){
        return new ActivateRoleAction();
    }


}
