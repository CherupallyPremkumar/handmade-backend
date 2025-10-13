package com.homebase.ecom.configuration;



import com.homebase.ecom.domain.Inventory;
import com.homebase.ecom.entitystore.InventoryEntityStore;
import com.homebase.ecom.repository.InventoryRepository;
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
public class InventoryConfiguration {


    private static final String FLOW_DEFINITION_FILE = "stm/inventory-flow.xml";

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> inventoryAuthorities(STMActionsInfoProvider inventoryActionsInfoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(inventoryActionsInfoProvider);
        return builder.build();
    }


    @Bean
    BeanFactoryAdapter inventoryBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }




    @Bean
    STMFlowStoreImpl inventoryFlowStore(@Qualifier("inventoryBeanFactoryAdapter") BeanFactoryAdapter inventoryBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(inventoryBeanFactoryAdapter);
        return stmFlowStore;
    }


    @Bean
    @Autowired
    STM<Inventory> inventoryEntityStm(@Qualifier("inventoryFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<Inventory> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider inventoryActionsInfoProvider(@Qualifier("inventoryFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new STMActionsInfoProvider(stmFlowStore);
    }

    @Bean
    EntityStore<Inventory> inventoryEntityStore(InventoryRepository inventoryRepository) {
        return new InventoryEntityStore(inventoryRepository);
    }

    @Bean
    @Autowired
    StateEntityService _inventoryStateEntityService_(
            @Qualifier("inventoryEntityStm") STM<Inventory> stm,
            @Qualifier("inventoryActionsInfoProvider") STMActionsInfoProvider inventoryInfoProvider,
            @Qualifier("inventoryEntityStore") EntityStore<Inventory> entityStore) {
        return new StateEntityServiceImpl(stm, inventoryInfoProvider, entityStore);
    }

    // Now we start constructing the STM Components

    @Bean
    @Autowired
    GenericEntryAction<Inventory> inventoryEntryAction(@Qualifier("inventoryEntityStore") EntityStore<Inventory> entityStore,
                                               @Qualifier("inventoryActionsInfoProvider") STMActionsInfoProvider inventoryInfoProvider) {
        return new GenericEntryAction<Inventory>(entityStore, inventoryInfoProvider);
    }

    @Bean
    GenericExitAction<Inventory> inventoryExitAction() {
        return new GenericExitAction<Inventory>();
    }

    @Bean
    @Autowired
    StmBodyTypeSelector inventoryBodyTypeSelector(@Qualifier("inventoryActionsInfoProvider") STMActionsInfoProvider inventoryInfoProvider) {
        return new StmBodyTypeSelector(inventoryInfoProvider);
    }

    @Bean
    STMTransitionAction<Inventory> inventoryBaseTransitionAction() {
        return new BaseTransitionAction<>();
    }


    @Bean
    XmlFlowReader inventoryFlowReader(@Qualifier("inventoryFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}

