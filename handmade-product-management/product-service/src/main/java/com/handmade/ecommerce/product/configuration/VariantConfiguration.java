package com.handmade.ecommerce.product.configuration;

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
import com.handmade.ecommerce.product.domain.model.Variant;
import com.handmade.ecommerce.product.service.cmds.*;
import com.handmade.ecommerce.product.service.healthcheck.VariantHealthChecker;
import com.handmade.ecommerce.product.service.store.VariantEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.stmcmds.StmAuthoritiesBuilder;
import java.util.function.Function;
import org.chenile.core.context.ChenileExchange;
import org.chenile.stm.State;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.activities.AreActivitiesComplete;
import com.handmade.ecommerce.product.service.postSaveHooks.*;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class VariantConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/variant/variant-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Variant";
    public static final String PREFIX_FOR_RESOLVER = "variant";

    @Bean BeanFactoryAdapter variantBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl variantFlowStore(
            @Qualifier("variantBeanFactoryAdapter") BeanFactoryAdapter variantBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(variantBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Variant> variantEntityStm(@Qualifier("variantFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Variant> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider variantActionsInfoProvider(@Qualifier("variantFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("variant",provider);
        return provider;
	}
	
	@Bean EntityStore<Variant> variantEntityStore() {
		return new VariantEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Variant> _variantStateEntityService_(
			@Qualifier("variantEntityStm") STM<Variant> stm,
			@Qualifier("variantActionsInfoProvider") STMActionsInfoProvider variantInfoProvider,
			@Qualifier("variantEntityStore") EntityStore<Variant> entityStore){
		return new StateEntityServiceImpl<>(stm, variantInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 


    @Bean @Autowired DefaultPostSaveHook<Variant> variantDefaultPostSaveHook(
    @Qualifier("variantTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
    DefaultPostSaveHook<Variant> postSaveHook = new DefaultPostSaveHook<>(stmTransitionActionResolver);
    return postSaveHook;
    }

    @Bean @Autowired GenericEntryAction<Variant> variantEntryAction(@Qualifier("variantEntityStore") EntityStore<Variant> entityStore,
    @Qualifier("variantActionsInfoProvider") STMActionsInfoProvider variantInfoProvider,
    @Qualifier("variantFlowStore") STMFlowStoreImpl stmFlowStore,
    @Qualifier("variantDefaultPostSaveHook") DefaultPostSaveHook<Variant> postSaveHook)  {
    GenericEntryAction<Variant> entryAction =  new GenericEntryAction<Variant>(entityStore,variantInfoProvider,postSaveHook);
    stmFlowStore.setEntryAction(entryAction);
    return entryAction;
    }

    @Bean @Autowired DefaultAutomaticStateComputation<Variant> variantDefaultAutoState(
    @Qualifier("variantTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
    @Qualifier("variantFlowStore") STMFlowStoreImpl stmFlowStore){
    DefaultAutomaticStateComputation<Variant> autoState = new DefaultAutomaticStateComputation<>(stmTransitionActionResolver);
    stmFlowStore.setDefaultAutomaticStateComputation(autoState);
    return autoState;
    }

	@Bean GenericExitAction<Variant> variantExitAction(@Qualifier("variantFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Variant> exitAction = new GenericExitAction<Variant>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader variantFlowReader(@Qualifier("variantFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean VariantHealthChecker variantHealthChecker(){
    	return new VariantHealthChecker();
    }

    @Bean STMTransitionAction<Variant> defaultvariantSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver variantTransitionActionResolver(
    @Qualifier("defaultvariantSTMTransitionAction") STMTransitionAction<Variant> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction,true);
    }

    @Bean @Autowired StmBodyTypeSelector variantBodyTypeSelector(
    @Qualifier("variantActionsInfoProvider") STMActionsInfoProvider variantInfoProvider,
    @Qualifier("variantTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(variantInfoProvider,stmTransitionActionResolver);
    }


    @Bean @Autowired STMTransitionAction<Variant> variantBaseTransitionAction(
        @Qualifier("variantTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
        @Qualifier("variantActivityChecker") ActivityChecker activityChecker,
        @Qualifier("variantFlowStore") STMFlowStoreImpl stmFlowStore){
        BaseTransitionAction<Variant> baseTransitionAction = new BaseTransitionAction<>(stmTransitionActionResolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker variantActivityChecker(@Qualifier("variantFlowStore") STMFlowStoreImpl stmFlowStore){
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete activitiesCompletionCheck(@Qualifier("variantActivityChecker") ActivityChecker activityChecker){
        return new AreActivitiesComplete(activityChecker);
    }

    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "variant" + eventId + "Action" for the method name. (variant is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/variant/variant-states.xml


    @Bean DiscontinueVariantAction
            variantDiscontinueAction(){
        return new DiscontinueVariantAction();
    }

    @Bean ActivateVariantAction
            variantActivateAction(){
        return new ActivateVariantAction();
    }

    @Bean AddPriceVariantAction
            variantAddPriceAction(){
        return new AddPriceVariantAction();
    }

    @Bean DeactivateVariantAction
            variantDeactivateAction(){
        return new DeactivateVariantAction();
    }


    @Bean ConfigProviderImpl variantConfigProvider() {
        return new ConfigProviderImpl();
    }

    @Bean ConfigBasedEnablementStrategy variantConfigBasedEnablementStrategy(
        @Qualifier("variantConfigProvider") ConfigProvider configProvider,
        @Qualifier("variantFlowStore") STMFlowStoreImpl stmFlowStore) {
        ConfigBasedEnablementStrategy enablementStrategy = new ConfigBasedEnablementStrategy(configProvider,PREFIX_FOR_PROPERTIES);
        stmFlowStore.setEnablementStrategy(enablementStrategy);
        return enablementStrategy;
    }


    @Bean @Autowired Function<ChenileExchange, String[]> variantEventAuthoritiesSupplier(
        @Qualifier("variantActionsInfoProvider") STMActionsInfoProvider variantInfoProvider)
                    throws Exception{
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(variantInfoProvider);
        return builder.build();
    }


    @Bean ACTIVEVariantPostSaveHook
        variantACTIVEPostSaveHook(){
            return new ACTIVEVariantPostSaveHook();
    }

    @Bean DRAFTVariantPostSaveHook
        variantDRAFTPostSaveHook(){
            return new DRAFTVariantPostSaveHook();
    }

    @Bean INACTIVEVariantPostSaveHook
        variantINACTIVEPostSaveHook(){
            return new INACTIVEVariantPostSaveHook();
    }

    @Bean DISCONTINUEDVariantPostSaveHook
        variantDISCONTINUEDPostSaveHook(){
            return new DISCONTINUEDVariantPostSaveHook();
    }

}
