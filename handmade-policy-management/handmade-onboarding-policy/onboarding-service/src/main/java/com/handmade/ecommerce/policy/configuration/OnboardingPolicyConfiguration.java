package com.handmade.ecommerce.policy.configuration;

import com.handmade.ecommerce.policy.api.OnboardingPolicyManager;
import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.service.health.OnboardingPolicyHealthChecker;
import com.handmade.ecommerce.policy.service.impl.OnboardingPolicyManagerImpl;
import com.handmade.ecommerce.policy.service.store.OnboardingPolicyEntityStore;
import com.handmade.ecommerce.policy.service.cmds.*;
import org.chenile.stm.*;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.impl.*;
import org.chenile.stm.spring.SpringBeanFactoryAdapter;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.param.MinimalPayload;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.activities.AreActivitiesComplete;
import org.chenile.workflow.service.stmcmds.*;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;
import org.chenile.core.context.ChenileExchange;

@Configuration
public class OnboardingPolicyConfiguration {
    private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/policy/onboarding-policy-states.xml";
    public static final String PREFIX_FOR_PROPERTIES = "OnboardingPolicy";
    public static final String PREFIX_FOR_RESOLVER = "onboardingPolicy";

    @Bean BeanFactoryAdapter onboardingPolicyBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }
    
    @Bean STMFlowStoreImpl onboardingPolicyFlowStore(
            @Qualifier("onboardingPolicyBeanFactoryAdapter") BeanFactoryAdapter beanFactoryAdapter
            ) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }
    
    @Bean @Autowired STM<OnboardingPolicy> onboardingPolicyEntityStm(
            @Qualifier("onboardingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<OnboardingPolicy> stm = new STMImpl<>();        
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }
    
    @Bean @Autowired STMActionsInfoProvider onboardingPolicyActionsInfoProvider(
            @Qualifier("onboardingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("onboardingPolicy", provider);
        return provider;
    }
    
    @Bean EntityStore<OnboardingPolicy> onboardingPolicyEntityStore() {
        return new OnboardingPolicyEntityStore();
    }
    
    @Bean @Autowired
    OnboardingPolicyManager _onboardingPolicyStateEntityService_(
            @Qualifier("onboardingPolicyEntityStm") STM<OnboardingPolicy> stm,
            @Qualifier("onboardingPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("onboardingPolicyEntityStore") EntityStore<OnboardingPolicy> entityStore) {
        return new OnboardingPolicyManagerImpl(stm, infoProvider, entityStore);
    }
    
    @Bean @Autowired DefaultPostSaveHook<OnboardingPolicy> onboardingPolicyDefaultPostSaveHook(
            @Qualifier("onboardingPolicyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new DefaultPostSaveHook<>(stmTransitionActionResolver);
    }

    @Bean @Autowired GenericEntryAction<OnboardingPolicy> onboardingPolicyEntryAction(
            @Qualifier("onboardingPolicyEntityStore") EntityStore<OnboardingPolicy> entityStore,
            @Qualifier("onboardingPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("onboardingPolicyDefaultPostSaveHook") DefaultPostSaveHook<OnboardingPolicy> postSaveHook,
            @Qualifier("onboardingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericEntryAction<OnboardingPolicy> entryAction = new GenericEntryAction<>(entityStore, infoProvider, postSaveHook);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
    }

    @Bean @Autowired DefaultAutomaticStateComputation<OnboardingPolicy> onboardingPolicyDefaultAutoState(
            @Qualifier("onboardingPolicyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
            @Qualifier("onboardingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        DefaultAutomaticStateComputation<OnboardingPolicy> autoState = new DefaultAutomaticStateComputation<>(stmTransitionActionResolver);
        stmFlowStore.setDefaultAutomaticStateComputation(autoState);
        return autoState;
    }

    @Bean GenericExitAction<OnboardingPolicy> onboardingPolicyExitAction(
            @Qualifier("onboardingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericExitAction<OnboardingPolicy> exitAction = new GenericExitAction<>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
    }

    @Bean
    XmlFlowReader onboardingPolicyFlowReader(@Qualifier("onboardingPolicyFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
    
    @Bean
    OnboardingPolicyHealthChecker onboardingPolicyHealthChecker() {
        return new OnboardingPolicyHealthChecker();
    }

    @Bean STMTransitionAction<OnboardingPolicy> defaultOnboardingPolicySTMTransitionAction() {
        return (policy, payload, startState, eventId, endState, stm, transition) -> {
            // Default action: do nothing but proceed
        };
    }

    @Bean
    STMTransitionActionResolver onboardingPolicyTransitionActionResolver(
            @Qualifier("defaultOnboardingPolicySTMTransitionAction") STMTransitionAction<OnboardingPolicy> defaultAction) {
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, defaultAction, true);
    }

    @Bean @Autowired StmBodyTypeSelector onboardingPolicyBodyTypeSelector(
            @Qualifier("onboardingPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("onboardingPolicyTransitionActionResolver") STMTransitionActionResolver resolver) {
        return new StmBodyTypeSelector(infoProvider, resolver);
    }

    // ========== TRANSITION ACTIONS (Commands) ==========

    @Bean ApproveOnboardingPolicyAction onboardingPolicyApproveAction() {
        return new ApproveOnboardingPolicyAction();
    }

    @Bean DeleteOnboardingPolicyAction onboardingPolicyDeleteAction() {
        return new DeleteOnboardingPolicyAction();
    }

    @Bean UpdateOnboardingPolicyAction onboardingPolicyUpdatePolicyAction() {
        return new UpdateOnboardingPolicyAction();
    }

    @Bean AddOnboardingPolicyRuleAction onboardingPolicyAddRuleAction() {
        return new AddOnboardingPolicyRuleAction();
    }

    @Bean UpdateOnboardingPolicyRuleAction onboardingPolicyUpdateRuleAction() {
        return new UpdateOnboardingPolicyRuleAction();
    }

    @Bean RemoveOnboardingPolicyRuleAction onboardingPolicyRemoveRuleAction() {
        return new RemoveOnboardingPolicyRuleAction();
    }

    @Bean DeprecateOnboardingPolicyAction onboardingPolicyDeprecateAction() {
        return new DeprecateOnboardingPolicyAction();
    }

    @Bean UpdateOnboardingPolicyDescriptionAction onboardingPolicyUpdateDescriptionAction() {
        return new UpdateOnboardingPolicyDescriptionAction();
    }

    @Bean SetOnboardingPolicyEffectiveToAction onboardingPolicySetEffectiveToAction() {
        return new SetOnboardingPolicyEffectiveToAction();
    }

    @Bean @Autowired STMTransitionAction<OnboardingPolicy> onboardingPolicyBaseTransitionAction(
            @Qualifier("onboardingPolicyTransitionActionResolver") STMTransitionActionResolver resolver,
            @Qualifier("onboardingPolicyActivityChecker") ActivityChecker activityChecker,
            @Qualifier("onboardingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        BaseTransitionAction<OnboardingPolicy> baseTransitionAction = new BaseTransitionAction<>(resolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker onboardingPolicyActivityChecker(@Qualifier("onboardingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete onboardingPolicyActivitiesCompletionCheck(@Qualifier("onboardingPolicyActivityChecker") ActivityChecker activityChecker) {
        return new AreActivitiesComplete(activityChecker);
    }

    @Bean @Autowired Function<ChenileExchange, String[]> onboardingPolicyEventAuthoritiesSupplier(
            @Qualifier("onboardingPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(infoProvider);
        return builder.build();
    }
}
