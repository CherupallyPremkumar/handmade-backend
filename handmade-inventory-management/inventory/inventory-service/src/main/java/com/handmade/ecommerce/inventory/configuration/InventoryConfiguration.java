package com.handmade.ecommerce.inventory.configuration;

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
import com.handmade.ecommerce.inventory.model.Inventory;
import com.handmade.ecommerce.inventory.service.cmds.*;
import com.handmade.ecommerce.inventory.service.healthcheck.InventoryHealthChecker;
import com.handmade.ecommerce.inventory.service.store.InventoryEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.stmcmds.StmAuthoritiesBuilder;
import java.util.function.Function;
import org.chenile.core.context.ChenileExchange;
import org.chenile.stm.State;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.activities.AreActivitiesComplete;
import com.handmade.ecommerce.inventory.service.postSaveHooks.*;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class InventoryConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/inventory/inventory-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Inventory";
    public static final String PREFIX_FOR_RESOLVER = "inventory";

    @Bean BeanFactoryAdapter inventoryBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl inventoryFlowStore(
            @Qualifier("inventoryBeanFactoryAdapter") BeanFactoryAdapter inventoryBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(inventoryBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Inventory> inventoryEntityStm(@Qualifier("inventoryFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Inventory> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider inventoryActionsInfoProvider(@Qualifier("inventoryFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("inventory",provider);
        return provider;
	}
	
	@Bean EntityStore<Inventory> inventoryEntityStore() {
		return new InventoryEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Inventory> _inventoryStateEntityService_(
			@Qualifier("inventoryEntityStm") STM<Inventory> stm,
			@Qualifier("inventoryActionsInfoProvider") STMActionsInfoProvider inventoryInfoProvider,
			@Qualifier("inventoryEntityStore") EntityStore<Inventory> entityStore){
		return new StateEntityServiceImpl<>(stm, inventoryInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 


    @Bean @Autowired DefaultPostSaveHook<Inventory> inventoryDefaultPostSaveHook(
    @Qualifier("inventoryTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
    DefaultPostSaveHook<Inventory> postSaveHook = new DefaultPostSaveHook<>(stmTransitionActionResolver);
    return postSaveHook;
    }

    @Bean @Autowired GenericEntryAction<Inventory> inventoryEntryAction(@Qualifier("inventoryEntityStore") EntityStore<Inventory> entityStore,
    @Qualifier("inventoryActionsInfoProvider") STMActionsInfoProvider inventoryInfoProvider,
    @Qualifier("inventoryFlowStore") STMFlowStoreImpl stmFlowStore,
    @Qualifier("inventoryDefaultPostSaveHook") DefaultPostSaveHook<Inventory> postSaveHook)  {
    GenericEntryAction<Inventory> entryAction =  new GenericEntryAction<Inventory>(entityStore,inventoryInfoProvider,postSaveHook);
    stmFlowStore.setEntryAction(entryAction);
    return entryAction;
    }

    @Bean @Autowired DefaultAutomaticStateComputation<Inventory> inventoryDefaultAutoState(
    @Qualifier("inventoryTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
    @Qualifier("inventoryFlowStore") STMFlowStoreImpl stmFlowStore){
    DefaultAutomaticStateComputation<Inventory> autoState = new DefaultAutomaticStateComputation<>(stmTransitionActionResolver);
    stmFlowStore.setDefaultAutomaticStateComputation(autoState);
    return autoState;
    }

	@Bean GenericExitAction<Inventory> inventoryExitAction(@Qualifier("inventoryFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Inventory> exitAction = new GenericExitAction<Inventory>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader inventoryFlowReader(@Qualifier("inventoryFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean InventoryHealthChecker inventoryHealthChecker(){
    	return new InventoryHealthChecker();
    }

    @Bean STMTransitionAction<Inventory> defaultinventorySTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver inventoryTransitionActionResolver(
    @Qualifier("defaultinventorySTMTransitionAction") STMTransitionAction<Inventory> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction,true);
    }

    @Bean @Autowired StmBodyTypeSelector inventoryBodyTypeSelector(
    @Qualifier("inventoryActionsInfoProvider") STMActionsInfoProvider inventoryInfoProvider,
    @Qualifier("inventoryTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(inventoryInfoProvider,stmTransitionActionResolver);
    }


    @Bean @Autowired STMTransitionAction<Inventory> inventoryBaseTransitionAction(
        @Qualifier("inventoryTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
        @Qualifier("inventoryActivityChecker") ActivityChecker activityChecker,
        @Qualifier("inventoryFlowStore") STMFlowStoreImpl stmFlowStore){
        BaseTransitionAction<Inventory> baseTransitionAction = new BaseTransitionAction<>(stmTransitionActionResolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker inventoryActivityChecker(@Qualifier("inventoryFlowStore") STMFlowStoreImpl stmFlowStore){
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete activitiesCompletionCheck(@Qualifier("inventoryActivityChecker") ActivityChecker activityChecker){
        return new AreActivitiesComplete(activityChecker);
    }

    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "inventory" + eventId + "Action" for the method name. (inventory is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/inventory/inventory-states.xml


    @Bean OutOfStockInventoryAction
            inventoryOutOfStockAction(){
        return new OutOfStockInventoryAction();
    }

    @Bean RestockInventoryAction
            inventoryRestockAction(){
        return new RestockInventoryAction();
    }

    @Bean LowStockInventoryAction
            inventoryLowStockAction(){
        return new LowStockInventoryAction();
    }

    @Bean StockInventoryAction
            inventoryStockAction(){
        return new StockInventoryAction();
    }


    @Bean ConfigProviderImpl inventoryConfigProvider() {
        return new ConfigProviderImpl();
    }

    @Bean ConfigBasedEnablementStrategy inventoryConfigBasedEnablementStrategy(
        @Qualifier("inventoryConfigProvider") ConfigProvider configProvider,
        @Qualifier("inventoryFlowStore") STMFlowStoreImpl stmFlowStore) {
        ConfigBasedEnablementStrategy enablementStrategy = new ConfigBasedEnablementStrategy(configProvider,PREFIX_FOR_PROPERTIES);
        stmFlowStore.setEnablementStrategy(enablementStrategy);
        return enablementStrategy;
    }


    @Bean @Autowired Function<ChenileExchange, String[]> inventoryEventAuthoritiesSupplier(
        @Qualifier("inventoryActionsInfoProvider") STMActionsInfoProvider inventoryInfoProvider)
                    throws Exception{
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(inventoryInfoProvider);
        return builder.build();
    }


    @Bean CREATEDInventoryPostSaveHook
        inventoryCREATEDPostSaveHook(){
            return new CREATEDInventoryPostSaveHook();
    }

    @Bean OUT_OF_STOCKInventoryPostSaveHook
        inventoryOUT_OF_STOCKPostSaveHook(){
            return new OUT_OF_STOCKInventoryPostSaveHook();
    }

    @Bean LOW_STOCKInventoryPostSaveHook
        inventoryLOW_STOCKPostSaveHook(){
            return new LOW_STOCKInventoryPostSaveHook();
    }

    @Bean IN_STOCKInventoryPostSaveHook
        inventoryIN_STOCKPostSaveHook(){
            return new IN_STOCKInventoryPostSaveHook();
    }

}
