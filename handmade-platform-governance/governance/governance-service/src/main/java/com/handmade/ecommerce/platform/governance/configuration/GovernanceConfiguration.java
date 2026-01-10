package com.handmade.ecommerce.platform.governance.configuration;

import com.handmade.ecommerce.platform.governance.api.GdprRequestService;
import com.handmade.ecommerce.platform.governance.entity.GdprRequest;
import com.handmade.ecommerce.platform.governance.service.impl.GdprRequestServiceImpl;
import com.handmade.ecommerce.platform.governance.store.GdprRequestStoreImpl;
import org.chenile.stm.STM;
import org.chenile.stm.impl.*;
import org.chenile.stm.spring.SpringBeanFactoryAdapter;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.stmcmds.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class for Governance service.
 */
@Configuration
public class GovernanceConfiguration {

    private static final String GDPR_FLOW_DEFINITION_FILE = "stm/gdpr-request-states.xml";
    public static final String PREFIX_FOR_RESOLVER = "";

    @Bean
    BeanFactoryAdapter governanceBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    @Bean
    @Autowired
    StmBodyTypeSelector gdprRequestBodyTypeSelector(
            @Qualifier("governanceActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("governanceTransitionActionResolver") STMTransitionActionResolver transitionActionResolver) {
        return new StmBodyTypeSelector(infoProvider, transitionActionResolver);
    }

    @Bean
    STMFlowStoreImpl governanceFlowStore(
            @Qualifier("governanceBeanFactoryAdapter") BeanFactoryAdapter adapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(adapter);
        return stmFlowStore;
    }

    @Bean
    @Autowired
    STM<GdprRequest> gdprRequestStm(
            @Qualifier("governanceFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<GdprRequest> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    GdprRequestService _governanceService_(
            @Qualifier("gdprRequestStm") STM<GdprRequest> stm,
            @Qualifier("governanceActionsInfoProvider") STMActionsInfoProvider provider,
            @Qualifier("governanceEntityStore") EntityStore<GdprRequest> entityStore) {
        return new GdprRequestServiceImpl(stm, provider, entityStore);
    }

    @Bean
    XmlFlowReader governanceFlowReader(
            @Qualifier("governanceFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(GDPR_FLOW_DEFINITION_FILE);
        return flowReader;
    }

    @Bean
    @Autowired
    GenericEntryAction<GdprRequest> governanceEntryAction(
            @Qualifier("governanceEntityStore") EntityStore<GdprRequest> entityStore,
            @Qualifier("governanceActionsInfoProvider") STMActionsInfoProvider provider,
            @Qualifier("governanceFlowStore") STMFlowStoreImpl stmFlowStore) {
        GenericEntryAction<GdprRequest> entryAction = new GenericEntryAction<>(entityStore, provider, null);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider governanceActionsInfoProvider(
            @Qualifier("governanceFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("gdpr", provider);
        return provider;
    }

    @Bean
    GenericExitAction<GdprRequest> governanceExitAction() {
        return new GenericExitAction<GdprRequest>();
    }

    @Bean
    @Autowired
    STMTransitionActionResolver governanceTransitionActionResolver() {
        // You might want to provide a default transition action here
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER, null);
    }

    @Bean
    EntityStore<GdprRequest> governanceEntityStore() {
        return new GdprRequestStoreImpl();
    }
}
