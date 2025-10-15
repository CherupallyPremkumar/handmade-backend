package com.homebase.ecom.configuration;



import com.homebase.ecom.domain.Tenant;
import com.homebase.ecom.entitystore.TenantEntityStore;
import com.homebase.ecom.repository.TenantRepository;
import com.homebase.ecom.service.impl.TenantStateServiceImpl;
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
public class TenantConfiguration {


    private static final String FLOW_DEFINITION_FILE = "state/tenant-flow.xml";

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> tenantAuthorities(STMActionsInfoProvider tenantActionsInfoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(tenantActionsInfoProvider);
        return builder.build();
    }


    @Bean
    BeanFactoryAdapter tenantBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }




    @Bean
    STMFlowStoreImpl tenantFlowStore(@Qualifier("tenantBeanFactoryAdapter") BeanFactoryAdapter tenantBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(tenantBeanFactoryAdapter);
        return stmFlowStore;
    }


    @Bean
    @Autowired
    STM<Tenant> tenantEntityStm(@Qualifier("tenantFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<Tenant> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider tenantActionsInfoProvider(@Qualifier("tenantFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new STMActionsInfoProvider(stmFlowStore);
    }

    @Bean
    @Autowired
    EntityStore<Tenant> tenantEntityStore(TenantRepository tenantRepository) {
        return new TenantEntityStore(tenantRepository);
    }

    @Bean
    @Autowired
    StateEntityService<Tenant> _tenantStateEntityService_(
            @Qualifier("tenantEntityStm") STM<Tenant> stm,
            @Qualifier("tenantActionsInfoProvider") STMActionsInfoProvider tenantInfoProvider,
            @Qualifier("tenantEntityStore") EntityStore<Tenant> entityStore) {
        return new TenantStateServiceImpl(stm, tenantInfoProvider, entityStore);
    }

    // Now we start constructing the STM Components

    @Bean
    @Autowired
    GenericEntryAction<Tenant> tenantEntryAction(@Qualifier("tenantEntityStore") EntityStore<Tenant> entityStore,
                                               @Qualifier("tenantActionsInfoProvider") STMActionsInfoProvider tenantInfoProvider) {
        return new GenericEntryAction<Tenant>(entityStore, tenantInfoProvider);
    }

    @Bean
    GenericExitAction<Tenant> tenantExitAction() {
        return new GenericExitAction<Tenant>();
    }

    @Bean
    @Autowired
    StmBodyTypeSelector tenantBodyTypeSelector(@Qualifier("tenantActionsInfoProvider") STMActionsInfoProvider tenantInfoProvider) {
        return new StmBodyTypeSelector(tenantInfoProvider);
    }

    @Bean
    STMTransitionAction<Tenant> tenantBaseTransitionAction() {
        return new BaseTransitionAction<>();
    }


    @Bean
    XmlFlowReader tenantFlowReader(@Qualifier("tenantFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}

