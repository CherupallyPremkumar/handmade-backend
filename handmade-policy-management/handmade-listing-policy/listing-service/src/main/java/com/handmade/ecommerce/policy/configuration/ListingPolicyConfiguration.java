package com.handmade.ecommerce.policy.configuration;

import com.handmade.ecommerce.policy.api.ListingPolicyManager;
import com.handmade.ecommerce.policy.domain.aggregate.ListingPolicy;
import com.handmade.ecommerce.policy.service.health.ListingPolicyHealthChecker;
import com.handmade.ecommerce.policy.service.impl.ListingPolicyManagerImpl;
import com.handmade.ecommerce.policy.service.store.ListingPolicyEntityStore;
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
public class ListingPolicyConfiguration {
    private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/policy/listing-policy-states.xml";
    public static final String PREFIX_FOR_RESOLVER = "listingPolicy";

    @Bean BeanFactoryAdapter listingPolicyBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }
    
    @Bean STMFlowStoreImpl listingPolicyFlowStore(
            @Qualifier("listingPolicyBeanFactoryAdapter") BeanFactoryAdapter beanFactoryAdapter
            ) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }
    
    @Bean @Autowired STM<ListingPolicy> listingPolicyEntityStm(
            @Qualifier("listingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<ListingPolicy> stm = new STMImpl<>();        
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }
    
    @Bean @Autowired STMActionsInfoProvider listingPolicyActionsInfoProvider(
            @Qualifier("listingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("listingPolicy", provider);
        return provider;
    }
    
    @Bean @Autowired
    ListingPolicyManager _listingPolicyStateEntityService_(
            @Qualifier("listingPolicyEntityStm") STM<ListingPolicy> stm,
            @Qualifier("listingPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("listingPolicyEntityStore") EntityStore<ListingPolicy> entityStore) {
        return new ListingPolicyManagerImpl(stm, infoProvider, entityStore);
    }
    
    @Bean @Autowired DefaultPostSaveHook<ListingPolicy> listingPolicyDefaultPostSaveHook(
            @Qualifier("listingPolicyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new DefaultPostSaveHook<>(stmTransitionActionResolver);
    }

    @Bean @Autowired GenericEntryAction<ListingPolicy> listingPolicyEntryAction(
            @Qualifier("listingPolicyEntityStore") EntityStore<ListingPolicy> entityStore,
            @Qualifier("listingPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("listingPolicyDefaultPostSaveHook") DefaultPostSaveHook<ListingPolicy> postSaveHook,
            @Qualifier("listingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericEntryAction<ListingPolicy> entryAction = new GenericEntryAction<>(entityStore, infoProvider, postSaveHook);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
    }

    @Bean
    XmlFlowReader listingPolicyFlowReader(@Qualifier("listingPolicyFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
    
    @Bean ListingPolicyHealthChecker listingPolicyHealthChecker() {
        return new ListingPolicyHealthChecker();
    }

    @Bean STMTransitionAction<ListingPolicy> defaultListingPolicySTMTransitionAction() {
        return (policy, payload, startState, eventId, endState, stm, transition) -> { };
    }

    @Bean
    STMTransitionActionResolver listingPolicyTransitionActionResolver(
            @Qualifier("defaultListingPolicySTMTransitionAction") STMTransitionAction<ListingPolicy> defaultAction) {
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, defaultAction, true);
    }

    @Bean @Autowired StmBodyTypeSelector listingPolicyBodyTypeSelector(
            @Qualifier("listingPolicyActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("listingPolicyTransitionActionResolver") STMTransitionActionResolver resolver) {
        return new StmBodyTypeSelector(infoProvider, resolver);
    }

    @Bean ApproveListingPolicyAction listingPolicyApproveAction() {
        return new ApproveListingPolicyAction();
    }

    @Bean DeleteListingPolicyAction listingPolicyDeleteAction() {
        return new DeleteListingPolicyAction();
    }

    @Bean DeprecateListingPolicyAction listingPolicyDeprecateAction() {
        return new DeprecateListingPolicyAction();
    }

    @Bean AddListingRuleAction listingPolicyAddRuleAction() {
        return new AddListingRuleAction();
    }

    @Bean UpdateListingRuleAction listingPolicyUpdateRuleAction() {
        return new UpdateListingRuleAction();
    }

    @Bean RemoveListingRuleAction listingPolicyRemoveRuleAction() {
        return new RemoveListingRuleAction();
    }

    @Bean @Autowired STMTransitionAction<ListingPolicy> listingPolicyBaseTransitionAction(
            @Qualifier("listingPolicyTransitionActionResolver") STMTransitionActionResolver resolver,
            @Qualifier("listingPolicyActivityChecker") ActivityChecker activityChecker,
            @Qualifier("listingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        BaseTransitionAction<ListingPolicy> baseTransitionAction = new BaseTransitionAction<>(resolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker listingPolicyActivityChecker(@Qualifier("listingPolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new ActivityChecker(stmFlowStore);
    }
}
