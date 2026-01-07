package com.handmade.ecommerce.policy.configuration;

import com.handmade.ecommerce.policy.api.InventoryPolicyManager;
import com.handmade.ecommerce.policy.domain.aggregate.InventoryPolicy;
import com.handmade.ecommerce.policy.service.health.InventoryPolicyHealthChecker;
import com.handmade.ecommerce.policy.service.impl.InventoryPolicyManagerImpl;
import com.handmade.ecommerce.policy.service.store.InventoryPolicyEntityStore;
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
public class InventoryPolicyConfiguration {
    private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/policy/inventory-policy-states.xml";
    public static final String PREFIX_FOR_RESOLVER = "inventoryPolicy";

    @Bean BeanFactoryAdapter inventoryPolicyBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }
    
    @Bean STMFlowStoreImpl inventoryPolicyFlowStore(
            @Qualifier("inventoryPolicyBeanFactoryAdapter") BeanFactoryAdapter beanFactoryAdapter
            ) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }
    
    @Bean @Autowired STM<InventoryPolicy> inventoryPolicyEntityStm(
            @Qualifier("inventoryPolicyFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<InventoryPolicy> stm = new STMImpl<>();        
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }
    
    @Bean @Autowired STMActionsInfoProvider inventoryPolicyActionsInfoProvider(
            @Qualifier("inventoryPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("inventoryPolicy", provider);
        return provider;
    }
    
    @Bean @Autowired
    InventoryPolicyManager _inventoryPolicyStateEntityService_(
            @Qualifier("inventoryPolicyEntityStm") STM<InventoryPolicy> stm,
            @Qualifier("inventoryPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("inventoryPolicyEntityStore") EntityStore<InventoryPolicy> entityStore) {
        return new InventoryPolicyManagerImpl(stm, infoProvider, entityStore);
    }
    
    @Bean @Autowired DefaultPostSaveHook<InventoryPolicy> inventoryPolicyDefaultPostSaveHook(
            @Qualifier("inventoryPolicyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new DefaultPostSaveHook<>(stmTransitionActionResolver);
    }

    @Bean @Autowired GenericEntryAction<InventoryPolicy> inventoryPolicyEntryAction(
            @Qualifier("inventoryPolicyEntityStore") EntityStore<InventoryPolicy> entityStore,
            @Qualifier("inventoryPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("inventoryPolicyDefaultPostSaveHook") DefaultPostSaveHook<InventoryPolicy> postSaveHook,
            @Qualifier("inventoryPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericEntryAction<InventoryPolicy> entryAction = new GenericEntryAction<>(entityStore, infoProvider, postSaveHook);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
    }

    @Bean
    XmlFlowReader inventoryPolicyFlowReader(@Qualifier("inventoryPolicyFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
    
    @Bean InventoryPolicyHealthChecker inventoryPolicyHealthChecker() {
        return new InventoryPolicyHealthChecker();
    }

    @Bean STMTransitionAction<InventoryPolicy> defaultInventoryPolicySTMTransitionAction() {
        return (policy, payload, startState, eventId, endState, stm, transition) -> { };
    }

    @Bean
    STMTransitionActionResolver inventoryPolicyTransitionActionResolver(
            @Qualifier("defaultInventoryPolicySTMTransitionAction") STMTransitionAction<InventoryPolicy> defaultAction) {
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, defaultAction, true);
    }

    @Bean @Autowired StmBodyTypeSelector inventoryPolicyBodyTypeSelector(
            @Qualifier("inventoryPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("inventoryPolicyTransitionActionResolver") STMTransitionActionResolver resolver) {
        return new StmBodyTypeSelector(infoProvider, resolver);
    }

    @Bean ApproveInventoryPolicyAction inventoryPolicyApproveAction() {
        return new ApproveInventoryPolicyAction();
    }

    @Bean DeleteInventoryPolicyAction inventoryPolicyDeleteAction() {
        return new DeleteInventoryPolicyAction();
    }

    @Bean DeprecateInventoryPolicyAction inventoryPolicyDeprecateAction() {
        return new DeprecateInventoryPolicyAction();
    }

    @Bean AddInventoryRuleAction inventoryPolicyAddRuleAction() {
        return new AddInventoryRuleAction();
    }

    @Bean UpdateInventoryRuleAction inventoryPolicyUpdateRuleAction() {
        return new UpdateInventoryRuleAction();
    }

    @Bean RemoveInventoryRuleAction inventoryPolicyRemoveRuleAction() {
        return new RemoveInventoryRuleAction();
    }

    @Bean @Autowired STMTransitionAction<InventoryPolicy> inventoryPolicyBaseTransitionAction(
            @Qualifier("inventoryPolicyTransitionActionResolver") STMTransitionActionResolver resolver,
            @Qualifier("inventoryPolicyActivityChecker") ActivityChecker activityChecker,
            @Qualifier("inventoryPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        BaseTransitionAction<InventoryPolicy> baseTransitionAction = new BaseTransitionAction<>(resolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker inventoryPolicyActivityChecker(@Qualifier("inventoryPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new ActivityChecker(stmFlowStore);
    }
}
