package com.homebase.ecom.configuration;



import com.homebase.ecom.domain.Admin;
import com.homebase.ecom.entitystore.AdminEntityStore;
import com.homebase.ecom.repository.AdminRepository;
import com.homebase.ecom.service.impl.AdminStateServiceImpl;
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
public class AdminConfiguration {

    private static final String FLOW_DEFINITION_FILE = "state/admin-flow.xml";

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> adminAuthorities(STMActionsInfoProvider adminActionsInfoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(adminActionsInfoProvider);
        return builder.build();
    }


    @Bean
    BeanFactoryAdapter adminBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }




    @Bean
    STMFlowStoreImpl adminFlowStore(@Qualifier("adminBeanFactoryAdapter") BeanFactoryAdapter adminBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(adminBeanFactoryAdapter);
        return stmFlowStore;
    }


    @Bean
    @Autowired
    STM<Admin> adminEntityStm(@Qualifier("adminFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<Admin> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider adminActionsInfoProvider(@Qualifier("adminFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new STMActionsInfoProvider(stmFlowStore);
    }

    @Bean
    EntityStore<Admin> adminEntityStore(AdminRepository adminRepository) {
        return new AdminEntityStore(adminRepository);
    }

    @Bean
    @Autowired
    StateEntityService<Admin> _adminStateEntityService_(
            @Qualifier("adminEntityStm") STM<Admin> stm,
            @Qualifier("adminActionsInfoProvider") STMActionsInfoProvider adminInfoProvider,
            @Qualifier("adminEntityStore") EntityStore<Admin> entityStore) {
        return new AdminStateServiceImpl(stm, adminInfoProvider, entityStore);
    }

    // Now we start constructing the STM Components

    @Bean
    @Autowired
    GenericEntryAction<Admin> adminEntryAction(@Qualifier("adminEntityStore") EntityStore<Admin> entityStore,
                                               @Qualifier("adminActionsInfoProvider") STMActionsInfoProvider adminInfoProvider) {
        return new GenericEntryAction<Admin>(entityStore, adminInfoProvider);
    }

    @Bean
    GenericExitAction<Admin> adminExitAction() {
        return new GenericExitAction<Admin>();
    }

    @Bean
    @Autowired
    StmBodyTypeSelector adminBodyTypeSelector(@Qualifier("adminActionsInfoProvider") STMActionsInfoProvider adminInfoProvider) {
        return new StmBodyTypeSelector(adminInfoProvider);
    }

    @Bean
    STMTransitionAction<Admin> adminBaseTransitionAction() {
        return new BaseTransitionAction<>();
    }


    @Bean
    XmlFlowReader adminFlowReader(@Qualifier("adminFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}

