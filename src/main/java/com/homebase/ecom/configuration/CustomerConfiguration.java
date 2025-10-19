package com.homebase.ecom.configuration;



import com.homebase.ecom.domain.Customer;
import com.homebase.ecom.entitystore.CustomerEntityStore;
import com.homebase.ecom.repository.CustomerRepository;
import com.homebase.ecom.service.CustomerStateService;
import com.homebase.ecom.service.impl.CustomerStateServiceImpl;
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
public class CustomerConfiguration {


    private static final String FLOW_DEFINITION_FILE = "state/customer-flow.xml";

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> customerAuthorities(STMActionsInfoProvider customerActionsInfoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(customerActionsInfoProvider);
        return builder.build();
    }


    @Bean
    BeanFactoryAdapter customerBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }




    @Bean
    STMFlowStoreImpl customerFlowStore(@Qualifier("customerBeanFactoryAdapter") BeanFactoryAdapter customerBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(customerBeanFactoryAdapter);
        return stmFlowStore;
    }


    @Bean
    @Autowired
    STM<Customer> customerEntityStm(@Qualifier("customerFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<Customer> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider customerActionsInfoProvider(@Qualifier("customerFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new STMActionsInfoProvider(stmFlowStore);
    }

    @Bean
    @Autowired
    EntityStore<Customer> customerEntityStore(CustomerRepository customerRepository) {
        return new CustomerEntityStore(customerRepository);
    }

    @Bean
    @Autowired
    CustomerStateService<Customer> _customerStateEntityService_(
            @Qualifier("customerEntityStm") STM<Customer> stm,
            @Qualifier("customerActionsInfoProvider") STMActionsInfoProvider customerInfoProvider,
            @Qualifier("customerEntityStore") EntityStore<Customer> entityStore) {
        return new CustomerStateServiceImpl(stm, customerInfoProvider, entityStore);
    }

    // Now we start constructi
    //ng the STM Components

    @Bean
    @Autowired
    GenericEntryAction<Customer> customerEntryAction(@Qualifier("customerEntityStore") EntityStore<Customer> entityStore,
                                                     @Qualifier("customerActionsInfoProvider") STMActionsInfoProvider customerInfoProvider) {
        return new GenericEntryAction<Customer>(entityStore, customerInfoProvider);
    }

    @Bean
    GenericExitAction<Customer> customerExitAction() {
        return new GenericExitAction<Customer>();
    }

    @Bean
    @Autowired
    StmBodyTypeSelector customerBodyTypeSelector(@Qualifier("customerActionsInfoProvider") STMActionsInfoProvider customerInfoProvider) {
        return new StmBodyTypeSelector(customerInfoProvider);
    }

    @Bean

    STMTransitionAction<Customer> customerBaseTransitionAction() {
        return new BaseTransitionAction<>();
    }


    @Bean
    @Autowired
    XmlFlowReader customerFlowReader(@Qualifier("customerFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}

