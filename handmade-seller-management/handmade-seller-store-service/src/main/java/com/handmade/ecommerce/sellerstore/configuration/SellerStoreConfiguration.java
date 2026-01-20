package com.handmade.ecommerce.sellerstore.configuration;

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
import com.handmade.ecommerce.seller.model.SellerStore;
import com.handmade.ecommerce.sellerstore.service.cmds.*;
import com.handmade.ecommerce.sellerstore.service.healthcheck.SellerStoreHealthChecker;
import com.handmade.ecommerce.sellerstore.service.store.SellerStoreEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class SellerStoreConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/sellerstore/sellerstore-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "SellerStore";
    public static final String PREFIX_FOR_RESOLVER = "sellerstore";

    @Bean BeanFactoryAdapter sellerstoreBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl sellerstoreFlowStore(
            @Qualifier("sellerstoreBeanFactoryAdapter") BeanFactoryAdapter sellerstoreBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(sellerstoreBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<SellerStore> sellerstoreEntityStm(@Qualifier("sellerstoreFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<SellerStore> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider sellerstoreActionsInfoProvider(@Qualifier("sellerstoreFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("sellerstore",provider);
        return provider;
	}
	
	@Bean EntityStore<SellerStore> sellerstoreEntityStore() {
		return new SellerStoreEntityStore();
	}
	
	@Bean StateEntityServiceImpl<SellerStore> _sellerstoreStateEntityService_(
			@Qualifier("sellerstoreEntityStm") STM<SellerStore> stm,
			@Qualifier("sellerstoreActionsInfoProvider") STMActionsInfoProvider sellerstoreInfoProvider,
			@Qualifier("sellerstoreEntityStore") EntityStore<SellerStore> entityStore){
		return new StateEntityServiceImpl<>(stm, sellerstoreInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<SellerStore> sellerstoreEntryAction(@Qualifier("sellerstoreEntityStore") EntityStore<SellerStore> entityStore,
			@Qualifier("sellerstoreActionsInfoProvider") STMActionsInfoProvider sellerstoreInfoProvider,
            @Qualifier("sellerstoreFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<SellerStore> entryAction =  new GenericEntryAction<SellerStore>(entityStore,sellerstoreInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<SellerStore> sellerstoreExitAction(@Qualifier("sellerstoreFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<SellerStore> exitAction = new GenericExitAction<SellerStore>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader sellerstoreFlowReader(@Qualifier("sellerstoreFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean SellerStoreHealthChecker sellerstoreHealthChecker(){
    	return new SellerStoreHealthChecker();
    }

    @Bean STMTransitionAction<SellerStore> defaultsellerstoreSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver sellerstoreTransitionActionResolver(
    @Qualifier("defaultsellerstoreSTMTransitionAction") STMTransitionAction<SellerStore> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector sellerstoreBodyTypeSelector(
    @Qualifier("sellerstoreActionsInfoProvider") STMActionsInfoProvider sellerstoreInfoProvider,
    @Qualifier("sellerstoreTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(sellerstoreInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<SellerStore> sellerstoreBaseTransitionAction(
        @Qualifier("sellerstoreTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "sellerstore" + eventId for the method name. (sellerstore is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/sellerstore/sellerstore-states.xml

    @Bean SuspendSellerStoreAction
            sellerstoreSuspend(){
        return new SuspendSellerStoreAction();
    }
    @Bean UnpublishSellerStoreAction
            sellerstoreUnpublish(){
        return new UnpublishSellerStoreAction();
    }
    @Bean SubmitSellerStoreAction
            sellerstoreSubmit(){
        return new SubmitSellerStoreAction();
    }
    @Bean ReactivateSellerStoreAction
            sellerstoreReactivate(){
        return new ReactivateSellerStoreAction();
    }
    @Bean RepublishSellerStoreAction
            sellerstoreRepublish(){
        return new RepublishSellerStoreAction();
    }
    @Bean ApproveSellerStoreAction
            sellerstoreApprove(){
        return new ApproveSellerStoreAction();
    }
    @Bean RejectSellerStoreAction
            sellerstoreReject(){
        return new RejectSellerStoreAction();
    }
    @Bean PublishSellerStoreAction
            sellerstorePublish(){
        return new PublishSellerStoreAction();
    }


}
