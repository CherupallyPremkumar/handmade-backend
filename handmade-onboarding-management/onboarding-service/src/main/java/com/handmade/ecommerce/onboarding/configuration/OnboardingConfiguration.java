package com.handmade.ecommerce.onboarding.configuration;

import com.handmade.ecommerce.onboarding.api.OnboardingCaseService;
import com.handmade.ecommerce.onboarding.api.OnBoardingInternalCaseService;
import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import com.handmade.ecommerce.identity.api.IdentityVerificationService;
import com.handmade.ecommerce.onboarding.service.cmds.*;
import com.handmade.ecommerce.onboarding.service.impl.OnboardingCaseServiceImpl;
import com.handmade.ecommerce.onboarding.service.store.SellerOnboardingCaseStore;
import com.handmade.ecommerce.onboarding.service.store.SellerOnboardingCaseStoreImpl;
import org.chenile.stm.*;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.impl.*;
import org.chenile.stm.model.Transition;
import org.chenile.stm.spring.SpringBeanFactoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.stmcmds.*;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.activities.AreActivitiesComplete;

/**
 * Configuration for Onboarding Management (SellerAccount Lifecycle)
 */
@Configuration
public class OnboardingConfiguration {
    @Autowired
    private IdentityVerificationService identityVerificationService;
    private static final String ONBOARDING_FLOW_DEFINITION_FILE = "com/handmade/ecommerce/onboarding/onboarding-states.xml";
    public static final String PREFIX_FOR_PROPERTIES = "Onboarding";
    public static final String PREFIX_FOR_RESOLVER = "onboarding";

    @Bean
    BeanFactoryAdapter onboardingBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    @Bean
    STMFlowStoreImpl onboardingFlowStore(
            @Qualifier("onboardingBeanFactoryAdapter") BeanFactoryAdapter adapter) throws Exception {
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
    STMActionsInfoProvider onboardingActionsInfoProvider(
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("onboarding", provider);
        return provider;
    }

    @Bean
    SellerOnboardingCaseStore<SellerOnboardingCase> onboardingEntityStore() {
        return new SellerOnboardingCaseStoreImpl();
    }

    @Bean
    @Autowired
    OnboardingCaseService onboardingCaseService(
            @Qualifier("onboardingEntityStm") STM<SellerOnboardingCase> stm,
            @Qualifier("onboardingActionsInfoProvider") STMActionsInfoProvider provider,
            @Qualifier("onboardingEntityStore") SellerOnboardingCaseStore<SellerOnboardingCase> entityStore) {
        return new OnboardingCaseServiceImpl(stm, provider, entityStore, identityVerificationService);
    }

    @Bean
    @Autowired
    OnBoardingInternalCaseService onboardingInternalCaseService(
            @Qualifier("onboardingEntityStm") STM<SellerOnboardingCase> stm,
            @Qualifier("onboardingActionsInfoProvider") STMActionsInfoProvider provider,
            @Qualifier("onboardingEntityStore") SellerOnboardingCaseStore<SellerOnboardingCase> entityStore) {
        return new OnboardingCaseServiceImpl(stm, provider, entityStore, identityVerificationService);
    }

    @Bean
    @Autowired
    DefaultPostSaveHook<SellerOnboardingCase> onboardingDefaultPostSaveHook(
            @Qualifier("onboardingTransitionActionResolver") STMTransitionActionResolver resolver) {
        return new DefaultPostSaveHook<>(resolver);
    }

    @Bean
    @Autowired
    GenericEntryAction<SellerOnboardingCase> onboardingEntryAction(
            @Qualifier("onboardingEntityStore") EntityStore<SellerOnboardingCase> entityStore,
            @Qualifier("onboardingActionsInfoProvider") STMActionsInfoProvider provider,
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore,
            @Qualifier("onboardingDefaultPostSaveHook") DefaultPostSaveHook<SellerOnboardingCase> postSaveHook) {
        GenericEntryAction<SellerOnboardingCase> entryAction = new GenericEntryAction<>(entityStore, provider,
                postSaveHook);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
    }

    @Bean
    @Autowired
    DefaultAutomaticStateComputation<SellerOnboardingCase> onboardingDefaultAutoState(
            @Qualifier("onboardingTransitionActionResolver") STMTransitionActionResolver resolver,
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore) {
        DefaultAutomaticStateComputation<SellerOnboardingCase> autoState = new DefaultAutomaticStateComputation<>(
                resolver);
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
    XmlFlowReader onboardingFlowReader(
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(ONBOARDING_FLOW_DEFINITION_FILE);
        return flowReader;
    }

    @Bean
    STMTransitionAction<SellerOnboardingCase> defaultOnboardingSTMTransitionAction() {
        return new STMTransitionAction<SellerOnboardingCase>() {
            @Override
            public void doTransition(SellerOnboardingCase stateEntity, Object transitionParam, State startState,
                    String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition)
                    throws Exception {

            }
        };
    }

    @Bean
    STMTransitionActionResolver onboardingTransitionActionResolver(
            @Qualifier("defaultOnboardingSTMTransitionAction") STMTransitionAction<SellerOnboardingCase> defaultAction) {
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, defaultAction, true);
    }

    @Bean
    @Autowired
    StmBodyTypeSelector onboardingBodyTypeSelector(
            @Qualifier("onboardingActionsInfoProvider") STMActionsInfoProvider provider,
            @Qualifier("onboardingTransitionActionResolver") STMTransitionActionResolver resolver) {
        return new StmBodyTypeSelector(provider, resolver);
    }

    @Bean
    @Autowired
    STMTransitionAction<SellerOnboardingCase> onboardingBaseTransitionAction(
            @Qualifier("onboardingTransitionActionResolver") STMTransitionActionResolver resolver,
            @Qualifier("onboardingActivityChecker") ActivityChecker activityChecker,
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore) {
        BaseTransitionAction<SellerOnboardingCase> baseAction = new BaseTransitionAction<>(resolver);
        baseAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseAction);
        return baseAction;
    }

    @Bean
    ActivityChecker onboardingActivityChecker(
            @Qualifier("onboardingFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete onboardingActivitiesCompletionCheck(
            @Qualifier("onboardingActivityChecker") ActivityChecker activityChecker) {
        return new AreActivitiesComplete(activityChecker);
    }

    // --- Onboarding Specific Actions ---

    @Bean
    VerifyIdentityAction onboardingVerifyIdentityAction() {
        return new VerifyIdentityAction();
    }

    @Bean
    CompleteTaxInterviewAction onboardingCompleteTaxInterviewAction() {
        return new CompleteTaxInterviewAction();
    }

    @Bean
    VerifyBankAccountAction onboardingVerifyBankAccountAction() {
        return new VerifyBankAccountAction();
    }

    @Bean
    ApproveSellerAction onboardingApproveAction() {
        return new ApproveSellerAction();
    }

    @Bean
    RejectSellerAction onboardingRejectAction() {
        return new RejectSellerAction();
    }

    @Bean
    SubmitApplicationAction onboardingSubmitApplicationAction() {
        return new SubmitApplicationAction();
    }

    @Bean
    ProvisionSellerAction onboardingProvisionSellerAction() {
        return new ProvisionSellerAction();
    }
}
