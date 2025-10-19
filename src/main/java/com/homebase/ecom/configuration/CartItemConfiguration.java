package com.homebase.ecom.configuration;



import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.entitystore.CartItemEntityStore;
import com.homebase.ecom.repository.CartItemRepository;
import com.homebase.ecom.service.impl.CartItemStateServiceImpl;
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
public class CartItemConfiguration {


    private static final String FLOW_DEFINITION_FILE = "state/cart-item-flow.xml";

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> cartItemAuthorities(STMActionsInfoProvider cartItemActionsInfoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(cartItemActionsInfoProvider);
        return builder.build();
    }


    @Bean
    BeanFactoryAdapter cartItemBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }




    @Bean
    STMFlowStoreImpl cartItemFlowStore(@Qualifier("cartItemBeanFactoryAdapter") BeanFactoryAdapter cartItemBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(cartItemBeanFactoryAdapter);
        return stmFlowStore;
    }


    @Bean
    @Autowired
    STM<CartItem> cartItemEntityStm(@Qualifier("cartItemFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<CartItem> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider cartItemActionsInfoProvider(@Qualifier("cartItemFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new STMActionsInfoProvider(stmFlowStore);
    }

    @Bean
    EntityStore<CartItem> cartItemEntityStore(CartItemRepository cartItemRepository) {
        return new CartItemEntityStore(cartItemRepository);
    }

    @Bean
    @Autowired
    StateEntityService<CartItem> _cartItemStateEntityService_(
            @Qualifier("cartItemEntityStm") STM<CartItem> stm,
            @Qualifier("cartItemActionsInfoProvider") STMActionsInfoProvider cartItemInfoProvider,
            @Qualifier("cartItemEntityStore") EntityStore<CartItem> entityStore) {
        return new CartItemStateServiceImpl(stm, cartItemInfoProvider, entityStore);
    }

    // Now we start constructing the STM Components

    @Bean
    @Autowired
    GenericEntryAction<CartItem> cartItemEntryAction(@Qualifier("cartItemEntityStore") EntityStore<CartItem> entityStore,
                                               @Qualifier("cartItemActionsInfoProvider") STMActionsInfoProvider cartItemInfoProvider) {
        return new GenericEntryAction<CartItem>(entityStore, cartItemInfoProvider);
    }

    @Bean
    GenericExitAction<CartItem> cartItemExitAction() {
        return new GenericExitAction<CartItem>();
    }

    @Bean
    @Autowired
    StmBodyTypeSelector cartItemBodyTypeSelector(@Qualifier("cartItemActionsInfoProvider") STMActionsInfoProvider cartItemInfoProvider) {
        return new StmBodyTypeSelector(cartItemInfoProvider);
    }

    @Bean
    STMTransitionAction<CartItem> cartItemBaseTransitionAction() {
        return new BaseTransitionAction<>();
    }


    @Bean
    XmlFlowReader cartItemFlowReader(@Qualifier("cartItemFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}

