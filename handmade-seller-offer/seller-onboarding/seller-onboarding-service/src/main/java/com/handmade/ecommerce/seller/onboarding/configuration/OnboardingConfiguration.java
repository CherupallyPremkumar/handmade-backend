package com.handmade.ecommerce.seller.onboarding.configuration;

import com.handmade.ecommerce.seller.onboarding.SellerOnboardingService;
import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingCase;
import com.handmade.ecommerce.seller.onboarding.repository.SellerOnboardingStepRepository;

import org.chenile.stm.STM;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.impl.*;
import org.chenile.stm.spring.SpringBeanFactoryAdapter;
import org.chenile.workflow.param.MinimalPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.stmcmds.*;
import com.handmade.ecommerce.seller.onboarding.service.actions.*;
import com.handmade.ecommerce.seller.onboarding.service.commands.*;
import com.handmade.ecommerce.seller.onboarding.service.health.SellerOnboardingHealthChecker;
import com.handmade.ecommerce.seller.onboarding.service.impl.SellerOnboardingServiceImpl;
import com.handmade.ecommerce.seller.onboarding.store.SellerOnboardingStoreImpl;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.stmcmds.StmAuthoritiesBuilder;

import java.util.function.Function;
import org.chenile.core.context.ChenileExchange;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.activities.AreActivitiesComplete;
import org.chenile.stm.impl.BeanFactoryAdapter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.chenile.owiz.config.impl.XmlOrchConfigurator;
import org.chenile.owiz.impl.OrchExecutorImpl;
import org.chenile.owiz.OrchExecutor;

