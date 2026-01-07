package com.handmade.ecommerce.policy.configuration;

import com.handmade.ecommerce.policy.api.PerformancePolicyManager;
import com.handmade.ecommerce.policy.domain.aggregate.PerformancePolicy;
import com.handmade.ecommerce.policy.service.health.PerformancePolicyHealthChecker;
import com.handmade.ecommerce.policy.service.impl.PerformancePolicyManagerImpl;
import com.handmade.ecommerce.policy.service.store.PerformancePolicyEntityStore;
import com.handmade.ecommerce.policy.service.cmds.*;
import org.chenile.stm.*;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.impl.*;
import org.chenile.stm.spring.SpringBeanFactoryAdapter;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.stmcmds.*;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PerformancePolicyConfiguration {
    private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/policy/performance-policy-states.xml";
    public static final String PREFIX_FOR_RESOLVER = "performancePolicy";

    @Bean BeanFactoryAdapter performancePolicyBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }
    
    @Bean STMFlowStoreImpl performancePolicyFlowStore(
            @Qualifier("performancePolicyBeanFactoryAdapter") BeanFactoryAdapter beanFactoryAdapter
            ) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }
    
    @Bean @Autowired STM<PerformancePolicy> performancePolicyEntityStm(
            @Qualifier("performancePolicyFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<PerformancePolicy> stm = new STMImpl<>();        
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }
    
    @Bean @Autowired STMActionsInfoProvider performancePolicyActionsInfoProvider(
            @Qualifier("performancePolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("performancePolicy", provider);
        return provider;
    }
    
    @Bean @Autowired
    PerformancePolicyManager _performancePolicyStateEntityService_(
            @Qualifier("performancePolicyEntityStm") STM<PerformancePolicy> stm,
            @Qualifier("performancePolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("performancePolicyEntityStore") EntityStore<PerformancePolicy> entityStore) {
        return new PerformancePolicyManagerImpl(stm, infoProvider, entityStore);
    }
    
    @Bean @Autowired DefaultPostSaveHook<PerformancePolicy> performancePolicyDefaultPostSaveHook(
            @Qualifier("performancePolicyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new DefaultPostSaveHook<>(stmTransitionActionResolver);
    }

    @Bean @Autowired GenericEntryAction<PerformancePolicy> performancePolicyEntryAction(
            @Qualifier("performancePolicyEntityStore") EntityStore<PerformancePolicy> entityStore,
            @Qualifier("performancePolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("performancePolicyDefaultPostSaveHook") DefaultPostSaveHook<PerformancePolicy> postSaveHook,
            @Qualifier("performancePolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericEntryAction<PerformancePolicy> entryAction = new GenericEntryAction<>(entityStore, infoProvider, postSaveHook);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
    }

    @Bean
    XmlFlowReader performancePolicyFlowReader(@Qualifier("performancePolicyFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
    
    @Bean PerformancePolicyHealthChecker performancePolicyHealthChecker() {
        return new PerformancePolicyHealthChecker();
    }

    @Bean STMTransitionAction<PerformancePolicy> defaultPerformancePolicySTMTransitionAction() {
        return (policy, payload, startState, eventId, endState, stm, transition) -> { };
    }

    @Bean
    STMTransitionActionResolver performancePolicyTransitionActionResolver(
            @Qualifier("defaultPerformancePolicySTMTransitionAction") STMTransitionAction<PerformancePolicy> defaultAction) {
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, defaultAction, true);
    }

    @Bean @Autowired StmBodyTypeSelector performancePolicyBodyTypeSelector(
            @Qualifier("performancePolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("performancePolicyTransitionActionResolver") STMTransitionActionResolver resolver) {
        return new StmBodyTypeSelector(infoProvider, resolver);
    }

    @Bean ApprovePerformancePolicyAction performancePolicyApproveAction() {
        return new ApprovePerformancePolicyAction();
    }

    @Bean DeletePerformancePolicyAction performancePolicyDeleteAction() {
        return new DeletePerformancePolicyAction();
    }

    @Bean DeprecatePerformancePolicyAction performancePolicyDeprecateAction() {
        return new DeprecatePerformancePolicyAction();
    }

    @Bean AddPerformanceThresholdAction performancePolicyAddRuleAction() {
        return new AddPerformanceThresholdAction();
    }

    @Bean UpdatePerformanceThresholdAction performancePolicyUpdateRuleAction() {
        return new UpdatePerformanceThresholdAction();
    }

    @Bean RemovePerformanceThresholdAction performancePolicyRemoveRuleAction() {
        return new RemovePerformanceThresholdAction();
    }

    @Bean @Autowired STMTransitionAction<PerformancePolicy> performancePolicyBaseTransitionAction(
            @Qualifier("performancePolicyTransitionActionResolver") STMTransitionActionResolver resolver,
            @Qualifier("performancePolicyActivityChecker") ActivityChecker activityChecker,
            @Qualifier("performancePolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        BaseTransitionAction<PerformancePolicy> baseTransitionAction = new BaseTransitionAction<>(resolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker performancePolicyActivityChecker(@Qualifier("performancePolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new ActivityChecker(stmFlowStore);
    }
}
