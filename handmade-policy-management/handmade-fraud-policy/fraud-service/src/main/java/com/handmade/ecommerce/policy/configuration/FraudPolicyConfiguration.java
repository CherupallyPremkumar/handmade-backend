package com.handmade.ecommerce.policy.configuration;

import com.handmade.ecommerce.policy.api.FraudPolicyManager;
import com.handmade.ecommerce.policy.domain.aggregate.FraudPolicy;
import com.handmade.ecommerce.policy.service.health.FraudPolicyHealthChecker;
import com.handmade.ecommerce.policy.service.impl.FraudPolicyManagerImpl;
import com.handmade.ecommerce.policy.service.store.FraudPolicyEntityStore;
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
public class FraudPolicyConfiguration {
    private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/policy/fraud-policy-states.xml";
    public static final String PREFIX_FOR_RESOLVER = "fraudPolicy";

    @Bean BeanFactoryAdapter fraudPolicyBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }
    
    @Bean STMFlowStoreImpl fraudPolicyFlowStore(
            @Qualifier("fraudPolicyBeanFactoryAdapter") BeanFactoryAdapter beanFactoryAdapter
            ) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }
    
    @Bean @Autowired STM<FraudPolicy> fraudPolicyEntityStm(
            @Qualifier("fraudPolicyFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<FraudPolicy> stm = new STMImpl<>();        
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }
    
    @Bean @Autowired STMActionsInfoProvider fraudPolicyActionsInfoProvider(
            @Qualifier("fraudPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("fraudPolicy", provider);
        return provider;
    }
    
    @Bean @Autowired
    FraudPolicyManager _fraudPolicyStateEntityService_(
            @Qualifier("fraudPolicyEntityStm") STM<FraudPolicy> stm,
            @Qualifier("fraudPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("fraudPolicyEntityStore") EntityStore<FraudPolicy> entityStore) {
        return new FraudPolicyManagerImpl(stm, infoProvider, entityStore);
    }
    
    @Bean @Autowired DefaultPostSaveHook<FraudPolicy> fraudPolicyDefaultPostSaveHook(
            @Qualifier("fraudPolicyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new DefaultPostSaveHook<>(stmTransitionActionResolver);
    }

    @Bean @Autowired GenericEntryAction<FraudPolicy> fraudPolicyEntryAction(
            @Qualifier("fraudPolicyEntityStore") EntityStore<FraudPolicy> entityStore,
            @Qualifier("fraudPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("fraudPolicyDefaultPostSaveHook") DefaultPostSaveHook<FraudPolicy> postSaveHook,
            @Qualifier("fraudPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericEntryAction<FraudPolicy> entryAction = new GenericEntryAction<>(entityStore, infoProvider, postSaveHook);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
    }

    @Bean
    XmlFlowReader fraudPolicyFlowReader(@Qualifier("fraudPolicyFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
    
    @Bean FraudPolicyHealthChecker fraudPolicyHealthChecker() {
        return new FraudPolicyHealthChecker();
    }

    @Bean STMTransitionAction<FraudPolicy> defaultFraudPolicySTMTransitionAction() {
        return (policy, payload, startState, eventId, endState, stm, transition) -> { };
    }

    @Bean
    STMTransitionActionResolver fraudPolicyTransitionActionResolver(
            @Qualifier("defaultFraudPolicySTMTransitionAction") STMTransitionAction<FraudPolicy> defaultAction) {
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, defaultAction, true);
    }

    @Bean @Autowired StmBodyTypeSelector fraudPolicyBodyTypeSelector(
            @Qualifier("fraudPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("fraudPolicyTransitionActionResolver") STMTransitionActionResolver resolver) {
        return new StmBodyTypeSelector(infoProvider, resolver);
    }

    @Bean ApproveFraudPolicyAction fraudPolicyApproveAction() {
        return new ApproveFraudPolicyAction();
    }

    @Bean DeleteFraudPolicyAction fraudPolicyDeleteAction() {
        return new DeleteFraudPolicyAction();
    }

    @Bean DeprecateFraudPolicyAction fraudPolicyDeprecateAction() {
        return new DeprecateFraudPolicyAction();
    }

    @Bean AddFraudRuleAction fraudPolicyAddRuleAction() {
        return new AddFraudRuleAction();
    }

    @Bean UpdateFraudRuleAction fraudPolicyUpdateRuleAction() {
        return new UpdateFraudRuleAction();
    }

    @Bean RemoveFraudRuleAction fraudPolicyRemoveRuleAction() {
        return new RemoveFraudRuleAction();
    }

    @Bean @Autowired STMTransitionAction<FraudPolicy> fraudPolicyBaseTransitionAction(
            @Qualifier("fraudPolicyTransitionActionResolver") STMTransitionActionResolver resolver,
            @Qualifier("fraudPolicyActivityChecker") ActivityChecker activityChecker,
            @Qualifier("fraudPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        BaseTransitionAction<FraudPolicy> baseTransitionAction = new BaseTransitionAction<>(resolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker fraudPolicyActivityChecker(@Qualifier("fraudPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new ActivityChecker(stmFlowStore);
    }
}
