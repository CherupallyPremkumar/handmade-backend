package com.handmade.ecommerce.offer.configuration;

import com.handmade.ecommerce.offer.domain.aggregate.Offer;
import com.handmade.ecommerce.offer.service.cmds.*;
import com.handmade.ecommerce.offer.service.store.OfferEntityStore;
import org.chenile.stm.*;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.impl.*;
import org.chenile.stm.spring.SpringBeanFactoryAdapter;
import org.chenile.workflow.param.MinimalPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.chenile.workflow.api.WorkflowRegistry;

@Configuration
public class OfferConfiguration {
    private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/offer/offer-states.xml";
    public static final String PREFIX_FOR_RESOLVER = "offer";

    @Bean
    BeanFactoryAdapter offerBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    @Bean
    STMFlowStoreImpl offerFlowStore(
            @Qualifier("offerBeanFactoryAdapter") BeanFactoryAdapter offerBeanFactoryAdapter
    ) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(offerBeanFactoryAdapter);
        return stmFlowStore;
    }

    @Bean
    @Autowired
    STM<Offer> offerEntityStm(@Qualifier("offerFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<Offer> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider offerActionsInfoProvider(@Qualifier("offerFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider(PREFIX_FOR_RESOLVER, provider);
        return provider;
    }

    @Bean
    EntityStore<Offer> offerEntityStore() {
        return new OfferEntityStore();
    }

    @Bean
    @Autowired
    StateEntityServiceImpl<Offer> _offerStateEntityService_(
            @Qualifier("offerEntityStm") STM<Offer> stm,
            @Qualifier("offerActionsInfoProvider") STMActionsInfoProvider offerInfoProvider,
            @Qualifier("offerEntityStore") EntityStore<Offer> entityStore) {
        return new StateEntityServiceImpl<>(stm, offerInfoProvider, entityStore);
    }

    @Bean
    XmlFlowReader offerFlowReader(@Qualifier("offerFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }

    @Bean
    STMTransitionAction<Offer> defaultOfferSTMTransitionAction() {
        return new DefaultOfferSTMAction();
    }

    @Bean
    STMTransitionActionResolver offerTransitionActionResolver(
            @Qualifier("defaultOfferSTMTransitionAction") STMTransitionAction<Offer> defaultSTMTransitionAction) {
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, defaultSTMTransitionAction, true);
    }

    @Bean
    @Autowired
    STMTransitionAction<Offer> offerBaseTransitionAction(
            @Qualifier("offerTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
            @Qualifier("offerFlowStore") STMFlowStoreImpl stmFlowStore) {
        BaseTransitionAction<Offer> baseTransitionAction = new BaseTransitionAction<>(stmTransitionActionResolver);
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean
    ReconcileOfferActivationAction reconcileOfferActivationAction() {
        return new ReconcileOfferActivationAction();
    }

    @Bean
    org.chenile.workflow.service.activities.ActivityChecker offerActivityChecker(
            @Qualifier("offerFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new org.chenile.workflow.service.activities.ActivityChecker(stmFlowStore);
    }

    @Bean
    org.chenile.workflow.service.activities.AreActivitiesComplete offerActivitiesCompletionCheck(
            @Qualifier("offerActivityChecker") org.chenile.workflow.service.activities.ActivityChecker activityChecker) {
        return new org.chenile.workflow.service.activities.AreActivitiesComplete(activityChecker);
    }

    // Transition Actions tagged by naming convention for automatic resolution
    @Bean
    ActivateOfferAction offerActivateAction() {
        return new ActivateOfferAction();
    }

    @Bean
    SuspendOfferAction offerSuspendAction() {
        return new SuspendOfferAction();
    }
    
    @Bean
    DefaultOfferSTMAction offerDefaultAction() {
        return new DefaultOfferSTMAction();
    }
}
