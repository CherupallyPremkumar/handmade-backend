package com.handmade.ecommerce.pricing.configuration;

import com.handmade.ecommerce.pricing.entity.Price;
import com.handmade.ecommerce.pricing.service.cmds.*;
import com.handmade.ecommerce.pricing.service.postSaveHooks.*;
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
import com.handmade.ecommerce.pricing.service.healthcheck.PriceHealthChecker;
import com.handmade.ecommerce.pricing.service.store.PriceEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.stmcmds.StmAuthoritiesBuilder;
import java.util.function.Function;
import org.chenile.core.context.ChenileExchange;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.activities.AreActivitiesComplete;

/**
 * This is where you will instantiate all the required classes in Spring
 */
@Configuration
public class PriceConfiguration {
    private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/price/price-states.xml";
    public static final String PREFIX_FOR_PROPERTIES = "Price";
    public static final String PREFIX_FOR_RESOLVER = "price";

    @Bean
    BeanFactoryAdapter priceBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    @Bean
    STMFlowStoreImpl priceFlowStore(
            @Qualifier("priceBeanFactoryAdapter") BeanFactoryAdapter priceBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(priceBeanFactoryAdapter);
        return stmFlowStore;
    }

    @Bean
    @Autowired
    STM<Price> priceEntityStm(@Qualifier("priceFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<Price> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider priceActionsInfoProvider(@Qualifier("priceFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("price", provider);
        return provider;
    }

    @Bean
    EntityStore<Price> priceEntityStore() {
        return new PriceEntityStore();
    }

    @Bean
    @Autowired
    StateEntityServiceImpl<Price> _priceStateEntityService_(
            @Qualifier("priceEntityStm") STM<Price> stm,
            @Qualifier("priceActionsInfoProvider") STMActionsInfoProvider priceInfoProvider,
            @Qualifier("priceEntityStore") EntityStore<Price> entityStore) {
        return new StateEntityServiceImpl<>(stm, priceInfoProvider, entityStore);
    }

    // Now we start constructing the STM Components

    @Bean
    @Autowired
    DefaultPostSaveHook<Price> priceDefaultPostSaveHook(
            @Qualifier("priceTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        DefaultPostSaveHook<Price> postSaveHook = new DefaultPostSaveHook<>(stmTransitionActionResolver);
        return postSaveHook;
    }

    @Bean
    @Autowired
    GenericEntryAction<Price> priceEntryAction(@Qualifier("priceEntityStore") EntityStore<Price> entityStore,
            @Qualifier("priceActionsInfoProvider") STMActionsInfoProvider priceInfoProvider,
            @Qualifier("priceFlowStore") STMFlowStoreImpl stmFlowStore,
            @Qualifier("priceDefaultPostSaveHook") DefaultPostSaveHook<Price> postSaveHook) {
        GenericEntryAction<Price> entryAction = new GenericEntryAction<Price>(entityStore, priceInfoProvider,
                postSaveHook);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
    }

    @Bean
    @Autowired
    DefaultAutomaticStateComputation<Price> priceDefaultAutoState(
            @Qualifier("priceTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
            @Qualifier("priceFlowStore") STMFlowStoreImpl stmFlowStore) {
        DefaultAutomaticStateComputation<Price> autoState = new DefaultAutomaticStateComputation<>(
                stmTransitionActionResolver);
        stmFlowStore.setDefaultAutomaticStateComputation(autoState);
        return autoState;
    }

    @Bean
    GenericExitAction<Price> priceExitAction(@Qualifier("priceFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericExitAction<Price> exitAction = new GenericExitAction<Price>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
    }

    @Bean
    XmlFlowReader priceFlowReader(@Qualifier("priceFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }

    @Bean
    PriceHealthChecker priceHealthChecker() {
        return new PriceHealthChecker();
    }

    @Bean
    STMTransitionAction<Price> defaultpriceSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver priceTransitionActionResolver(
            @Qualifier("defaultpriceSTMTransitionAction") STMTransitionAction<Price> defaultSTMTransitionAction) {
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, defaultSTMTransitionAction, true);
    }

    @Bean
    @Autowired
    StmBodyTypeSelector priceBodyTypeSelector(
            @Qualifier("priceActionsInfoProvider") STMActionsInfoProvider priceInfoProvider,
            @Qualifier("priceTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(priceInfoProvider, stmTransitionActionResolver);
    }

    @Bean
    @Autowired
    STMTransitionAction<Price> priceBaseTransitionAction(
            @Qualifier("priceTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
            @Qualifier("priceActivityChecker") ActivityChecker activityChecker,
            @Qualifier("priceFlowStore") STMFlowStoreImpl stmFlowStore) {
        BaseTransitionAction<Price> baseTransitionAction = new BaseTransitionAction<>(stmTransitionActionResolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean
    ActivityChecker priceActivityChecker(@Qualifier("priceFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete activitiesCompletionCheck(
            @Qualifier("priceActivityChecker") ActivityChecker activityChecker) {
        return new AreActivitiesComplete(activityChecker);
    }

    // Create the specific transition actions here. Make sure that these actions are
    // inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this).
    // To automatically wire
    // them into the STM use the convention of "price" + eventId + "Action" for the
    // method name. (price is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow
    // system.
    // The payload types will be detected as well so that there is no need to
    // introduce an <event-information/>
    // segment in src/main/resources/com/handmade/price/price-states.xml

    @Bean
    RemoveFromSalePriceAction priceRemoveFromSaleAction() {
        return new RemoveFromSalePriceAction();
    }

    @Bean
    PutOnSalePriceAction pricePutOnSaleAction() {
        return new PutOnSalePriceAction();
    }

    @Bean
    ExpirePriceAction priceExpireAction() {
        return new ExpirePriceAction();
    }

    @Bean
    ActivatePriceAction priceActivateAction() {
        return new ActivatePriceAction();
    }

    @Bean
    DeactivatePriceAction priceDeactivateAction() {
        return new DeactivatePriceAction();
    }

    @Bean
    ConfigProviderImpl priceConfigProvider() {
        return new ConfigProviderImpl();
    }

    @Bean
    ConfigBasedEnablementStrategy priceConfigBasedEnablementStrategy(
            @Qualifier("priceConfigProvider") ConfigProvider configProvider,
            @Qualifier("priceFlowStore") STMFlowStoreImpl stmFlowStore) {
        ConfigBasedEnablementStrategy enablementStrategy = new ConfigBasedEnablementStrategy(configProvider,
                PREFIX_FOR_PROPERTIES);
        stmFlowStore.setEnablementStrategy(enablementStrategy);
        return enablementStrategy;
    }

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> priceEventAuthoritiesSupplier(
            @Qualifier("priceActionsInfoProvider") STMActionsInfoProvider priceInfoProvider)
            throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(priceInfoProvider);
        return builder.build();
    }

    @Bean
    ON_SALEPricePostSaveHook priceON_SALEPostSaveHook() {
        return new ON_SALEPricePostSaveHook();
    }

    @Bean
    CREATEDPricePostSaveHook priceCREATEDPostSaveHook() {
        return new CREATEDPricePostSaveHook();
    }

    @Bean
    ACTIVEPricePostSaveHook priceACTIVEPostSaveHook() {
        return new ACTIVEPricePostSaveHook();
    }

    @Bean
    INACTIVEPricePostSaveHook priceINACTIVEPostSaveHook() {
        return new INACTIVEPricePostSaveHook();
    }

    @Bean
    EXPIREDPricePostSaveHook priceEXPIREDPostSaveHook() {
        return new EXPIREDPricePostSaveHook();
    }

}
