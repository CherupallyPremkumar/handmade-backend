package com.homebase.ecom.configuration;



import com.homebase.ecom.domain.Price;
import com.homebase.ecom.entitystore.PriceEntityStore;
import com.homebase.ecom.repository.PriceRepository;
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
public class PriceConfiguration {


    private static final String FLOW_DEFINITION_FILE = "stm/price-flow.xml";

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> priceAuthorities(STMActionsInfoProvider priceActionsInfoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(priceActionsInfoProvider);
        return builder.build();
    }


    @Bean
    BeanFactoryAdapter priceBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }




    @Bean
    STMFlowStoreImpl priceFlowStore(@Qualifier("priceBeanFactoryAdapter") BeanFactoryAdapter priceBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(priceBeanFactoryAdapter);
        return stmFlowStore;
    }


    @Bean
    @Autowired
    STM<Price> priceEntityStm(@Qualifier("priceFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<Price> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider priceActionsInfoProvider(@Qualifier("priceFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new STMActionsInfoProvider(stmFlowStore);
    }

    @Bean
    EntityStore<Price> priceEntityStore(PriceRepository priceRepository) {
        return new PriceEntityStore(priceRepository);
    }

    @Bean
    @Autowired
    StateEntityService _priceStateEntityService_(
            @Qualifier("priceEntityStm") STM<Price> stm,
            @Qualifier("priceActionsInfoProvider") STMActionsInfoProvider priceInfoProvider,
            @Qualifier("priceEntityStore") EntityStore<Price> entityStore) {
        return new StateEntityServiceImpl(stm, priceInfoProvider, entityStore);
    }

    // Now we start constructing the STM Components

    @Bean
    @Autowired
    GenericEntryAction<Price> priceEntryAction(@Qualifier("priceEntityStore") EntityStore<Price> entityStore,
                                               @Qualifier("priceActionsInfoProvider") STMActionsInfoProvider priceInfoProvider) {
        return new GenericEntryAction<Price>(entityStore, priceInfoProvider);
    }

    @Bean
    GenericExitAction<Price> priceExitAction() {
        return new GenericExitAction<Price>();
    }

    @Bean
    @Autowired
    StmBodyTypeSelector priceBodyTypeSelector(@Qualifier("priceActionsInfoProvider") STMActionsInfoProvider priceInfoProvider) {
        return new StmBodyTypeSelector(priceInfoProvider);
    }

    @Bean
    STMTransitionAction<Price> priceBaseTransitionAction() {
        return new BaseTransitionAction<>();
    }


    @Bean
    XmlFlowReader priceFlowReader(@Qualifier("priceFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}

