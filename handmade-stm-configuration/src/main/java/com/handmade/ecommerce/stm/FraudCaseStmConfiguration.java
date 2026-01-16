package com.handmade.ecommerce.stm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.stm.impl.STMFlowStoreImpl;
import org.chenile.stm.impl.STMImpl;
import org.chenile.stm.impl.XmlFlowReader;
import org.chenile.stm.impl.BeanFactoryAdapter;
import org.chenile.stm.spring.SpringBeanFactoryAdapter;
import org.chenile.workflow.api.WorkflowRegistry;

import com.handmade.ecommerce.domain.risk.FraudCase;

@Configuration
public class FraudCaseStmConfiguration {

    private static final String FLOW_DEFINITION_FILE = "stm/stm/fraud-case-states.xml";
    private static final String WORKFLOW_NAME = "fraud-case";

    // -------- BeanFactoryAdapter (PER STM) --------
    @Bean
    public BeanFactoryAdapter fraudCaseBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    // -------- Flow Store --------
    @Bean
    public STMFlowStoreImpl fraudCaseFlowStore(
            @Qualifier("fraudCaseBeanFactoryAdapter")
            BeanFactoryAdapter beanFactoryAdapter) {

        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }

    // -------- STM --------
    @Bean
    public STM<FraudCase> fraudCaseEntityStm(
            @Qualifier("fraudCaseFlowStore")
            STMFlowStoreImpl stmFlowStore) {

        STMImpl<FraudCase> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    // -------- Actions Info --------
    @Bean
    public STMActionsInfoProvider fraudCaseActionsInfoProvider(
            @Qualifier("fraudCaseFlowStore")
            STMFlowStoreImpl stmFlowStore) {

        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider(WORKFLOW_NAME, provider);
        return provider;
    }

    // -------- XML Flow Reader --------
    @Bean
    public XmlFlowReader fraudCaseFlowReader(
            @Qualifier("fraudCaseFlowStore")
            STMFlowStoreImpl flowStore) throws Exception {

        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}
