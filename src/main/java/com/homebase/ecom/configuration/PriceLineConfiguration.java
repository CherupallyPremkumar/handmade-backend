package com.homebase.ecom.configuration;



import com.homebase.ecom.domain.PriceLine;
import com.homebase.ecom.entitystore.PriceLineEntityStore;
import com.homebase.ecom.entitystore.impl.PriceLineEntityStoreImpl;
import com.homebase.ecom.repository.PriceLineRepository;
import com.homebase.ecom.service.PriceLineStateService;

import com.homebase.ecom.service.impl.BasePriceLineCalculator;
import com.homebase.ecom.service.impl.PriceCalculationService;
import com.homebase.ecom.service.impl.PriceLineStateServiceImpl;
import org.chenile.core.context.ChenileExchange;
import org.chenile.stm.STM;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.impl.BeanFactoryAdapter;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.stm.impl.STMFlowStoreImpl;
import org.chenile.stm.impl.STMImpl;
import org.chenile.stm.impl.XmlFlowReader;
import org.chenile.stm.spring.SpringBeanFactoryAdapter;
import org.chenile.utils.entity.service.EntityStore;
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
public class PriceLineConfiguration {


    private static final String FLOW_DEFINITION_FILE = "state/priceLine-flow.xml";

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> priceLineAuthorities(STMActionsInfoProvider priceLineActionsInfoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(priceLineActionsInfoProvider);
        return builder.build();
    }


    @Bean
    BeanFactoryAdapter priceLineBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }




    @Bean
    STMFlowStoreImpl priceLineFlowStore(@Qualifier("priceLineBeanFactoryAdapter") BeanFactoryAdapter priceLineBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(priceLineBeanFactoryAdapter);
        return stmFlowStore;
    }


    @Bean
    @Autowired
    STM<PriceLine> priceLineEntityStm(@Qualifier("priceLineFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<PriceLine> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider priceLineActionsInfoProvider(@Qualifier("priceLineFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new STMActionsInfoProvider(stmFlowStore);
    }

    @Bean
    @Autowired
    EntityStore<PriceLine> priceLineEntityStore(PriceLineRepository priceLineRepository) {
        return new PriceLineEntityStoreImpl(priceLineRepository);
    }

    @Bean
    @Autowired
    PriceLineStateService _priceLineStateEntityService_(
            @Qualifier("priceLineEntityStm") STM<PriceLine> stm,
            @Qualifier("priceLineActionsInfoProvider") STMActionsInfoProvider priceLineInfoProvider,
            @Qualifier("priceLineEntityStore") EntityStore<PriceLine> priceLineEntityStore,
            PriceCalculationService priceCalculationService
    ) {
        return new PriceLineStateServiceImpl(stm, priceLineInfoProvider, priceLineEntityStore, priceCalculationService);
    }

    @Bean
    @Autowired
    BasePriceLineCalculator basePriceLineCalculator(
            @Qualifier("priceLineEntityStm") STM<PriceLine> stm,
            @Qualifier("priceLineActionsInfoProvider") STMActionsInfoProvider priceLineInfoProvider,
            @Qualifier("priceLineEntityStore") EntityStore<PriceLine> entityStore,PriceCalculationService priceCalculationService) {
        return new PriceLineStateServiceImpl(stm, priceLineInfoProvider, entityStore,priceCalculationService);
    }

    // Now we start constructing the STM Components

    @Bean
    @Autowired
    GenericEntryAction<PriceLine> priceLineEntryAction(@Qualifier("priceLineEntityStore") EntityStore<PriceLine> entityStore,
                                               @Qualifier("priceLineActionsInfoProvider") STMActionsInfoProvider priceLineInfoProvider) {
        return new GenericEntryAction<PriceLine>(entityStore, priceLineInfoProvider);
    }

    @Bean
    GenericExitAction<PriceLine> priceLineExitAction() {
        return new GenericExitAction<PriceLine>();
    }

    @Bean
    @Autowired
    StmBodyTypeSelector priceLineBodyTypeSelector(@Qualifier("priceLineActionsInfoProvider") STMActionsInfoProvider priceLineInfoProvider) {
        return new StmBodyTypeSelector(priceLineInfoProvider);
    }

    @Bean
    STMTransitionAction<PriceLine> priceLineBaseTransitionAction() {
        return new BaseTransitionAction<>();
    }


    @Bean
    XmlFlowReader priceLineFlowReader(@Qualifier("priceLineFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }



}

