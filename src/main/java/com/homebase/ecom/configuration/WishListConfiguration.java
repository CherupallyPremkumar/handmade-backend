package com.homebase.ecom.configuration;



import com.homebase.ecom.domain.Wishlist;
import com.homebase.ecom.entitystore.WishlistEntityStore;
import com.homebase.ecom.repository.WishlistRepository;
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
public class WishListConfiguration {


    private static final String FLOW_DEFINITION_FILE = "stm/wishlist-flow.xml";

    @Bean
    @Autowired
    Function<ChenileExchange, String[]> wishlistAuthorities(STMActionsInfoProvider wishlistActionsInfoProvider) throws Exception {
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(wishlistActionsInfoProvider);
        return builder.build();
    }


    @Bean
    BeanFactoryAdapter wishlistBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }




    @Bean
    STMFlowStoreImpl wishlistFlowStore(@Qualifier("wishlistBeanFactoryAdapter") BeanFactoryAdapter wishlistBeanFactoryAdapter) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(wishlistBeanFactoryAdapter);
        return stmFlowStore;
    }


    @Bean
    @Autowired
    STM<Wishlist> wishlistEntityStm(@Qualifier("wishlistFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<Wishlist> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean
    @Autowired
    STMActionsInfoProvider wishlistActionsInfoProvider(@Qualifier("wishlistFlowStore") STMFlowStoreImpl stmFlowStore) {
        return new STMActionsInfoProvider(stmFlowStore);
    }

    @Bean
    EntityStore<Wishlist> wishlistEntityStore(WishlistRepository wishlistRepository) {
        return new WishlistEntityStore(wishlistRepository);
    }

    @Bean
    @Autowired
    StateEntityService _wishlistStateEntityService_(
            @Qualifier("wishlistEntityStm") STM<Wishlist> stm,
            @Qualifier("wishlistActionsInfoProvider") STMActionsInfoProvider wishlistInfoProvider,
            @Qualifier("wishlistEntityStore") EntityStore<Wishlist> entityStore) {
        return new StateEntityServiceImpl(stm, wishlistInfoProvider, entityStore);
    }

    // Now we start constructing the STM Components

    @Bean
    @Autowired
    GenericEntryAction<Wishlist> wishlistEntryAction(@Qualifier("wishlistEntityStore") EntityStore<Wishlist> entityStore,
                                               @Qualifier("wishlistActionsInfoProvider") STMActionsInfoProvider wishlistInfoProvider) {
        return new GenericEntryAction<Wishlist>(entityStore, wishlistInfoProvider);
    }

    @Bean
    GenericExitAction<Wishlist> wishlistExitAction() {
        return new GenericExitAction<Wishlist>();
    }

    @Bean
    @Autowired
    StmBodyTypeSelector wishlistBodyTypeSelector(@Qualifier("wishlistActionsInfoProvider") STMActionsInfoProvider wishlistInfoProvider) {
        return new StmBodyTypeSelector(wishlistInfoProvider);
    }

    @Bean
    STMTransitionAction<Wishlist> wishlistBaseTransitionAction() {
        return new BaseTransitionAction<>();
    }


    @Bean
    XmlFlowReader wishlistFlowReader(@Qualifier("wishlistFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }
}

