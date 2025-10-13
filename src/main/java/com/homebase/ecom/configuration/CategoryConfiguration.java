package com.homebase.ecom.configuration;



import com.homebase.ecom.domain.Category;
import com.homebase.ecom.entitystore.CategoryEntityStore;
import com.homebase.ecom.repository.CategoryRepository;
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
public class CategoryConfiguration {


    private static final String FLOW_DEFINITION_FILE = "stm/category-flow.xml";

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> categoryAuthorities(STMActionsInfoProvider categoryActionsInfoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(categoryActionsInfoProvider);
        return builder.build();
    }


    @Bean
    BeanFactoryAdapter categoryBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }




    @Bean
    STMFlowStoreImpl categoryFlowStore(@Qualifier("categoryBeanFactoryAdapter") BeanFactoryAdapter categoryBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(categoryBeanFactoryAdapter);
        return stmFlowStore;
    }


    @Bean
    @Autowired
    STM<Category> categoryEntityStm(@Qualifier("categoryFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<Category> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider categoryActionsInfoProvider(@Qualifier("categoryFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new STMActionsInfoProvider(stmFlowStore);
    }

    @Bean
    @Autowired
    EntityStore<Category> categoryEntityStore(CategoryRepository categoryRepository) {
        return new CategoryEntityStore(categoryRepository);
    }

    @Bean
    @Autowired
    StateEntityService _categoryStateEntityService_(
            @Qualifier("categoryEntityStm") STM<Category> stm,
            @Qualifier("categoryActionsInfoProvider") STMActionsInfoProvider categoryInfoProvider,
            @Qualifier("categoryEntityStore") EntityStore<Category> entityStore) {
        return new StateEntityServiceImpl(stm, categoryInfoProvider, entityStore);
    }

    // Now we start constructing the STM Components

    @Bean
    @Autowired
    GenericEntryAction<Category> categoryEntryAction(@Qualifier("categoryEntityStore") EntityStore<Category> entityStore,
                                               @Qualifier("categoryActionsInfoProvider") STMActionsInfoProvider categoryInfoProvider) {
        return new GenericEntryAction<Category>(entityStore, categoryInfoProvider);
    }

    @Bean
    GenericExitAction<Category> categoryExitAction() {
        return new GenericExitAction<Category>();
    }

    @Bean
    @Autowired
    StmBodyTypeSelector categoryBodyTypeSelector(@Qualifier("categoryActionsInfoProvider") STMActionsInfoProvider categoryInfoProvider) {
        return new StmBodyTypeSelector(categoryInfoProvider);
    }

    @Bean

    STMTransitionAction<Category> categoryBaseTransitionAction() {
        return new BaseTransitionAction<>();
    }


    @Bean
    XmlFlowReader categoryFlowReader(@Qualifier("categoryFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}

