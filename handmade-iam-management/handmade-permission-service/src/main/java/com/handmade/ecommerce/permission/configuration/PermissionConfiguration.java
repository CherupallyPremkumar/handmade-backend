package com.handmade.ecommerce.permission.configuration;

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
import com.handmade.ecommerce.iam.model.Permission;
import com.handmade.ecommerce.permission.service.cmds.*;
import com.handmade.ecommerce.permission.service.healthcheck.PermissionHealthChecker;
import com.handmade.ecommerce.permission.service.store.PermissionEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PermissionConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/permission/permission-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Permission";
    public static final String PREFIX_FOR_RESOLVER = "permission";

    @Bean BeanFactoryAdapter permissionBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl permissionFlowStore(
            @Qualifier("permissionBeanFactoryAdapter") BeanFactoryAdapter permissionBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(permissionBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<Permission> permissionEntityStm(@Qualifier("permissionFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Permission> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider permissionActionsInfoProvider(@Qualifier("permissionFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("permission",provider);
        return provider;
	}
	
	@Bean EntityStore<Permission> permissionEntityStore() {
		return new PermissionEntityStore();
	}
	
	@Bean StateEntityServiceImpl<Permission> _permissionStateEntityService_(
			@Qualifier("permissionEntityStm") STM<Permission> stm,
			@Qualifier("permissionActionsInfoProvider") STMActionsInfoProvider permissionInfoProvider,
			@Qualifier("permissionEntityStore") EntityStore<Permission> entityStore){
		return new StateEntityServiceImpl<>(stm, permissionInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<Permission> permissionEntryAction(@Qualifier("permissionEntityStore") EntityStore<Permission> entityStore,
			@Qualifier("permissionActionsInfoProvider") STMActionsInfoProvider permissionInfoProvider,
            @Qualifier("permissionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Permission> entryAction =  new GenericEntryAction<Permission>(entityStore,permissionInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Permission> permissionExitAction(@Qualifier("permissionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Permission> exitAction = new GenericExitAction<Permission>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader permissionFlowReader(@Qualifier("permissionFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PermissionHealthChecker permissionHealthChecker(){
    	return new PermissionHealthChecker();
    }

    @Bean STMTransitionAction<Permission> defaultpermissionSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver permissionTransitionActionResolver(
    @Qualifier("defaultpermissionSTMTransitionAction") STMTransitionAction<Permission> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector permissionBodyTypeSelector(
    @Qualifier("permissionActionsInfoProvider") STMActionsInfoProvider permissionInfoProvider,
    @Qualifier("permissionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(permissionInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<Permission> permissionBaseTransitionAction(
        @Qualifier("permissionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "permission" + eventId for the method name. (permission is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/permission/permission-states.xml

    @Bean DeprecatePermissionAction
            permissionDeprecate(){
        return new DeprecatePermissionAction();
    }
    @Bean ActivatePermissionAction
            permissionActivate(){
        return new ActivatePermissionAction();
    }


}
