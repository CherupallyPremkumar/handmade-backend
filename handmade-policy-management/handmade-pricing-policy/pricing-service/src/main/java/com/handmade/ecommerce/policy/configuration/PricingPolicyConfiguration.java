package com.handmade.ecommerce.policy.configuration;

import com.handmade.ecommerce.policy.api.PricingPolicyManager;
import com.handmade.ecommerce.policy.domain.aggregate.PricingPolicy;
import com.handmade.ecommerce.policy.service.health.PricingPolicyHealthChecker;
import com.handmade.ecommerce.policy.service.impl.PricingPolicyManagerImpl;
import com.handmade.ecommerce.policy.service.store.PricingPolicyEntityStore;
import com.handmade.ecommerce.policy.service.cmds.*;
import org.chenile.stm.*;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.impl.*;
import org.chenile.stm.spring.SpringBeanFactoryAdapter;
import org.chenile.workflow.api.WorkflowRegistry;
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
public class PricingPolicyConfiguration {
    private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/policy/pricing-policy-states.xml";
    public static final String PREFIX_FOR_PROPERTIES = "PricingPolicy";
    public static final String PREFIX_FOR_RESOLVER = "pricingPolicy";

    @Bean BeanFactoryAdapter pricingPolicyBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }
    
    @Bean STMFlowStoreImpl pricingPolicyFlowStore(
            @Qualifier("pricingPolicyBeanFactoryAdapter") BeanFactoryAdapter beanFactoryAdapter
            ) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }
    
    @Bean @Autowired STM<PricingPolicy> pricingPolicyEntityStm(
            @Qualifier("pricingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<PricingPolicy> stm = new STMImpl<>();        
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }
    
    @Bean @Autowired STMActionsInfoProvider pricingPolicyActionsInfoProvider(
            @Qualifier("pricingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("pricingPolicy", provider);
        return provider;
    }
    
    @Bean @Autowired
    PricingPolicyManager _pricingPolicyStateEntityService_(
            @Qualifier("pricingPolicyEntityStm") STM<PricingPolicy> stm,
            @Qualifier("pricingPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("pricingPolicyEntityStore") EntityStore<PricingPolicy> entityStore) {
        return new PricingPolicyManagerImpl(stm, infoProvider, entityStore);
    }
    
    @Bean @Autowired DefaultPostSaveHook<PricingPolicy> pricingPolicyDefaultPostSaveHook(
            @Qualifier("pricingPolicyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new DefaultPostSaveHook<>(stmTransitionActionResolver);
    }

    @Bean @Autowired GenericEntryAction<PricingPolicy> pricingPolicyEntryAction(
            @Qualifier("pricingPolicyEntityStore") EntityStore<PricingPolicy> entityStore,
            @Qualifier("pricingPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("pricingPolicyDefaultPostSaveHook") DefaultPostSaveHook<PricingPolicy> postSaveHook,
            @Qualifier("pricingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericEntryAction<PricingPolicy> entryAction = new GenericEntryAction<>(entityStore, infoProvider, postSaveHook);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
    }

    @Bean @Autowired DefaultAutomaticStateComputation<PricingPolicy> pricingPolicyDefaultAutoState(
            @Qualifier("pricingPolicyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
            @Qualifier("pricingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        DefaultAutomaticStateComputation<PricingPolicy> autoState = new DefaultAutomaticStateComputation<>(stmTransitionActionResolver);
        stmFlowStore.setDefaultAutomaticStateComputation(autoState);
        return autoState;
    }

    @Bean GenericExitAction<PricingPolicy> pricingPolicyExitAction(
            @Qualifier("pricingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericExitAction<PricingPolicy> exitAction = new GenericExitAction<>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
    }

    @Bean
    XmlFlowReader pricingPolicyFlowReader(@Qualifier("pricingPolicyFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
    
    @Bean
    PricingPolicyHealthChecker pricingPolicyHealthChecker() {
        return new PricingPolicyHealthChecker();
    }

    @Bean STMTransitionAction<PricingPolicy> defaultPricingPolicySTMTransitionAction() {
        return (policy, payload, startState, eventId, endState, stm, transition) -> {
            // Default action: do nothing but proceed
        };
    }

    @Bean
    STMTransitionActionResolver pricingPolicyTransitionActionResolver(
            @Qualifier("defaultPricingPolicySTMTransitionAction") STMTransitionAction<PricingPolicy> defaultAction) {
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, defaultAction, true);
    }

    @Bean @Autowired StmBodyTypeSelector pricingPolicyBodyTypeSelector(
            @Qualifier("pricingPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("pricingPolicyTransitionActionResolver") STMTransitionActionResolver resolver) {
        return new StmBodyTypeSelector(infoProvider, resolver);
    }

    // ========== TRANSITION ACTIONS (Commands) ==========

    @Bean ApprovePricingPolicyAction pricingPolicyApproveAction() {
        return new ApprovePricingPolicyAction();
    }

    @Bean DeletePricingPolicyAction pricingPolicyDeleteAction() {
        return new DeletePricingPolicyAction();
    }

    @Bean DeprecatePricingPolicyAction pricingPolicyDeprecateAction() {
        return new DeprecatePricingPolicyAction();
    }

    @Bean AddPricingRuleAction pricingPolicyAddRuleAction() {
        return new AddPricingRuleAction();
    }

    @Bean UpdatePricingRuleAction pricingPolicyUpdateRuleAction() {
        return new UpdatePricingRuleAction();
    }

    @Bean RemovePricingRuleAction pricingPolicyRemoveRuleAction() {
        return new RemovePricingRuleAction();
    }

    @Bean @Autowired STMTransitionAction<PricingPolicy> pricingPolicyBaseTransitionAction(
            @Qualifier("pricingPolicyTransitionActionResolver") STMTransitionActionResolver resolver,
            @Qualifier("pricingPolicyActivityChecker") ActivityChecker activityChecker,
            @Qualifier("pricingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        BaseTransitionAction<PricingPolicy> baseTransitionAction = new BaseTransitionAction<>(resolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker pricingPolicyActivityChecker(@Qualifier("pricingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete pricingPolicyActivitiesCompletionCheck(@Qualifier("pricingPolicyActivityChecker") ActivityChecker activityChecker) {
        return new AreActivitiesComplete(activityChecker);
    }

    @Bean @Autowired Function<ChenileExchange, String[]> pricingPolicyEventAuthoritiesSupplier(
            @Qualifier("pricingPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(infoProvider);
        return builder.build();
    }
}
