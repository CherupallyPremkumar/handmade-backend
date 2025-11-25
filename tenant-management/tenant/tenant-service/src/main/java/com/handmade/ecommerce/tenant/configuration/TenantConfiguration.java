package com.handmade.ecommerce.tenant.configuration;


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
import com.handmade.ecommerce.tenant.model.Tenant;
import com.handmade.ecommerce.tenant.service.cmds.*;
import com.handmade.ecommerce.tenant.service.healthcheck.TenantHealthChecker;
import com.handmade.ecommerce.tenant.service.store.TenantEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.stmcmds.StmAuthoritiesBuilder;

import java.util.HashMap;
import java.util.function.Function;
import org.chenile.core.context.ChenileExchange;
import org.chenile.stm.State;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.activities.AreActivitiesComplete;
import com.handmade.ecommerce.tenant.service.postSaveHooks.*;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class TenantConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/tenant/tenant-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Tenant";
    public static final String PREFIX_FOR_RESOLVER = "tenant";



    @Bean BeanFactoryAdapter tenantBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl tenantFlowStore(
            @Qualifier("tenantBeanFactoryAdapter") BeanFactoryAdapter tenantBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(tenantBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Tenant> tenantEntityStm(@Qualifier("tenantFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Tenant> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider tenantActionsInfoProvider(@Qualifier("tenantFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("tenant",provider);
        return provider;
	}
	
	@Bean EntityStore<Tenant> tenantEntityStore() {
		return new TenantEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Tenant> _tenantStateEntityService_(
			@Qualifier("tenantEntityStm") STM<Tenant> stm,
			@Qualifier("tenantActionsInfoProvider") STMActionsInfoProvider tenantInfoProvider,
			@Qualifier("tenantEntityStore") EntityStore<Tenant> entityStore){
		return new StateEntityServiceImpl<>(stm, tenantInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 


    @Bean @Autowired DefaultPostSaveHook<Tenant> tenantDefaultPostSaveHook(
    @Qualifier("tenantTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
    DefaultPostSaveHook<Tenant> postSaveHook = new DefaultPostSaveHook<>(stmTransitionActionResolver);
    return postSaveHook;
    }

    @Bean @Autowired GenericEntryAction<Tenant> tenantEntryAction(@Qualifier("tenantEntityStore") EntityStore<Tenant> entityStore,
    @Qualifier("tenantActionsInfoProvider") STMActionsInfoProvider tenantInfoProvider,
    @Qualifier("tenantFlowStore") STMFlowStoreImpl stmFlowStore,
    @Qualifier("tenantDefaultPostSaveHook") DefaultPostSaveHook<Tenant> postSaveHook)  {
    GenericEntryAction<Tenant> entryAction =  new GenericEntryAction<Tenant>(entityStore,tenantInfoProvider,postSaveHook);
    stmFlowStore.setEntryAction(entryAction);
    return entryAction;
    }

    @Bean @Autowired DefaultAutomaticStateComputation<Tenant> tenantDefaultAutoState(
    @Qualifier("tenantTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
    @Qualifier("tenantFlowStore") STMFlowStoreImpl stmFlowStore){
    DefaultAutomaticStateComputation<Tenant> autoState = new DefaultAutomaticStateComputation<>(stmTransitionActionResolver);
    stmFlowStore.setDefaultAutomaticStateComputation(autoState);
    return autoState;
    }

	@Bean GenericExitAction<Tenant> tenantExitAction(@Qualifier("tenantFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Tenant> exitAction = new GenericExitAction<Tenant>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader tenantFlowReader(@Qualifier("tenantFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean TenantHealthChecker tenantHealthChecker(){
    	return new TenantHealthChecker();
    }

    @Bean STMTransitionAction<Tenant> defaulttenantSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver tenantTransitionActionResolver(
    @Qualifier("defaulttenantSTMTransitionAction") STMTransitionAction<Tenant> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction,true);
    }

    @Bean @Autowired StmBodyTypeSelector tenantBodyTypeSelector(
    @Qualifier("tenantActionsInfoProvider") STMActionsInfoProvider tenantInfoProvider,
    @Qualifier("tenantTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(tenantInfoProvider,stmTransitionActionResolver);
    }


    @Bean @Autowired STMTransitionAction<Tenant> tenantBaseTransitionAction(
        @Qualifier("tenantTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
        @Qualifier("tenantActivityChecker") ActivityChecker activityChecker,
        @Qualifier("tenantFlowStore") STMFlowStoreImpl stmFlowStore){
        BaseTransitionAction<Tenant> baseTransitionAction = new BaseTransitionAction<>(stmTransitionActionResolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker tenantActivityChecker(@Qualifier("tenantFlowStore") STMFlowStoreImpl stmFlowStore){
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete activitiesCompletionCheck(@Qualifier("tenantActivityChecker") ActivityChecker activityChecker){
        return new AreActivitiesComplete(activityChecker);
    }

    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "tenant" + eventId + "Action" for the method name. (tenant is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/tenant/tenant-states.xml


    @Bean SuspendTenantAction
            tenantSuspendAction(){
        return new SuspendTenantAction();
    }

    @Bean ReactivateTenantAction
            tenantReactivateAction(){
        return new ReactivateTenantAction();
    }

    @Bean ActivateTenantAction
            tenantActivateAction(){
        return new ActivateTenantAction();
    }

    @Bean DeleteTenantAction
            tenantDeleteAction(){
        return new DeleteTenantAction();
    }


    @Bean ConfigProviderImpl tenantConfigProvider() {
        return new ConfigProviderImpl();
    }

    @Bean ConfigBasedEnablementStrategy tenantConfigBasedEnablementStrategy(
        @Qualifier("tenantConfigProvider") ConfigProvider configProvider,
        @Qualifier("tenantFlowStore") STMFlowStoreImpl stmFlowStore) {
        ConfigBasedEnablementStrategy enablementStrategy = new ConfigBasedEnablementStrategy(configProvider,PREFIX_FOR_PROPERTIES);
        stmFlowStore.setEnablementStrategy(enablementStrategy);
        return enablementStrategy;
    }


    @Bean @Autowired Function<ChenileExchange, String[]> tenantEventAuthoritiesSupplier(
        @Qualifier("tenantActionsInfoProvider") STMActionsInfoProvider tenantInfoProvider)
                    throws Exception{
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(tenantInfoProvider);
        return builder.build();
    }


    @Bean CREATEDTenantPostSaveHook
        tenantCREATEDPostSaveHook(){
            return new CREATEDTenantPostSaveHook();
    }

    @Bean ACTIVETenantPostSaveHook
        tenantACTIVEPostSaveHook(){
            return new ACTIVETenantPostSaveHook();
    }

    @Bean SUSPENDEDTenantPostSaveHook
        tenantSUSPENDEDPostSaveHook(){
            return new SUSPENDEDTenantPostSaveHook();
    }

    @Bean DELETEDTenantPostSaveHook
        tenantDELETEDPostSaveHook(){
            return new DELETEDTenantPostSaveHook();
    }

}
