package com.homebase.ecom.configuration;

import com.homebase.ecom.domain.Artisan;
import com.homebase.ecom.entitystore.ArtisanEntityStore;
import com.homebase.ecom.repository.ArtisanRepository;
import com.homebase.ecom.service.impl.ArtisanStateServiceImpl;
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
public class ArtisanConfiguration {

    private static final String FLOW_DEFINITION_FILE = "state/artisan-flow.xml";

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> artisanAuthorities(STMActionsInfoProvider artisanActionsInfoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(artisanActionsInfoProvider);
        return builder.build();
    }

    @Bean
    BeanFactoryAdapter artisanBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    @Bean
    STMFlowStoreImpl artisanFlowStore(@Qualifier("artisanBeanFactoryAdapter") BeanFactoryAdapter artisanBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(artisanBeanFactoryAdapter);
        return stmFlowStore;
    }

    @Bean
    @Autowired
    STM<Artisan> artisanEntityStm(@Qualifier("artisanFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<Artisan> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider artisanActionsInfoProvider(@Qualifier("artisanFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new STMActionsInfoProvider(stmFlowStore);
    }

    @Bean
    @Autowired
    EntityStore<Artisan> artisanEntityStore(ArtisanRepository artisanRepository) {
        return new ArtisanEntityStore(artisanRepository);
    }

    @Bean
    @Autowired
    StateEntityService<Artisan> _artisanStateEntityService_(
            @Qualifier("artisanEntityStm") STM<Artisan> stm,
            @Qualifier("artisanActionsInfoProvider") STMActionsInfoProvider artisanInfoProvider,
            @Qualifier("artisanEntityStore") EntityStore<Artisan> entityStore) {
        return new ArtisanStateServiceImpl(stm, artisanInfoProvider, entityStore);
    }

    // Now we start constructing the STM Components

    @Bean
    @Autowired
    GenericEntryAction<Artisan> artisanEntryAction(@Qualifier("artisanEntityStore") EntityStore<Artisan> entityStore,
                                             @Qualifier("artisanActionsInfoProvider") STMActionsInfoProvider artisanInfoProvider) {
        return new GenericEntryAction<Artisan>(entityStore, artisanInfoProvider);
    }

    @Bean
    GenericExitAction<Artisan> artisanExitAction() {
        return new GenericExitAction<Artisan>();
    }

    @Bean
    @Autowired
    StmBodyTypeSelector artisanBodyTypeSelector(@Qualifier("artisanActionsInfoProvider") STMActionsInfoProvider artisanInfoProvider) {
        return new StmBodyTypeSelector(artisanInfoProvider);
    }

    @Bean
    STMTransitionAction<Artisan> artisanBaseTransitionAction() {
        return new BaseTransitionAction<>();
    }

    @Bean
    XmlFlowReader artisanFlowReader(@Qualifier("artisanFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}
