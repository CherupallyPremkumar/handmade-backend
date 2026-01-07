package com.handmade.ecommerce.seller.configuration;

import com.handmade.ecommerce.seller.infrastructure.persistence.SellerRepository;
import com.handmade.ecommerce.seller.service.cmds.*;
import com.handmade.ecommerce.seller.service.postSaveHooks.ACTIVESellerPostSaveHook;
import com.handmade.ecommerce.seller.service.postSaveHooks.CREATEDSellerPostSaveHook;
import com.handmade.ecommerce.seller.service.postSaveHooks.DELETEDSellerPostSaveHook;
import com.handmade.ecommerce.seller.service.postSaveHooks.SUSPENDEDSellerPostSaveHook;
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
import com.handmade.ecommerce.seller.domain.aggregate.Seller;
import com.handmade.ecommerce.seller.api.SellerService;
import com.handmade.ecommerce.seller.service.impl.SellerServiceImpl;

import com.handmade.ecommerce.seller.service.healthcheck.SellerHealthChecker;
import com.handmade.ecommerce.seller.service.store.SellerEntityStore;
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
public class SellerConfiguration {
    private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/seller/seller-store-states.xml";
    public static final String PREFIX_FOR_PROPERTIES = "SellerStore";
    public static final String PREFIX_FOR_RESOLVER = "sellerStore";

    @Bean
    BeanFactoryAdapter sellerBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    @Bean
    STMFlowStoreImpl sellerFlowStore(
            @Qualifier("sellerBeanFactoryAdapter") BeanFactoryAdapter sellerBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(sellerBeanFactoryAdapter);
        return stmFlowStore;
    }

