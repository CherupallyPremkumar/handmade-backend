package com.handmade.ecommerce.seller.configuration;

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
import com.handmade.ecommerce.seller.model.Seller;
import com.handmade.ecommerce.seller.service.cmds.*;
import com.handmade.ecommerce.seller.service.healthcheck.SellerHealthChecker;
import com.handmade.ecommerce.seller.service.store.SellerEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class SellerConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/seller/seller-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Seller";
    public static final String PREFIX_FOR_RESOLVER = "seller";

    @Bean BeanFactoryAdapter sellerBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl sellerFlowStore(
            @Qualifier("sellerBeanFactoryAdapter") BeanFactoryAdapter sellerBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(sellerBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Seller> sellerEntityStm(@Qualifier("sellerFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Seller> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider sellerActionsInfoProvider(@Qualifier("sellerFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("seller",provider);
        return provider;
	}
	
	@Bean EntityStore<Seller> sellerEntityStore() {
		return new SellerEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Seller> _sellerStateEntityService_(
			@Qualifier("sellerEntityStm") STM<Seller> stm,
			@Qualifier("sellerActionsInfoProvider") STMActionsInfoProvider sellerInfoProvider,
			@Qualifier("sellerEntityStore") EntityStore<Seller> entityStore){
		return new StateEntityServiceImpl<>(stm, sellerInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Seller> sellerEntryAction(@Qualifier("sellerEntityStore") EntityStore<Seller> entityStore,
			@Qualifier("sellerActionsInfoProvider") STMActionsInfoProvider sellerInfoProvider,
            @Qualifier("sellerFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Seller> entryAction =  new GenericEntryAction<Seller>(entityStore,sellerInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Seller> sellerExitAction(@Qualifier("sellerFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Seller> exitAction = new GenericExitAction<Seller>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader sellerFlowReader(@Qualifier("sellerFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean SellerHealthChecker sellerHealthChecker(){
    	return new SellerHealthChecker();
    }

    @Bean STMTransitionAction<Seller> defaultsellerSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver sellerTransitionActionResolver(
    @Qualifier("defaultsellerSTMTransitionAction") STMTransitionAction<Seller> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector sellerBodyTypeSelector(
    @Qualifier("sellerActionsInfoProvider") STMActionsInfoProvider sellerInfoProvider,
    @Qualifier("sellerTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(sellerInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Seller> sellerBaseTransitionAction(
        @Qualifier("sellerTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "seller" + eventId for the method name. (seller is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/seller/seller-states.xml

    @Bean SubmitSellerAction
            sellerSubmit(){
        return new SubmitSellerAction();
    }
    @Bean ApproveSellerAction
            sellerApprove(){
        return new ApproveSellerAction();
    }
    @Bean RejectSellerAction
            sellerReject(){
        return new RejectSellerAction();
    }
    @Bean RequestChangesSellerAction
            sellerRequestChanges(){
        return new RequestChangesSellerAction();
    }


}
