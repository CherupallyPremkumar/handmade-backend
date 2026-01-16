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

import com.handmade.ecommerce.domain.policy.PolicyDefinition;

@Configuration
public class PolicyDefinitionStmConfiguration {

    private static final String FLOW_DEFINITION_FILE = "stm/stm/policy-definition-states.xml";
    private static final String WORKFLOW_NAME = "policy-definition";

    // -------- BeanFactoryAdapter (PER STM) --------
    @Bean
    public BeanFactoryAdapter policyDefinitionBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    // -------- Flow Store --------
    @Bean
    public STMFlowStoreImpl policyDefinitionFlowStore(
            @Qualifier("policyDefinitionBeanFactoryAdapter")
            BeanFactoryAdapter beanFactoryAdapter) {

        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }

    // -------- STM --------
    @Bean
    public STM<PolicyDefinition> policyDefinitionEntityStm(
            @Qualifier("policyDefinitionFlowStore")
            STMFlowStoreImpl stmFlowStore) {

        STMImpl<PolicyDefinition> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    // -------- Actions Info --------
    @Bean
    public STMActionsInfoProvider policyDefinitionActionsInfoProvider(
            @Qualifier("policyDefinitionFlowStore")
            STMFlowStoreImpl stmFlowStore) {

        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider(WORKFLOW_NAME, provider);
        return provider;
    }

    // -------- XML Flow Reader --------
    @Bean
    public XmlFlowReader policyDefinitionFlowReader(
            @Qualifier("policyDefinitionFlowStore")
            STMFlowStoreImpl flowStore) throws Exception {

        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}
