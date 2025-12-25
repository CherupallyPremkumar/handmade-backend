package com.handmade.ecommerce.platform.configuration;


import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.service.cmds.DefaultSTMTransitionAction;
import com.handmade.ecommerce.platform.service.health.PlatformHealthChecker;
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
import com.handmade.ecommerce.platform.service.cmds.*;
import com.handmade.ecommerce.platform.service.store.PlatformEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.stmcmds.StmAuthoritiesBuilder;

import java.util.function.Function;
import org.chenile.core.context.ChenileExchange;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.activities.AreActivitiesComplete;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PlatformConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/platform/platform-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Platform";
    public static final String PREFIX_FOR_RESOLVER = "platform";



    @Bean BeanFactoryAdapter platformBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl platformFlowStore(
            @Qualifier("platformBeanFactoryAdapter") BeanFactoryAdapter platformBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(platformBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<PlatformOwner> platformEntityStm(@Qualifier("platformFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<PlatformOwner> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider platformActionsInfoProvider(@Qualifier("platformFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("platform",provider);
        return provider;
	}
	
	@Bean EntityStore<PlatformOwner> platformEntityStore() {
		return new PlatformEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<PlatformOwner> _platformStateEntityService_(
			@Qualifier("platformEntityStm") STM<PlatformOwner> stm,
			@Qualifier("platformActionsInfoProvider") STMActionsInfoProvider platformInfoProvider,
			@Qualifier("platformEntityStore") EntityStore<PlatformOwner> entityStore){
		return new StateEntityServiceImpl<>(stm, platformInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 


    @Bean @Autowired DefaultPostSaveHook<PlatformOwner> platformDefaultPostSaveHook(
    @Qualifier("platformTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
    DefaultPostSaveHook<PlatformOwner> postSaveHook = new DefaultPostSaveHook<>(stmTransitionActionResolver);
    return postSaveHook;
    }



    @Bean @Autowired DefaultAutomaticStateComputation<PlatformOwner> platformDefaultAutoState(
    @Qualifier("platformTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
    @Qualifier("platformFlowStore") STMFlowStoreImpl stmFlowStore){
    DefaultAutomaticStateComputation<PlatformOwner> autoState = new DefaultAutomaticStateComputation<>(stmTransitionActionResolver);
    stmFlowStore.setDefaultAutomaticStateComputation(autoState);
    return autoState;
    }

	@Bean GenericExitAction<PlatformOwner> platformExitAction(@Qualifier("platformFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<PlatformOwner> exitAction = new GenericExitAction<PlatformOwner>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader platformFlowReader(@Qualifier("platformFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean
    PlatformHealthChecker platformHealthChecker(){
    	return new PlatformHealthChecker();
    }

    @Bean STMTransitionAction<PlatformOwner> defaultplatformSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver platformTransitionActionResolver(
    @Qualifier("defaultplatformSTMTransitionAction") STMTransitionAction<PlatformOwner> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction,true);
    }

    @Bean @Autowired StmBodyTypeSelector platformBodyTypeSelector(
    @Qualifier("platformActionsInfoProvider") STMActionsInfoProvider platformInfoProvider,
    @Qualifier("platformTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(platformInfoProvider,stmTransitionActionResolver);
    }


    @Bean @Autowired STMTransitionAction<PlatformOwner> platformBaseTransitionAction(
        @Qualifier("platformTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
        @Qualifier("platformActivityChecker") ActivityChecker activityChecker,
        @Qualifier("platformFlowStore") STMFlowStoreImpl stmFlowStore){
        BaseTransitionAction<PlatformOwner> baseTransitionAction = new BaseTransitionAction<>(stmTransitionActionResolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker platformActivityChecker(@Qualifier("platformFlowStore") STMFlowStoreImpl stmFlowStore){
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete activitiesCompletionCheck(@Qualifier("platformActivityChecker") ActivityChecker activityChecker){
        return new AreActivitiesComplete(activityChecker);
    }

    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "platform" + eventId + "Action" for the method name. (platform is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/platform/platform-states.xml


    @Bean SuspendPlatformAction
            platformSuspendAction(){
        return new SuspendPlatformAction();
    }

    @Bean ReactivatePlatformAction
            platformReactivateAction(){
        return new ReactivatePlatformAction();
    }

    @Bean ActivatePlatformAction
            platformActivateAction(){
        return new ActivatePlatformAction();
    }

    @Bean DeletePlatformAction
            platformDeleteAction(){
        return new DeletePlatformAction();
    }


    @Bean ConfigProviderImpl platformConfigProvider() {
        return new ConfigProviderImpl();
    }

    @Bean ConfigBasedEnablementStrategy platformConfigBasedEnablementStrategy(
        @Qualifier("platformConfigProvider") ConfigProvider configProvider,
        @Qualifier("platformFlowStore") STMFlowStoreImpl stmFlowStore) {
        ConfigBasedEnablementStrategy enablementStrategy = new ConfigBasedEnablementStrategy(configProvider,PREFIX_FOR_PROPERTIES);
        stmFlowStore.setEnablementStrategy(enablementStrategy);
        return enablementStrategy;
    }


    @Bean @Autowired Function<ChenileExchange, String[]> platformEventAuthoritiesSupplier(
        @Qualifier("platformActionsInfoProvider") STMActionsInfoProvider platformInfoProvider)
                    throws Exception{
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(platformInfoProvider);
        return builder.build();
    }


}
