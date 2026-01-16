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

import com.handmade.ecommerce.domain.event.WorkflowTask;

@Configuration
public class WorkflowTaskStmConfiguration {

    private static final String FLOW_DEFINITION_FILE = "stm/stm/workflow-task-states.xml";
    private static final String WORKFLOW_NAME = "workflow-task";

    // -------- BeanFactoryAdapter (PER STM) --------
    @Bean
    public BeanFactoryAdapter workflowTaskBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    // -------- Flow Store --------
    @Bean
    public STMFlowStoreImpl workflowTaskFlowStore(
            @Qualifier("workflowTaskBeanFactoryAdapter")
            BeanFactoryAdapter beanFactoryAdapter) {

        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }

    // -------- STM --------
    @Bean
    public STM<WorkflowTask> workflowTaskEntityStm(
            @Qualifier("workflowTaskFlowStore")
            STMFlowStoreImpl stmFlowStore) {

        STMImpl<WorkflowTask> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    // -------- Actions Info --------
    @Bean
    public STMActionsInfoProvider workflowTaskActionsInfoProvider(
            @Qualifier("workflowTaskFlowStore")
            STMFlowStoreImpl stmFlowStore) {

        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider(WORKFLOW_NAME, provider);
        return provider;
    }

    // -------- XML Flow Reader --------
    @Bean
    public XmlFlowReader workflowTaskFlowReader(
            @Qualifier("workflowTaskFlowStore")
            STMFlowStoreImpl flowStore) throws Exception {

        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}
