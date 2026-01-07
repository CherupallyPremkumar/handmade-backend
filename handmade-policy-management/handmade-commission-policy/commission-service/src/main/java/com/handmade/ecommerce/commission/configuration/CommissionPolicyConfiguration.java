package com.handmade.ecommerce.commission.configuration;

import com.handmade.ecommerce.commission.api.CommissionPolicyManager;
import com.handmade.ecommerce.commission.domain.aggregate.CommissionPolicy;
import com.handmade.ecommerce.commission.service.health.CommissionPolicyHealthChecker;
import com.handmade.ecommerce.commission.service.impl.CommissionPolicyManagerImpl;
import com.handmade.ecommerce.commission.service.store.CommissionPolicyEntityStore;
import com.handmade.ecommerce.commission.service.cmds.*;
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
public class CommissionPolicyConfiguration {
    private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/commission/commission-policy-states.xml";
    public static final String PREFIX_FOR_RESOLVER = "commissionPolicy";

    @Bean BeanFactoryAdapter commissionPolicyBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }
    
    @Bean STMFlowStoreImpl commissionPolicyFlowStore(
            @Qualifier("commissionPolicyBeanFactoryAdapter") BeanFactoryAdapter beanFactoryAdapter
            ) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }
    
    @Bean @Autowired STM<CommissionPolicy> commissionPolicyEntityStm(
            @Qualifier("commissionPolicyFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<CommissionPolicy> stm = new STMImpl<>();        
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }
    
    @Bean @Autowired STMActionsInfoProvider commissionPolicyActionsInfoProvider(
            @Qualifier("commissionPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("commissionPolicy", provider);
        return provider;
    }
    
    @Bean @Autowired
    CommissionPolicyManager commissionPolicyManager(
            @Qualifier("commissionPolicyEntityStm") STM<CommissionPolicy> stm,
            @Qualifier("commissionPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("commissionPolicyEntityStore") EntityStore<CommissionPolicy> entityStore) {
        return new CommissionPolicyManagerImpl(stm, infoProvider, entityStore);
    }
    
    @Bean @Autowired DefaultPostSaveHook<CommissionPolicy> commissionPolicyDefaultPostSaveHook(
            @Qualifier("commissionPolicyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new DefaultPostSaveHook<>(stmTransitionActionResolver);
    }

    @Bean @Autowired GenericEntryAction<CommissionPolicy> commissionPolicyEntryAction(
            @Qualifier("commissionPolicyEntityStore") EntityStore<CommissionPolicy> entityStore,
            @Qualifier("commissionPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("commissionPolicyDefaultPostSaveHook") DefaultPostSaveHook<CommissionPolicy> postSaveHook,
            @Qualifier("commissionPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericEntryAction<CommissionPolicy> entryAction = new GenericEntryAction<>(entityStore, infoProvider, postSaveHook);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
    }

    @Bean
    XmlFlowReader commissionPolicyFlowReader(@Qualifier("commissionPolicyFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
    
    @Bean CommissionPolicyHealthChecker commissionPolicyHealthChecker() {
        return new CommissionPolicyHealthChecker();
    }

    @Bean STMTransitionAction<CommissionPolicy> defaultCommissionPolicySTMTransitionAction() {
        return (policy, payload, startState, eventId, endState, stm, transition) -> { };
    }

    @Bean
    STMTransitionActionResolver commissionPolicyTransitionActionResolver(
            @Qualifier("defaultCommissionPolicySTMTransitionAction") STMTransitionAction<CommissionPolicy> defaultAction) {
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, defaultAction, true);
    }

    @Bean ApproveCommissionPolicyAction commissionPolicyApproveAction() {
        return new ApproveCommissionPolicyAction();
    }

    @Bean DeleteCommissionPolicyAction commissionPolicyDeleteAction() {
        return new DeleteCommissionPolicyAction();
    }

    @Bean DeprecateCommissionPolicyAction commissionPolicyDeprecateAction() {
        return new DeprecateCommissionPolicyAction();
    }

    @Bean AddCommissionRuleAction commissionPolicyAddRuleAction() {
        return new AddCommissionRuleAction();
    }

    @Bean UpdateCommissionRuleAction commissionPolicyUpdateRuleAction() {
        return new UpdateCommissionRuleAction();
    }

    @Bean RemoveCommissionRuleAction commissionPolicyRemoveRuleAction() {
        return new RemoveCommissionRuleAction();
    }

    @Bean @Autowired STMTransitionAction<CommissionPolicy> commissionPolicyBaseTransitionAction(
            @Qualifier("commissionPolicyTransitionActionResolver") STMTransitionActionResolver resolver,
            @Qualifier("commissionPolicyActivityChecker") ActivityChecker activityChecker,
            @Qualifier("commissionPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        BaseTransitionAction<CommissionPolicy> baseTransitionAction = new BaseTransitionAction<>(resolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker commissionPolicyActivityChecker(@Qualifier("commissionPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new ActivityChecker(stmFlowStore);
    }
}
