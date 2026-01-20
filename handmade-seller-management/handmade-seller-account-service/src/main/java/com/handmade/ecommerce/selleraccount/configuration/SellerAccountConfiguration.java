package com.handmade.ecommerce.selleraccount.configuration;

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
import com.handmade.ecommerce.seller.model.SellerAccount;
import com.handmade.ecommerce.selleraccount.service.cmds.*;
import com.handmade.ecommerce.selleraccount.service.healthcheck.SellerAccountHealthChecker;
import com.handmade.ecommerce.selleraccount.service.store.SellerAccountEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class SellerAccountConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/selleraccount/selleraccount-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "SellerAccount";
    public static final String PREFIX_FOR_RESOLVER = "selleraccount";

    @Bean BeanFactoryAdapter selleraccountBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl selleraccountFlowStore(
            @Qualifier("selleraccountBeanFactoryAdapter") BeanFactoryAdapter selleraccountBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(selleraccountBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<SellerAccount> selleraccountEntityStm(@Qualifier("selleraccountFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<SellerAccount> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider selleraccountActionsInfoProvider(@Qualifier("selleraccountFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("selleraccount",provider);
        return provider;
	}
	
	@Bean EntityStore<SellerAccount> selleraccountEntityStore() {
		return new SellerAccountEntityStore();
	}
	
	@Bean StateEntityServiceImpl<SellerAccount> _selleraccountStateEntityService_(
			@Qualifier("selleraccountEntityStm") STM<SellerAccount> stm,
			@Qualifier("selleraccountActionsInfoProvider") STMActionsInfoProvider selleraccountInfoProvider,
			@Qualifier("selleraccountEntityStore") EntityStore<SellerAccount> entityStore){
		return new StateEntityServiceImpl<>(stm, selleraccountInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<SellerAccount> selleraccountEntryAction(@Qualifier("selleraccountEntityStore") EntityStore<SellerAccount> entityStore,
			@Qualifier("selleraccountActionsInfoProvider") STMActionsInfoProvider selleraccountInfoProvider,
            @Qualifier("selleraccountFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<SellerAccount> entryAction =  new GenericEntryAction<SellerAccount>(entityStore,selleraccountInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<SellerAccount> selleraccountExitAction(@Qualifier("selleraccountFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<SellerAccount> exitAction = new GenericExitAction<SellerAccount>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader selleraccountFlowReader(@Qualifier("selleraccountFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean SellerAccountHealthChecker selleraccountHealthChecker(){
    	return new SellerAccountHealthChecker();
    }

    @Bean STMTransitionAction<SellerAccount> defaultselleraccountSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver selleraccountTransitionActionResolver(
    @Qualifier("defaultselleraccountSTMTransitionAction") STMTransitionAction<SellerAccount> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector selleraccountBodyTypeSelector(
    @Qualifier("selleraccountActionsInfoProvider") STMActionsInfoProvider selleraccountInfoProvider,
    @Qualifier("selleraccountTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(selleraccountInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<SellerAccount> selleraccountBaseTransitionAction(
        @Qualifier("selleraccountTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "selleraccount" + eventId for the method name. (selleraccount is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/selleraccount/selleraccount-states.xml

    @Bean SuspendSellerAccountAction
            suspendSellerAccountAction(){
        return new SuspendSellerAccountAction();
    }

    @Bean CloseSellerAccountAction
            closeSellerAccountAction(){
        return new CloseSellerAccountAction();
    }
    @Bean ReactivateSellerAccountAction
            reactivateSellerAccountAction(){
        return new ReactivateSellerAccountAction();
    }
    @Bean DeactivateSellerAccountAction
            deactivateSellerAccountAction(){
        return new DeactivateSellerAccountAction();
    }
    @Bean VerifySellerAccountAction
            verifySellerAccountAction(){
        return new VerifySellerAccountAction();
    }
    @Bean ActivateSellerAccountAction
    activateSellerAccountAction(){
        return new ActivateSellerAccountAction();
    }


}
