package com.handmade.ecommerce.adcampaign.configuration;

import com.handmade.ecommerce.adcampaign.service.cmds.*;
import com.handmade.ecommerce.adcampaign.service.healthcheck.AdCampaignHealthChecker;
import com.handmade.ecommerce.adcampaign.service.store.AdCampaignEntityStore;
import com.handmade.ecommerce.adcampaign.model.AdCampaign;
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
import org.chenile.workflow.service.stmcmds.*;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 * This is where you will instantiate all the required classes in Spring
 */
@Configuration
public class AdCampaignConfiguration {
    private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/adcampaign/adcampaign-states.xml";
    public static final String PREFIX_FOR_PROPERTIES = "AdCampaign";
    public static final String PREFIX_FOR_RESOLVER = "adCampaign";

    @Bean
    BeanFactoryAdapter adCampaignBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    @Bean
    STMFlowStoreImpl adCampaignFlowStore(
            @Qualifier("adCampaignBeanFactoryAdapter") BeanFactoryAdapter adCampaignBeanFactoryAdapter)
            throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(adCampaignBeanFactoryAdapter);
        return stmFlowStore;
    }

    @Bean
    STM<AdCampaign> adCampaignEntityStm(@Qualifier("adCampaignFlowStore") STMFlowStoreImpl stmFlowStore)
            throws Exception {
        STMImpl<AdCampaign> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    STMActionsInfoProvider adCampaignActionsInfoProvider(
            @Qualifier("adCampaignFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("adCampaign", provider);
        return provider;
    }

    @Bean
    EntityStore<AdCampaign> adCampaignEntityStore() {
        return new AdCampaignEntityStore();
    }

    @Bean
    StateEntityServiceImpl<AdCampaign> _adCampaignStateEntityService_(
            @Qualifier("adCampaignEntityStm") STM<AdCampaign> stm,
            @Qualifier("adCampaignActionsInfoProvider") STMActionsInfoProvider adCampaignInfoProvider,
            @Qualifier("adCampaignEntityStore") EntityStore<AdCampaign> entityStore) {
        return new StateEntityServiceImpl<>(stm, adCampaignInfoProvider, entityStore);
    }

    // Now we start constructing the STM Components

    @Bean
    GenericEntryAction<AdCampaign> adCampaignEntryAction(
            @Qualifier("adCampaignEntityStore") EntityStore<AdCampaign> entityStore,
            @Qualifier("adCampaignActionsInfoProvider") STMActionsInfoProvider adCampaignInfoProvider,
            @Qualifier("adCampaignFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericEntryAction<AdCampaign> entryAction = new GenericEntryAction<AdCampaign>(entityStore,
                adCampaignInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
    }

    @Bean
    GenericExitAction<AdCampaign> adCampaignExitAction(
            @Qualifier("adCampaignFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericExitAction<AdCampaign> exitAction = new GenericExitAction<AdCampaign>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
    }

    @Bean
    XmlFlowReader adCampaignFlowReader(@Qualifier("adCampaignFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }

    @Bean
    AdCampaignHealthChecker adCampaignHealthChecker() {
        return new AdCampaignHealthChecker();
    }

    @Bean
    STMTransitionAction<AdCampaign> defaultadCampaignSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver adCampaignTransitionActionResolver(
            @Qualifier("defaultadCampaignSTMTransitionAction") STMTransitionAction<AdCampaign> defaultSTMTransitionAction) {
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, defaultSTMTransitionAction);
    }

    @Bean
    StmBodyTypeSelector adCampaignBodyTypeSelector(
            @Qualifier("adCampaignActionsInfoProvider") STMActionsInfoProvider adCampaignInfoProvider,
            @Qualifier("adCampaignTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(adCampaignInfoProvider, stmTransitionActionResolver);
    }

    @Bean
    STMTransitionAction<AdCampaign> adCampaignBaseTransitionAction(
            @Qualifier("adCampaignTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new BaseTransitionAction<>(stmTransitionActionResolver);
    }

    // Create the specific transition actions here. Make sure that these actions are
    // inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this).
    // To automatically wire
    // them into the STM use the convention of "adCampaign" + eventId for the method
    // name. (adCampaign is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow
    // system.
    // The payload types will be detected as well so that there is no need to
    // introduce an <event-information/>
    // segment in src/main/resources/com/handmade/adCampaign/adCampaign-states.xml

    @Bean
    ResumeAdCampaignAction resumeAdCampaignAction() {
        return new ResumeAdCampaignAction();
    }

    @Bean
    EndAdCampaignAction endAdCampaignAction() {
        return new EndAdCampaignAction();
    }

    @Bean
    PauseAdCampaignAction pauseAdCampaignAction() {
        return new PauseAdCampaignAction();
    }

    @Bean
    LaunchAdCampaignAction launchAdCampaignAction() {
        return new LaunchAdCampaignAction();
    }

}
