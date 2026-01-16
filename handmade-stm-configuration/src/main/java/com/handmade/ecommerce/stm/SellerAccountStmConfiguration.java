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

import com.handmade.ecommerce.domain.seller.SellerAccount;

@Configuration
public class SellerAccountStmConfiguration {

    private static final String FLOW_DEFINITION_FILE = "stm/stm/seller-account-states.xml";
    private static final String WORKFLOW_NAME = "seller-account";

    // -------- BeanFactoryAdapter (PER STM) --------
    @Bean
    public BeanFactoryAdapter sellerAccountBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    // -------- Flow Store --------
    @Bean
    public STMFlowStoreImpl sellerAccountFlowStore(
            @Qualifier("sellerAccountBeanFactoryAdapter")
            BeanFactoryAdapter beanFactoryAdapter) {

        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }

    // -------- STM --------
    @Bean
    public STM<SellerAccount> sellerAccountEntityStm(
            @Qualifier("sellerAccountFlowStore")
            STMFlowStoreImpl stmFlowStore) {

        STMImpl<SellerAccount> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    // -------- Actions Info --------
    @Bean
    public STMActionsInfoProvider sellerAccountActionsInfoProvider(
            @Qualifier("sellerAccountFlowStore")
            STMFlowStoreImpl stmFlowStore) {

        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider(WORKFLOW_NAME, provider);
        return provider;
    }

    // -------- XML Flow Reader --------
    @Bean
    public XmlFlowReader sellerAccountFlowReader(
            @Qualifier("sellerAccountFlowStore")
            STMFlowStoreImpl flowStore) throws Exception {

        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}
