package com.homebase.ecom.configuration;



import com.homebase.ecom.domain.ProductVariant;
import com.homebase.ecom.entitystore.ProductVariantEntityStore;
import com.homebase.ecom.repository.ProductVariantRepository;
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
public class ProductVariantConfiguration {


    private static final String FLOW_DEFINITION_FILE = "stm/productVariant-flow.xml";

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> productVariantAuthorities(STMActionsInfoProvider productVariantActionsInfoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(productVariantActionsInfoProvider);
        return builder.build();
    }


    @Bean
    BeanFactoryAdapter productVariantBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }




    @Bean
    STMFlowStoreImpl productVariantFlowStore(@Qualifier("productVariantBeanFactoryAdapter") BeanFactoryAdapter productVariantBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(productVariantBeanFactoryAdapter);
        return stmFlowStore;
    }


    @Bean
    @Autowired
    STM<ProductVariant> productVariantEntityStm(@Qualifier("productVariantFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<ProductVariant> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider productVariantActionsInfoProvider(@Qualifier("productVariantFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new STMActionsInfoProvider(stmFlowStore);
    }

    @Bean
    EntityStore<ProductVariant> productVariantEntityStore(ProductVariantRepository productVariantRepository) {
        return new ProductVariantEntityStore(productVariantRepository);
    }

    @Bean
    @Autowired
    StateEntityService _productVariantStateEntityService_(
            @Qualifier("productVariantEntityStm") STM<ProductVariant> stm,
            @Qualifier("productVariantActionsInfoProvider") STMActionsInfoProvider productVariantInfoProvider,
            @Qualifier("productVariantEntityStore") EntityStore<ProductVariant> entityStore) {
        return new StateEntityServiceImpl(stm, productVariantInfoProvider, entityStore);
    }

    // Now we start constructing the STM Components

    @Bean
    @Autowired
    GenericEntryAction<ProductVariant> productVariantEntryAction(@Qualifier("productVariantEntityStore") EntityStore<ProductVariant> entityStore,
                                               @Qualifier("productVariantActionsInfoProvider") STMActionsInfoProvider productVariantInfoProvider) {
        return new GenericEntryAction<ProductVariant>(entityStore, productVariantInfoProvider);
    }

    @Bean
    GenericExitAction<ProductVariant> productVariantExitAction() {
        return new GenericExitAction<ProductVariant>();
    }

    @Bean
    @Autowired
    StmBodyTypeSelector productVariantBodyTypeSelector(@Qualifier("productVariantActionsInfoProvider") STMActionsInfoProvider productVariantInfoProvider) {
        return new StmBodyTypeSelector(productVariantInfoProvider);
    }

    @Bean

    STMTransitionAction<ProductVariant> productVariantBaseTransitionAction() {
        return new BaseTransitionAction<>();
    }


    @Bean
    XmlFlowReader productVariantFlowReader(@Qualifier("productVariantFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}

