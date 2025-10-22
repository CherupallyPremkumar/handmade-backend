package com.homebase.ecom.configuration;

import com.homebase.ecom.domain.Cart;
import com.homebase.ecom.entitystore.CartEntityStore;
import com.homebase.ecom.entitystore.CartItemEntityStore;
import com.homebase.ecom.entitystore.impl.CartEntityStoreImpl;
import com.homebase.ecom.repository.CartRepository;
import com.homebase.ecom.service.CartStateService;
import com.homebase.ecom.service.impl.CartStateServiceImpl;
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
public class CartConfiguration {

    private static final String FLOW_DEFINITION_FILE = "state/cart-flow.xml";

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> cartAuthorities(STMActionsInfoProvider cartActionsInfoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(cartActionsInfoProvider);
        return builder.build();
    }

    @Bean
    BeanFactoryAdapter cartBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    @Bean
    STMFlowStoreImpl cartFlowStore(@Qualifier("cartBeanFactoryAdapter") BeanFactoryAdapter cartBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(cartBeanFactoryAdapter);
        return stmFlowStore;
    }

    @Bean
    @Autowired
    STM<Cart> cartEntityStm(@Qualifier("cartFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<Cart> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider cartActionsInfoProvider(@Qualifier("cartFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new STMActionsInfoProvider(stmFlowStore);
    }



    @Bean
    @Autowired
    CartEntityStore cartEntityStore(CartRepository cartRepository) {
        return new CartEntityStoreImpl(cartRepository);
    }

    @Bean
    @Autowired
    CartStateService _cartStateEntityService_(
            @Qualifier("cartEntityStm") STM<Cart> stm,
            @Qualifier("cartActionsInfoProvider") STMActionsInfoProvider cartInfoProvider,
            @Qualifier("cartEntityStore") CartEntityStore entityStore,
            @Qualifier("cartItemEntityStore") CartItemEntityStore cartItemEntityStore,
            ContextContainer contextContainer
    ) {
        return new CartStateServiceImpl(stm, cartInfoProvider, entityStore,cartItemEntityStore,contextContainer);
    }

    // Now we start constructing the STM Components

    @Bean
    @Autowired
    GenericEntryAction<Cart> cartEntryAction(@Qualifier("cartEntityStore") EntityStore<Cart> entityStore,
            @Qualifier("cartActionsInfoProvider") STMActionsInfoProvider cartInfoProvider) {
        return new GenericEntryAction<Cart>(entityStore, cartInfoProvider);
    }

    @Bean
    GenericExitAction<Cart> cartExitAction() {
        return new GenericExitAction<Cart>();
    }

    @Bean
    @Autowired
    StmBodyTypeSelector cartBodyTypeSelector(@Qualifier("cartActionsInfoProvider") STMActionsInfoProvider cartInfoProvider) {
        return new StmBodyTypeSelector(cartInfoProvider);
    }

    @Bean
    STMTransitionAction<Cart> cartBaseTransitionAction() {
        return new BaseTransitionAction<>();
    }

    @Bean
    XmlFlowReader cartFlowReader(@Qualifier("cartFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}
