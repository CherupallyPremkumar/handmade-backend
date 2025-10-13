package com.homebase.ecom.configuration;



import com.homebase.ecom.domain.Order;
import com.homebase.ecom.entitystore.OrderEntityStore;
import com.homebase.ecom.repository.OrderRepository;
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
public class OrderConfiguration {


    private static final String FLOW_DEFINITION_FILE = "stm/order-flow.xml";

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> orderAuthorities(STMActionsInfoProvider orderActionsInfoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(orderActionsInfoProvider);
        return builder.build();
    }


    @Bean
    BeanFactoryAdapter orderBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }




    @Bean
    STMFlowStoreImpl orderFlowStore(@Qualifier("orderBeanFactoryAdapter") BeanFactoryAdapter orderBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(orderBeanFactoryAdapter);
        return stmFlowStore;
    }


    @Bean
    @Autowired
    STM<Order> orderEntityStm(@Qualifier("orderFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<Order> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider orderActionsInfoProvider(@Qualifier("orderFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new STMActionsInfoProvider(stmFlowStore);
    }

    @Bean
    @Autowired
    EntityStore<Order> orderEntityStore(OrderRepository orderRepository) {
        return new OrderEntityStore(orderRepository);
    }

    @Bean
    @Autowired
    StateEntityService _orderStateEntityService_(
            @Qualifier("orderEntityStm") STM<Order> stm,
            @Qualifier("orderActionsInfoProvider") STMActionsInfoProvider orderInfoProvider,
            @Qualifier("orderEntityStore") EntityStore<Order> entityStore) {
        return new StateEntityServiceImpl(stm, orderInfoProvider, entityStore);
    }

    // Now we start constructing the STM Components

    @Bean
    @Autowired
    GenericEntryAction<Order> orderEntryAction(@Qualifier("orderEntityStore") EntityStore<Order> entityStore,
                                               @Qualifier("orderActionsInfoProvider") STMActionsInfoProvider orderInfoProvider) {
        return new GenericEntryAction<Order>(entityStore, orderInfoProvider);
    }

    @Bean
    GenericExitAction<Order> orderExitAction() {
        return new GenericExitAction<Order>();
    }

    @Bean
    @Autowired
    StmBodyTypeSelector orderBodyTypeSelector(@Qualifier("orderActionsInfoProvider") STMActionsInfoProvider orderInfoProvider) {
        return new StmBodyTypeSelector(orderInfoProvider);
    }

    @Bean
    @Autowired
    STMTransitionAction<Order> orderBaseTransitionAction() {
        return new BaseTransitionAction<>();
    }


    @Bean
    XmlFlowReader orderFlowReader(@Qualifier("orderFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}

