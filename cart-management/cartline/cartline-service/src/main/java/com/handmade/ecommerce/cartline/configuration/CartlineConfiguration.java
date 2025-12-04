package com.handmade.ecommerce.cartline.configuration;

import org.chenile.stm.*;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.impl.*;
import org.chenile.stm.spring.SpringBeanFactoryAdapter;
import org.chenile.workflow.param.MinimalPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.chenile.workflow.service.stmcmds.*;
import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.cartline.service.cmds.AssignCartlineAction;
import com.handmade.ecommerce.cartline.service.cmds.DefaultSTMTransitionAction;
import com.handmade.ecommerce.cartline.service.cmds.CloseCartlineAction;
import com.handmade.ecommerce.cartline.service.cmds.ResolveCartlineAction;
import com.handmade.ecommerce.cartline.service.healthcheck.CartlineHealthChecker;
import com.handmade.ecommerce.cartline.service.store.CartlineEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.stmcmds.StmAuthoritiesBuilder;
import java.util.function.Function;
import org.chenile.core.context.ChenileExchange;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class CartlineConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/cartline/cartline-states.xml";
	
	@Bean BeanFactoryAdapter cartlineBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl cartlineFlowStore(@Qualifier("cartlineBeanFactoryAdapter") BeanFactoryAdapter cartlineBeanFactoryAdapter) throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(cartlineBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Cartline> cartlineEntityStm(@Qualifier("cartlineFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Cartline> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider cartlineActionsInfoProvider(@Qualifier("cartlineFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("cartline",provider);
        return provider;
	}
	
	@Bean EntityStore<Cartline> cartlineEntityStore() {
		return new CartlineEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Cartline> _cartlineStateEntityService_(
			@Qualifier("cartlineEntityStm") STM<Cartline> stm,
			@Qualifier("cartlineActionsInfoProvider") STMActionsInfoProvider cartlineInfoProvider,
			@Qualifier("cartlineEntityStore") EntityStore<Cartline> entityStore){
		return new StateEntityServiceImpl<>(stm, cartlineInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Cartline> cartlineEntryAction(@Qualifier("cartlineEntityStore") EntityStore<Cartline> entityStore,
			@Qualifier("cartlineActionsInfoProvider") STMActionsInfoProvider cartlineInfoProvider){
		return new GenericEntryAction<Cartline>(entityStore,cartlineInfoProvider);
	}
	
	@Bean GenericExitAction<Cartline> cartlineExitAction(){
		return new GenericExitAction<Cartline>();
	}

	@Bean
	XmlFlowReader cartlineFlowReader(@Qualifier("cartlineFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean CartlineHealthChecker cartlineHealthChecker(){
    	return new CartlineHealthChecker();
    }

    @Bean STMTransitionAction<Cartline> defaultcartlineSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver cartlineTransitionActionResolver(
    @Qualifier("defaultcartlineSTMTransitionAction") STMTransitionAction<Cartline> defaultSTMTransitionAction){
        return new STMTransitionActionResolver("cartline",defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector cartlineBodyTypeSelector(
    @Qualifier("cartlineActionsInfoProvider") STMActionsInfoProvider cartlineInfoProvider,
    @Qualifier("cartlineTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(cartlineInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Cartline> cartlineBaseTransitionAction(
    @Qualifier("cartlineTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
        return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "cartline" + eventId for the method name. (cartline is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/cartline/cartline-states.xml

    @Bean ResolveCartlineAction cartlineResolve() {
        return new ResolveCartlineAction();
    }

    @Bean CloseCartlineAction cartlineClose() {
        return new CloseCartlineAction();
    }

    @Bean AssignCartlineAction cartlineAssign() {
        return new AssignCartlineAction();
    }


    @Bean @Autowired Function<ChenileExchange, String[]> cartlineEventAuthoritiesSupplier(
        @Qualifier("cartlineActionsInfoProvider") STMActionsInfoProvider cartlineInfoProvider)
                    throws Exception{
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(cartlineInfoProvider);
        return builder.build();
    }
}