/**
 * Onboarding Configuration aligned with Platform pattern
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.handmade.ecommerce.seller.onboarding.repository")
@EntityScan(basePackages = "com.handmade.ecommerce.seller.onboarding.entity")
public class OnboardingConfiguration {
    private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/seller.onboarding/stm/onboarding-states.xml";
    private static final String ONBOARDING_OWIZ_FILE = "com/handmade/ecommerce/seller.onboarding/owiz/seller-onboarding-flow-owiz.xml";
    public static final String PREFIX_FOR_PROPERTIES = "Onboarding";
    public static final String PREFIX_FOR_RESOLVER = "onboarding";
    private static final String ONBOARDING_INIT_FLOW = "ONBOARDING_INIT_FLOW";
    private static final String ONBOARDING_RESUME_FLOW = "ONBOARDING_RESUME_FLOW";
    private static final String ONBOARDING_CONFIRM_FLOW = "ONBOARDING_CONFIRM_FLOW";

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    BeanFactoryAdapter onboardingStmBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    @Bean
    STMFlowStoreImpl onboardingFlowStore(
            @Qualifier("onboardingStmBeanFactoryAdapter") BeanFactoryAdapter adapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(adapter);
        return stmFlowStore;
    }

    @Bean
    @Autowired
    STM<SellerOnboardingCase> onboardingEntityStm(@Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore)
            throws Exception {
        STMImpl<SellerOnboardingCase> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider onboardingActionsInfoProvider(
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider(PREFIX_FOR_RESOLVER, provider);
        return provider;
    }

    @Bean
    public EntityStore<SellerOnboardingCase> onboardingEntityStore() {
        return new SellerOnboardingStoreImpl();
    }

    @Bean
    @Autowired
    SellerOnboardingService _sellerOnboardingService_(
            @Qualifier("onboardingEntityStm") STM<SellerOnboardingCase> stm,
            @Qualifier("onboardingActionsInfoProvider") STMActionsInfoProvider onboardingInfoProvider,
            @Qualifier("onboardingEntityStore") EntityStore<SellerOnboardingCase> entityStore,
            @Qualifier("onboardingResumeExecutor") OrchExecutor<OnboardingResumeContext> orchExecutor,
            @Qualifier("onboardingStepRepository") SellerOnboardingStepRepository stepRepository) {
        return new SellerOnboardingServiceImpl(stm, onboardingInfoProvider, entityStore, orchExecutor, stepRepository);
    }

    // Chenile STM Components

    @Bean
    @Autowired
    DefaultPostSaveHook<SellerOnboardingCase> onboardingDefaultPostSaveHook(
            @Qualifier("onboardingTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new DefaultPostSaveHook<>(stmTransitionActionResolver);
    }

    @Bean
    @Autowired
    GenericEntryAction<SellerOnboardingCase> onboardingEntryAction(
            @Qualifier("onboardingEntityStore") EntityStore<SellerOnboardingCase> entityStore,
            @Qualifier("onboardingActionsInfoProvider") STMActionsInfoProvider onboardingActionsInfoProvider,
            @Qualifier("onboardingDefaultPostSaveHook") DefaultPostSaveHook<SellerOnboardingCase> postSaveHook,
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericEntryAction<SellerOnboardingCase> entryAction = new GenericEntryAction<>(entityStore,
                onboardingActionsInfoProvider, postSaveHook);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
    }

    @Bean
    @Autowired
    DefaultAutomaticStateComputation<SellerOnboardingCase> onboardingDefaultAutoState(
            @Qualifier("onboardingTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore) {
        DefaultAutomaticStateComputation<SellerOnboardingCase> autoState = new DefaultAutomaticStateComputation<>(
                stmTransitionActionResolver);
        stmFlowStore.setDefaultAutomaticStateComputation(autoState);
        return autoState;
    }

    @Bean
    GenericExitAction<SellerOnboardingCase> onboardingExitAction(
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericExitAction<SellerOnboardingCase> exitAction = new GenericExitAction<>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
    }

    @Bean
    XmlFlowReader onboardingFlowReader(@Qualifier("onboardingFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }

    @Bean
    SellerOnboardingHealthChecker onboardingHealthChecker() {
        return new SellerOnboardingHealthChecker();
    }

    @Bean
    STMTransitionAction<SellerOnboardingCase> defaultOnboardingSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver onboardingTransitionActionResolver(
            @Qualifier("defaultOnboardingSTMTransitionAction") STMTransitionAction<SellerOnboardingCase> defaultSTMTransitionAction) {
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, defaultSTMTransitionAction, true);
    }

    @Bean
    @Autowired
    StmBodyTypeSelector onboardingBodyTypeSelector(
            @Qualifier("onboardingActionsInfoProvider") STMActionsInfoProvider onboardingInfoProvider,
            @Qualifier("onboardingTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(onboardingInfoProvider, stmTransitionActionResolver);
    }

    @Bean
    @Autowired
    STMTransitionAction<SellerOnboardingCase> onboardingBaseTransitionAction(
            @Qualifier("onboardingTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
            @Qualifier("onboardingActivityChecker") ActivityChecker activityChecker,
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore) {
        BaseTransitionAction<SellerOnboardingCase> baseTransitionAction = new BaseTransitionAction<>(
                stmTransitionActionResolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean
    ActivityChecker onboardingActivityChecker(@Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete onboardingActivitiesCompletionCheck(
            @Qualifier("onboardingActivityChecker") ActivityChecker activityChecker) {
        return new AreActivitiesComplete(activityChecker);
    }

    // Transition Actions - Naming convention: prefix + eventId + Action

    @Bean(name = { "onboardingStartOnboardingAction", "startOnboardingAction" })
    public StartOnboardingAction startOnboardingAction() {
        return new StartOnboardingAction();
    }

    @Bean(name = { "onboardingIdentityVerifiedAction", "identityVerifiedAction" })
    public IdentityVerifiedAction identityVerifiedAction() {
        return new IdentityVerifiedAction();
    }

    @Bean(name = { "onboardingConfirmOnboardingAction", "confirmOnboardingAction" })
    public ConfirmOnboardingAction confirmOnboardingAction() {
        return new ConfirmOnboardingAction();
    }

    @Bean(name = { "onboardingRejectAction", "rejectOnboardingAction" })
    public RejectOnboardingAction rejectOnboardingAction() {
        return new RejectOnboardingAction();
    }

    @Bean(name = { "onboardingSubmitDocsAction", "submitDocsAction" })
    public SubmitDocsAction submitDocsAction() {
        return new SubmitDocsAction();
    }

    // OWIZ Components
    @Bean
    @Scope("prototype")
    public org.chenile.owiz.impl.Chain<Object> owizChain() {
        return new org.chenile.owiz.impl.Chain<>();
    }

    @Bean
    @Scope("prototype")
    public org.chenile.owiz.impl.ParallelChain<Object> owizParallelChain() {
        return new org.chenile.owiz.impl.ParallelChain<>();
    }

    @Bean
    public org.chenile.owiz.BeanFactoryAdapter onboardingOwizBeanFactoryAdapter() {
        return new org.chenile.owiz.BeanFactoryAdapter() {
            @Override
            public Object lookup(String componentName) {
                if (componentName == null)
                    return null;
                return applicationContext.getBean(componentName);
            }
        };
    }

    @Bean
    public XmlOrchConfigurator<OnboardingInitContext> onboardingInitConfigurator(
            @Qualifier("onboardingOwizBeanFactoryAdapter") org.chenile.owiz.BeanFactoryAdapter adapter)
            throws Exception {
        XmlOrchConfigurator<OnboardingInitContext> configurator = new XmlOrchConfigurator<>();
        configurator.setBeanFactoryAdapter(adapter);
        configurator.setFilename(ONBOARDING_OWIZ_FILE);
        return configurator;
    }

    @Bean
    public XmlOrchConfigurator<OnboardingResumeContext> onboardingResumeConfigurator(
            @Qualifier("onboardingOwizBeanFactoryAdapter") org.chenile.owiz.BeanFactoryAdapter adapter)
            throws Exception {
        XmlOrchConfigurator<OnboardingResumeContext> configurator = new XmlOrchConfigurator<>();
        configurator.setBeanFactoryAdapter(adapter);
        configurator.setFilename(ONBOARDING_OWIZ_FILE);
        return configurator;
    }

    @Bean
    public XmlOrchConfigurator<OnboardingConfirmContext> onboardingConfirmConfigurator(
            @Qualifier("onboardingOwizBeanFactoryAdapter") org.chenile.owiz.BeanFactoryAdapter adapter)
            throws Exception {
        XmlOrchConfigurator<OnboardingConfirmContext> configurator = new XmlOrchConfigurator<>();
        configurator.setBeanFactoryAdapter(adapter);
        configurator.setFilename(ONBOARDING_OWIZ_FILE);
        return configurator;
    }

    @Bean
    public OrchExecutor<OnboardingConfirmContext> onboardingConfirmExecutor(
            @Qualifier("onboardingConfirmConfigurator") XmlOrchConfigurator<OnboardingConfirmContext> configurator) {
        OrchExecutorImpl<OnboardingConfirmContext> executor = new OrchExecutorImpl<>() {
            @Override
            public void execute(OnboardingConfirmContext context) throws Exception {
                execute(ONBOARDING_CONFIRM_FLOW, context);
            }
        };
        executor.setOrchConfigurator(configurator);
        return executor;
    }

    @Bean
    public OrchExecutor<OnboardingInitContext> onboardingInitExecutor(
            @Qualifier("onboardingInitConfigurator") XmlOrchConfigurator<OnboardingInitContext> configurator) {
        OrchExecutorImpl<OnboardingInitContext> executor = new OrchExecutorImpl<>() {
            @Override
            public void execute(OnboardingInitContext context) throws Exception {
                execute(ONBOARDING_INIT_FLOW, context);
            }
        };
        executor.setOrchConfigurator(configurator);
        return executor;
    }

    @Bean
    public OrchExecutor<OnboardingResumeContext> onboardingResumeExecutor(
            @Qualifier("onboardingResumeConfigurator") XmlOrchConfigurator<OnboardingResumeContext> configurator) {
        OrchExecutorImpl<OnboardingResumeContext> executor = new OrchExecutorImpl<>() {
            @Override
            public void execute(OnboardingResumeContext context) throws Exception {
                execute(ONBOARDING_RESUME_FLOW, context);
            }
        };
        executor.setOrchConfigurator(configurator);
        return executor;
    }

    @Bean
    public UpdateOnboardingStateCommand updateOnboardingStateCommand() {
        return new UpdateOnboardingStateCommand();
    }

    @Bean
    public CreateSellerAccountCommand createSellerAccountCommand() {
        return new CreateSellerAccountCommand();
    }

    @Bean
    public VerifyKycCommand verifyKycCommand() {
        return new VerifyKycCommand();
    }

    @Bean
    public SendWelcomeEmailCommand sendWelcomeEmailCommand() {
        return new SendWelcomeEmailCommand();
    }

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> onboardingEventAuthoritiesSupplier(
            @Qualifier("onboardingActionsInfoProvider") STMActionsInfoProvider platformInfoProvider)
            throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(platformInfoProvider);
        return builder.build();
    }
}
