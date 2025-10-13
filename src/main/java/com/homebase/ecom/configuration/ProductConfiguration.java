package com.homebase.ecom.configuration;



import com.homebase.ecom.domain.Product;
import com.homebase.ecom.entitystore.ProductEntityStore;
import com.homebase.ecom.repository.ProductRepository;
import org.chenile.core.context.ChenileExchange;
import org.chenile.core.context.ContextContainer;
import org.chenile.stm.STM;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.impl.BeanFactoryAdapter;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.stm.impl.STMFlowStoreImpl;
import org.chenile.stm.impl.STMImpl;
import org.chenile.stm.impl.XmlFlowReader;
import org.chenile.stm.spring.SpringBeanFactoryAdapter;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.api.StateEntityService;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.chenile.workflow.service.stmcmds.BaseTransitionAction;
import org.chenile.workflow.service.stmcmds.GenericEntryAction;
import org.chenile.workflow.service.stmcmds.GenericExitAction;
import org.chenile.workflow.service.stmcmds.StmAuthoritiesBuilder;
import org.chenile.workflow.service.stmcmds.StmBodyTypeSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * This is where you will instantiate all the required classes in Spring
 */
@Configuration
public class ProductConfiguration {


    private static final String FLOW_DEFINITION_FILE = "stm/product-flow.xml";

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> productAuthorities(STMActionsInfoProvider productActionsInfoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(productActionsInfoProvider);
        return builder.build();
    }


    @Bean
    BeanFactoryAdapter productBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }




    @Bean
    STMFlowStoreImpl productFlowStore(@Qualifier("productBeanFactoryAdapter") BeanFactoryAdapter productBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(productBeanFactoryAdapter);
        return stmFlowStore;
    }


    @Bean
    @Autowired
    STM<Product> productEntityStm(@Qualifier("productFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<Product> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider productActionsInfoProvider(@Qualifier("productFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new STMActionsInfoProvider(stmFlowStore);
    }

    @Bean
    EntityStore<Product> productEntityStore(ProductRepository productRepository) {
        return new ProductEntityStore(productRepository);
    }

    @Bean
    @Autowired
    StateEntityService _productStateEntityService_(
            @Qualifier("productEntityStm") STM<Product> stm,
            @Qualifier("productActionsInfoProvider") STMActionsInfoProvider productInfoProvider,
            @Qualifier("productEntityStore") EntityStore<Product> entityStore) {
        return new StateEntityServiceImpl(stm, productInfoProvider, entityStore);
    }

    // Now we start constructing the STM Components

    @Bean
    @Autowired
    GenericEntryAction<Product> productEntryAction(@Qualifier("productEntityStore") EntityStore<Product> entityStore,
                                               @Qualifier("productActionsInfoProvider") STMActionsInfoProvider productInfoProvider) {
        return new GenericEntryAction<Product>(entityStore, productInfoProvider);
    }

    @Bean
    GenericExitAction<Product> productExitAction() {
        return new GenericExitAction<Product>();
    }

    @Bean
    @Autowired
    StmBodyTypeSelector productBodyTypeSelector(@Qualifier("productActionsInfoProvider") STMActionsInfoProvider productInfoProvider) {
        return new StmBodyTypeSelector(productInfoProvider);
    }

    @Bean

    STMTransitionAction<Product> productBaseTransitionAction() {
        return new BaseTransitionAction<>();
    }


    @Bean
    XmlFlowReader productFlowReader(@Qualifier("productFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}