    @Bean
    @Autowired
    STM<Seller> sellerEntityStm(@Qualifier("sellerFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<Seller> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider sellerActionsInfoProvider(@Qualifier("sellerFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("seller", provider);
        return provider;
    }

    @Bean
    @Autowired
    EntityStore<Seller> sellerEntityStore(SellerRepository sellerRepository) {
        return new SellerEntityStore(sellerRepository);
    }

    @Bean
    @Autowired
    SellerService _sellerService_(
            @Qualifier("sellerEntityStm") STM<Seller> stm,
            @Qualifier("sellerActionsInfoProvider") STMActionsInfoProvider sellerInfoProvider,
            @Qualifier("sellerEntityStore") EntityStore<Seller> entityStore,
            SellerRepository sellerRepository) {
        return new SellerServiceImpl(stm, sellerInfoProvider, entityStore, sellerRepository);
    }

    // Now we start constructing the STM Components

    @Bean
    @Autowired
    DefaultPostSaveHook<Seller> sellerDefaultPostSaveHook(
            @Qualifier("sellerTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        DefaultPostSaveHook<Seller> postSaveHook = new DefaultPostSaveHook<>(stmTransitionActionResolver);
        return postSaveHook;
    }

    @Bean
    @Autowired
    GenericEntryAction<Seller> sellerEntryAction(@Qualifier("sellerEntityStore") EntityStore<Seller> entityStore,
            @Qualifier("sellerActionsInfoProvider") STMActionsInfoProvider sellerInfoProvider,
            @Qualifier("sellerFlowStore") STMFlowStoreImpl stmFlowStore,
            @Qualifier("sellerDefaultPostSaveHook") DefaultPostSaveHook<Seller> postSaveHook) {
        GenericEntryAction<Seller> entryAction = new GenericEntryAction<Seller>(entityStore, sellerInfoProvider,
                postSaveHook);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
    }

    @Bean
    @Autowired
    DefaultAutomaticStateComputation<Seller> sellerDefaultAutoState(
            @Qualifier("sellerTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
            @Qualifier("sellerFlowStore") STMFlowStoreImpl stmFlowStore) {
        DefaultAutomaticStateComputation<Seller> autoState = new DefaultAutomaticStateComputation<>(
                stmTransitionActionResolver);
        stmFlowStore.setDefaultAutomaticStateComputation(autoState);
        return autoState;
    }

    @Bean
    GenericExitAction<Seller> sellerExitAction(@Qualifier("sellerFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericExitAction<Seller> exitAction = new GenericExitAction<Seller>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
    }

    @Bean
    XmlFlowReader sellerFlowReader(@Qualifier("sellerFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }

    @Bean
    SellerHealthChecker sellerHealthChecker() {
        return new SellerHealthChecker();
    }

    @Bean
    STMTransitionAction<Seller> defaultsellerSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver sellerTransitionActionResolver(
            @Qualifier("defaultsellerSTMTransitionAction") STMTransitionAction<Seller> defaultSTMTransitionAction) {
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, defaultSTMTransitionAction, true);
    }

    @Bean
    @Autowired
    StmBodyTypeSelector sellerBodyTypeSelector(
            @Qualifier("sellerActionsInfoProvider") STMActionsInfoProvider sellerInfoProvider,
            @Qualifier("sellerTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(sellerInfoProvider, stmTransitionActionResolver);
    }

    @Bean
    @Autowired
    STMTransitionAction<Seller> sellerBaseTransitionAction(
            @Qualifier("sellerTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
            @Qualifier("sellerActivityChecker") ActivityChecker activityChecker,
            @Qualifier("sellerFlowStore") STMFlowStoreImpl stmFlowStore) {
        BaseTransitionAction<Seller> baseTransitionAction = new BaseTransitionAction<>(stmTransitionActionResolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean
    ActivityChecker sellerActivityChecker(@Qualifier("sellerFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete _activitiesCompletionCheck_(
            @Qualifier("sellerActivityChecker") ActivityChecker activityChecker) {
        return new AreActivitiesComplete(activityChecker);
    }

    // Create the specific transition actions here. Make sure that these actions are
    // inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this).
    // To automatically wire
    // them into the STM use the convention of "seller" + eventId + "Action" for the
    // method name. (seller is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow
    // system.
    // The payload types will be detected as well so that there is no need to
    // introduce an <event-information/>
    // segment in src/main/resources/com/handmade/seller/seller-states.xml

    @Bean
    SuspendSellerAction sellerSuspendAction() {
        return new SuspendSellerAction();
    }

    @Bean
    ReactivateSellerAction sellerReactivateAction() {
        return new ReactivateSellerAction();
    }

    @Bean
    DeleteSellerAction sellerDeleteAction() {
        return new DeleteSellerAction();
    }

    @Bean
    TerminateSellerAction sellerTerminateAction() {
        return new TerminateSellerAction();
    }

    // --- Store Operations Actions ---

    // TODO: Add store-specific actions (Vacation mode, Listing pause)

    @Bean
    DeactivateSellerAction sellerDeactivateAction() {
        return new DeactivateSellerAction();
    }

    @Bean
    ConfigProviderImpl sellerConfigProvider() {
        return new ConfigProviderImpl();
    }

    @Bean
    ConfigBasedEnablementStrategy sellerConfigBasedEnablementStrategy(
            @Qualifier("sellerConfigProvider") ConfigProvider configProvider,
            @Qualifier("sellerFlowStore") STMFlowStoreImpl stmFlowStore) {
        ConfigBasedEnablementStrategy enablementStrategy = new ConfigBasedEnablementStrategy(configProvider,
                PREFIX_FOR_PROPERTIES);
        stmFlowStore.setEnablementStrategy(enablementStrategy);
        return enablementStrategy;
    }

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> sellerEventAuthoritiesSupplier(
            @Qualifier("sellerActionsInfoProvider") STMActionsInfoProvider sellerInfoProvider)
            throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(sellerInfoProvider);
        return builder.build();
    }

    @Bean
    CREATEDSellerPostSaveHook sellerCREATEDPostSaveHook() {
        return new CREATEDSellerPostSaveHook();
    }

    @Bean
    ACTIVESellerPostSaveHook sellerACTIVEPostSaveHook() {
        return new ACTIVESellerPostSaveHook();
    }

    @Bean
    SUSPENDEDSellerPostSaveHook sellerSUSPENDEDPostSaveHook() {
        return new SUSPENDEDSellerPostSaveHook();
    }

    @Bean
    DELETEDSellerPostSaveHook sellerDELETEDPostSaveHook() {
        return new DELETEDSellerPostSaveHook();
    }

}
