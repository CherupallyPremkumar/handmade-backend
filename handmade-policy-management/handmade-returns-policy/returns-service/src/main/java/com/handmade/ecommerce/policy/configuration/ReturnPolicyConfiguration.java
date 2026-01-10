package com.handmade.ecommerce.policy.configuration;

import com.handmade.ecommerce.policy.api.ReturnPolicyManager;
import com.handmade.ecommerce.policy.domain.aggregate.ReturnPolicy;
import com.handmade.ecommerce.policy.service.health.ReturnPolicyHealthChecker;
import com.handmade.ecommerce.policy.service.impl.ReturnPolicyManagerImpl;
import com.handmade.ecommerce.policy.service.store.ReturnPolicyEntityStore;
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
public class ReturnPolicyConfiguration {
    private static final String FLOW_DEFINITION_FILE = "stm/return-policy-states.xml";
    public static final String PREFIX_FOR_RESOLVER = "returnPolicy";

    @Bean
    BeanFactoryAdapter returnPolicyBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    @Bean
    STMFlowStoreImpl returnPolicyFlowStore(
            @Qualifier("returnPolicyBeanFactoryAdapter") BeanFactoryAdapter beanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }

    @Bean
    @Autowired
    STM<ReturnPolicy> returnPolicyEntityStm(
            @Qualifier("returnPolicyFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<ReturnPolicy> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider returnPolicyActionsInfoProvider(
            @Qualifier("returnPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("returnPolicy", provider);
        return provider;
    }

    @Bean
    @Autowired
    ReturnPolicyManager _returnPolicyStateEntityService_(
            @Qualifier("returnPolicyEntityStm") STM<ReturnPolicy> stm,
            @Qualifier("returnPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("returnPolicyEntityStore") EntityStore<ReturnPolicy> entityStore) {
        return new ReturnPolicyManagerImpl(stm, infoProvider, entityStore);
    }

    @Bean
    @Autowired
    DefaultPostSaveHook<ReturnPolicy> returnPolicyDefaultPostSaveHook(
            @Qualifier("returnPolicyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new DefaultPostSaveHook<>(stmTransitionActionResolver);
    }

    @Bean
    @Autowired
    GenericEntryAction<ReturnPolicy> returnPolicyEntryAction(
            @Qualifier("returnPolicyEntityStore") EntityStore<ReturnPolicy> entityStore,
            @Qualifier("returnPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("returnPolicyDefaultPostSaveHook") DefaultPostSaveHook<ReturnPolicy> postSaveHook,
            @Qualifier("returnPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericEntryAction<ReturnPolicy> entryAction = new GenericEntryAction<>(entityStore, infoProvider,
                postSaveHook);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
    }

    @Bean
    XmlFlowReader returnPolicyFlowReader(@Qualifier("returnPolicyFlowStore") STMFlowStoreImpl flowStore)
            throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }

    @Bean
    ReturnPolicyHealthChecker returnPolicyHealthChecker() {
        return new ReturnPolicyHealthChecker();
    }

    @Bean
    STMTransitionAction<ReturnPolicy> defaultReturnPolicySTMTransitionAction() {
        return (policy, payload, startState, eventId, endState, stm, transition) -> {
        };
    }

    @Bean
    STMTransitionActionResolver returnPolicyTransitionActionResolver(
            @Qualifier("defaultReturnPolicySTMTransitionAction") STMTransitionAction<ReturnPolicy> defaultAction) {
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, defaultAction, true);
    }

    @Bean
    @Autowired
    StmBodyTypeSelector returnPolicyBodyTypeSelector(
            @Qualifier("returnPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("returnPolicyTransitionActionResolver") STMTransitionActionResolver resolver) {
        return new StmBodyTypeSelector(infoProvider, resolver);
    }

    @Bean
    ApproveReturnPolicyAction returnPolicyApproveAction() {
        return new ApproveReturnPolicyAction();
    }

    @Bean
    DeleteReturnPolicyAction returnPolicyDeleteAction() {
        return new DeleteReturnPolicyAction();
    }

    @Bean
    DeprecateReturnPolicyAction returnPolicyDeprecateAction() {
        return new DeprecateReturnPolicyAction();
    }

    @Bean
    AddReturnRuleAction returnPolicyAddRuleAction() {
        return new AddReturnRuleAction();
    }

    @Bean
    UpdateReturnRuleAction returnPolicyUpdateRuleAction() {
        return new UpdateReturnRuleAction();
    }

    @Bean
    RemoveReturnRuleAction returnPolicyRemoveRuleAction() {
        return new RemoveReturnRuleAction();
    }

    @Bean
    @Autowired
    STMTransitionAction<ReturnPolicy> returnPolicyBaseTransitionAction(
            @Qualifier("returnPolicyTransitionActionResolver") STMTransitionActionResolver resolver,
            @Qualifier("returnPolicyActivityChecker") ActivityChecker activityChecker,
            @Qualifier("returnPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        BaseTransitionAction<ReturnPolicy> baseTransitionAction = new BaseTransitionAction<>(resolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean
    ActivityChecker returnPolicyActivityChecker(@Qualifier("returnPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new ActivityChecker(stmFlowStore);
    }
}
