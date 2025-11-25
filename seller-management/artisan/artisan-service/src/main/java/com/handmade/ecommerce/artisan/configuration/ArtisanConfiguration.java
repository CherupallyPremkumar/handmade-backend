package com.handmade.ecommerce.artisan.configuration;

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
import com.handmade.ecommerce.artisan.model.Artisan;
import com.handmade.ecommerce.artisan.service.cmds.*;
import com.handmade.ecommerce.artisan.service.healthcheck.ArtisanHealthChecker;
import com.handmade.ecommerce.artisan.service.store.ArtisanEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.stmcmds.StmAuthoritiesBuilder;
import java.util.function.Function;
import org.chenile.core.context.ChenileExchange;
import org.chenile.stm.State;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.activities.AreActivitiesComplete;
import com.handmade.ecommerce.artisan.service.postSaveHooks.*;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class ArtisanConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/artisan/artisan-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Artisan";
    public static final String PREFIX_FOR_RESOLVER = "artisan";

    @Bean BeanFactoryAdapter artisanBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl artisanFlowStore(
            @Qualifier("artisanBeanFactoryAdapter") BeanFactoryAdapter artisanBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(artisanBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Artisan> artisanEntityStm(@Qualifier("artisanFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Artisan> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider artisanActionsInfoProvider(@Qualifier("artisanFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("artisan",provider);
        return provider;
	}
	
	@Bean EntityStore<Artisan> artisanEntityStore() {
		return new ArtisanEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Artisan> _artisanStateEntityService_(
			@Qualifier("artisanEntityStm") STM<Artisan> stm,
			@Qualifier("artisanActionsInfoProvider") STMActionsInfoProvider artisanInfoProvider,
			@Qualifier("artisanEntityStore") EntityStore<Artisan> entityStore){
		return new StateEntityServiceImpl<>(stm, artisanInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 


    @Bean @Autowired DefaultPostSaveHook<Artisan> artisanDefaultPostSaveHook(
    @Qualifier("artisanTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
    DefaultPostSaveHook<Artisan> postSaveHook = new DefaultPostSaveHook<>(stmTransitionActionResolver);
    return postSaveHook;
    }

    @Bean @Autowired GenericEntryAction<Artisan> artisanEntryAction(@Qualifier("artisanEntityStore") EntityStore<Artisan> entityStore,
    @Qualifier("artisanActionsInfoProvider") STMActionsInfoProvider artisanInfoProvider,
    @Qualifier("artisanFlowStore") STMFlowStoreImpl stmFlowStore,
    @Qualifier("artisanDefaultPostSaveHook") DefaultPostSaveHook<Artisan> postSaveHook)  {
    GenericEntryAction<Artisan> entryAction =  new GenericEntryAction<Artisan>(entityStore,artisanInfoProvider,postSaveHook);
    stmFlowStore.setEntryAction(entryAction);
    return entryAction;
    }

    @Bean @Autowired DefaultAutomaticStateComputation<Artisan> artisanDefaultAutoState(
    @Qualifier("artisanTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
    @Qualifier("artisanFlowStore") STMFlowStoreImpl stmFlowStore){
    DefaultAutomaticStateComputation<Artisan> autoState = new DefaultAutomaticStateComputation<>(stmTransitionActionResolver);
    stmFlowStore.setDefaultAutomaticStateComputation(autoState);
    return autoState;
    }

	@Bean GenericExitAction<Artisan> artisanExitAction(@Qualifier("artisanFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Artisan> exitAction = new GenericExitAction<Artisan>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader artisanFlowReader(@Qualifier("artisanFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean ArtisanHealthChecker artisanHealthChecker(){
    	return new ArtisanHealthChecker();
    }

    @Bean STMTransitionAction<Artisan> defaultartisanSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver artisanTransitionActionResolver(
    @Qualifier("defaultartisanSTMTransitionAction") STMTransitionAction<Artisan> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction,true);
    }

    @Bean @Autowired StmBodyTypeSelector artisanBodyTypeSelector(
    @Qualifier("artisanActionsInfoProvider") STMActionsInfoProvider artisanInfoProvider,
    @Qualifier("artisanTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(artisanInfoProvider,stmTransitionActionResolver);
    }


    @Bean @Autowired STMTransitionAction<Artisan> artisanBaseTransitionAction(
        @Qualifier("artisanTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
        @Qualifier("artisanActivityChecker") ActivityChecker activityChecker,
        @Qualifier("artisanFlowStore") STMFlowStoreImpl stmFlowStore){
        BaseTransitionAction<Artisan> baseTransitionAction = new BaseTransitionAction<>(stmTransitionActionResolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker artisanActivityChecker(@Qualifier("artisanFlowStore") STMFlowStoreImpl stmFlowStore){
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete activitiesCompletionCheck(@Qualifier("artisanActivityChecker") ActivityChecker activityChecker){
        return new AreActivitiesComplete(activityChecker);
    }

    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "artisan" + eventId + "Action" for the method name. (artisan is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/artisan/artisan-states.xml


    @Bean SuspendArtisanAction
            artisanSuspendAction(){
        return new SuspendArtisanAction();
    }

    @Bean ActivateArtisanAction
            artisanActivateAction(){
        return new ActivateArtisanAction();
    }

    @Bean DeactivateArtisanAction
            artisanDeactivateAction(){
        return new DeactivateArtisanAction();
    }


    @Bean ConfigProviderImpl artisanConfigProvider() {
        return new ConfigProviderImpl();
    }

    @Bean ConfigBasedEnablementStrategy artisanConfigBasedEnablementStrategy(
        @Qualifier("artisanConfigProvider") ConfigProvider configProvider,
        @Qualifier("artisanFlowStore") STMFlowStoreImpl stmFlowStore) {
        ConfigBasedEnablementStrategy enablementStrategy = new ConfigBasedEnablementStrategy(configProvider,PREFIX_FOR_PROPERTIES);
        stmFlowStore.setEnablementStrategy(enablementStrategy);
        return enablementStrategy;
    }


    @Bean @Autowired Function<ChenileExchange, String[]> artisanEventAuthoritiesSupplier(
        @Qualifier("artisanActionsInfoProvider") STMActionsInfoProvider artisanInfoProvider)
                    throws Exception{
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(artisanInfoProvider);
        return builder.build();
    }


    @Bean CREATEDArtisanPostSaveHook
        artisanCREATEDPostSaveHook(){
            return new CREATEDArtisanPostSaveHook();
    }

    @Bean ACTIVEArtisanPostSaveHook
        artisanACTIVEPostSaveHook(){
            return new ACTIVEArtisanPostSaveHook();
    }

    @Bean INACTIVEArtisanPostSaveHook
        artisanINACTIVEPostSaveHook(){
            return new INACTIVEArtisanPostSaveHook();
    }

    @Bean SUSPENDEDArtisanPostSaveHook
        artisanSUSPENDEDPostSaveHook(){
            return new SUSPENDEDArtisanPostSaveHook();
    }

}
