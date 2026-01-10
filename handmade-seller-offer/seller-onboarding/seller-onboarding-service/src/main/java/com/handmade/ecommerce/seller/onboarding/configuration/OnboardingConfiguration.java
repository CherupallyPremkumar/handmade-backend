package com.handmade.ecommerce.seller.onboarding.configuration;

import com.handmade.ecommerce.seller.onboarding.api.SellerOnboardingService;
import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingCase;
import org.chenile.stm.STM;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.impl.*;
import org.chenile.stm.impl.BeanFactoryAdapter;
import org.chenile.stm.spring.SpringBeanFactoryAdapter;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.api.WorkflowRegistry;
import com.handmade.ecommerce.seller.onboarding.service.health.SellerOnboardingHealthChecker;
import com.handmade.ecommerce.seller.onboarding.service.impl.SellerOnboardingServiceImpl;
import com.handmade.ecommerce.seller.onboarding.store.SellerOnboardingStoreImpl;
import org.chenile.owiz.OrchExecutor;
import org.chenile.owiz.config.impl.XmlOrchConfigurator;
import org.chenile.owiz.impl.OrchExecutorImpl;
import com.handmade.ecommerce.seller.onboarding.service.actions.*;
import com.handmade.ecommerce.seller.onboarding.service.commands.*;
import org.chenile.workflow.param.MinimalPayload;
import org.chenile.workflow.service.stmcmds.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Onboarding Configuration
 * Manages the beans for Seller Onboarding State Machine and Service.
 */
@Configuration
public class OnboardingConfiguration {

    private static final String ONBOARDING_FLOW_DEFINITION_FILE = "stm/onboarding-states.xml";
    private static final String ONBOARDING_OWIZ_FILE = "owiz/seller-onboarding-flow-owiz.xml";
    public static final String PREFIX_FOR_RESOLVER = "";

    @Autowired
    private org.springframework.context.ApplicationContext applicationContext;

    @Bean
    public org.chenile.stm.impl.BeanFactoryAdapter onboardingStmBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    @Bean
    public org.chenile.owiz.BeanFactoryAdapter onboardingOwizBeanFactoryAdapter() {
        return new org.chenile.owiz.BeanFactoryAdapter() {
            @Override
            public Object lookup(String componentName) {
                return applicationContext.getBean(componentName);
            }
        };
    }

    @Bean
    @Autowired
    StmBodyTypeSelector sellerOnboardingBodyTypeSelector(
            @Qualifier("onboardingActionsInfoProvider") STMActionsInfoProvider onboardingInfoProvider,
            @Qualifier("onboardingTransitionActionResolver") STMTransitionActionResolver onboardingTransitionActionResolver) {
        return new StmBodyTypeSelector(onboardingInfoProvider, onboardingTransitionActionResolver);
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
    STM<SellerOnboardingCase> onboardingEntityStm(
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<SellerOnboardingCase> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    SellerOnboardingService _sellerOnboardingService_(
            @Qualifier("onboardingEntityStm") STM<SellerOnboardingCase> stm,
            @Qualifier("onboardingActionsInfoProvider") STMActionsInfoProvider provider,
            @Qualifier("onboardingEntityStore") EntityStore<SellerOnboardingCase> entityStore) {
        return new SellerOnboardingServiceImpl(stm, provider, entityStore);
    }

    @Bean
    XmlFlowReader onboardingFlowReader(
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(ONBOARDING_FLOW_DEFINITION_FILE);
        return flowReader;
    }

    @Bean
    @Autowired
    GenericEntryAction<SellerOnboardingCase> onboardingEntryAction(
            @Qualifier("onboardingEntityStore") EntityStore<SellerOnboardingCase> entityStore,
            @Qualifier("onboardingActionsInfoProvider") STMActionsInfoProvider provider,
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericEntryAction<SellerOnboardingCase> entryAction = new GenericEntryAction<>(entityStore, provider, null);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
    }

    @Bean
    EntityStore<SellerOnboardingCase> onboardingEntityStore() {
        return new SellerOnboardingStoreImpl();
    }

    @Bean
    @Autowired
    STMActionsInfoProvider onboardingActionsInfoProvider(
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("onboarding", provider);
        return provider;
    }

    @Bean
    GenericExitAction<SellerOnboardingCase> onboardingExitAction() {
        return new GenericExitAction<SellerOnboardingCase>();
    }

    @Bean
    SellerOnboardingHealthChecker sellerOnboardingHealthChecker() {
        return new SellerOnboardingHealthChecker();
    }

    @Bean
    @Autowired
    DefaultSTMTransitionAction<MinimalPayload> defaultOnboardingTransitionAction() {
        return new DefaultSTMTransitionAction<>();
    }

    @Bean
    @Autowired
    STMTransitionActionResolver onboardingTransitionActionResolver(
            @Qualifier("defaultOnboardingTransitionAction") STMTransitionAction<SellerOnboardingCase> defaultSTMTransitionAction) {
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, defaultSTMTransitionAction);
    }

    @Bean
    @Autowired
    STMTransitionAction<SellerOnboardingCase> sellerOnboardingSTMTransitionAction(
            @Qualifier("onboardingTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new BaseTransitionAction<>(stmTransitionActionResolver);
    }

    // Onboarding Actions - Named to match eventId + "Action" convention exactly
    @Bean
    public StartOnboardingAction startOnboardingAction() {
        return new StartOnboardingAction();
    }

    @Bean
    public IdentityVerifiedAction identityVerifiedAction() {
        return new IdentityVerifiedAction();
    }

    @Bean
    public ConfirmOnboardingAction confirmOnboardingAction() {
        return new ConfirmOnboardingAction();
    }

    @Bean
    public RejectOnboardingAction rejectOnboardingAction() {
        return new RejectOnboardingAction();
    }

    @Bean
    public UpdateOnboardingStateCommand updateOnboardingStateCommand() {
        return new UpdateOnboardingStateCommand();
    }

    @Bean
    public CreateSellerAccountCommand createSellerAccountCommand() {
        return new CreateSellerAccountCommand();
    }

    // OWIZ Orchestration Commands
    @Bean
    public VerifyKycCommand verifyKycCommand() {
        return new VerifyKycCommand();
    }

    @Bean
    public SendWelcomeEmailCommand sendWelcomeEmailCommand() {
        return new SendWelcomeEmailCommand();
    }

    // OWIZ Infrastructure - Specialized Configurators
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
    public OrchExecutor<OnboardingInitContext> onboardingInitExecutor(
            @Qualifier("onboardingInitConfigurator") XmlOrchConfigurator<OnboardingInitContext> configurator) {
        return new OrchExecutor<OnboardingInitContext>() {
            @Override
            public void execute(OnboardingInitContext context) throws Exception {
                execute("ONBOARDING_INIT_FLOW", context);
            }

            @Override
            public void execute(String flowId, OnboardingInitContext context) throws Exception {
                OrchExecutorImpl<OnboardingInitContext> executor = new OrchExecutorImpl<>();
                executor.setOrchConfigurator(configurator);
                executor.execute(flowId, context);
            }
        };
    }

    @Bean
    public OrchExecutor<OnboardingResumeContext> onboardingResumeExecutor(
            @Qualifier("onboardingResumeConfigurator") XmlOrchConfigurator<OnboardingResumeContext> configurator) {
        return new OrchExecutor<OnboardingResumeContext>() {
            @Override
            public void execute(OnboardingResumeContext context) throws Exception {
                execute("ONBOARDING_RESUME_FLOW", context);
            }

            @Override
            public void execute(String flowId, OnboardingResumeContext context) throws Exception {
                OrchExecutorImpl<OnboardingResumeContext> executor = new OrchExecutorImpl<>();
                executor.setOrchConfigurator(configurator);
                executor.execute(flowId, context);
            }
        };
    }

    @Bean
    public OrchExecutor<OnboardingConfirmContext> onboardingConfirmExecutor(
            @Qualifier("onboardingConfirmConfigurator") XmlOrchConfigurator<OnboardingConfirmContext> configurator) {
        return new OrchExecutor<OnboardingConfirmContext>() {
            @Override
            public void execute(OnboardingConfirmContext context) throws Exception {
                execute("ONBOARDING_CONFIRM_FLOW", context);
            }

            @Override
            public void execute(String flowId, OnboardingConfirmContext context) throws Exception {
                OrchExecutorImpl<OnboardingConfirmContext> executor = new OrchExecutorImpl<>();
                executor.setOrchConfigurator(configurator);
                executor.execute(flowId, context);
            }
        };
    }
}
