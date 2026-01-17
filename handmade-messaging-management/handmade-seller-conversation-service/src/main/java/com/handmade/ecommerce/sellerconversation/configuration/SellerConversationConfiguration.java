package com.handmade.ecommerce.sellerconversation.configuration;

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
import com.handmade.ecommerce.messaging.model.SellerConversation;
import com.handmade.ecommerce.sellerconversation.service.cmds.*;
import com.handmade.ecommerce.sellerconversation.service.healthcheck.SellerConversationHealthChecker;
import com.handmade.ecommerce.sellerconversation.service.store.SellerConversationEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class SellerConversationConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/sellerconversation/sellerconversation-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "SellerConversation";
    public static final String PREFIX_FOR_RESOLVER = "sellerconversation";

    @Bean BeanFactoryAdapter sellerconversationBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl sellerconversationFlowStore(
            @Qualifier("sellerconversationBeanFactoryAdapter") BeanFactoryAdapter sellerconversationBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(sellerconversationBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<SellerConversation> sellerconversationEntityStm(@Qualifier("sellerconversationFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<SellerConversation> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider sellerconversationActionsInfoProvider(@Qualifier("sellerconversationFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("sellerconversation",provider);
        return provider;
	}
	
	@Bean EntityStore<SellerConversation> sellerconversationEntityStore() {
		return new SellerConversationEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<SellerConversation> _sellerconversationStateEntityService_(
			@Qualifier("sellerconversationEntityStm") STM<SellerConversation> stm,
			@Qualifier("sellerconversationActionsInfoProvider") STMActionsInfoProvider sellerconversationInfoProvider,
			@Qualifier("sellerconversationEntityStore") EntityStore<SellerConversation> entityStore){
		return new StateEntityServiceImpl<>(stm, sellerconversationInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<SellerConversation> sellerconversationEntryAction(@Qualifier("sellerconversationEntityStore") EntityStore<SellerConversation> entityStore,
			@Qualifier("sellerconversationActionsInfoProvider") STMActionsInfoProvider sellerconversationInfoProvider,
            @Qualifier("sellerconversationFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<SellerConversation> entryAction =  new GenericEntryAction<SellerConversation>(entityStore,sellerconversationInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<SellerConversation> sellerconversationExitAction(@Qualifier("sellerconversationFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<SellerConversation> exitAction = new GenericExitAction<SellerConversation>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader sellerconversationFlowReader(@Qualifier("sellerconversationFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean SellerConversationHealthChecker sellerconversationHealthChecker(){
    	return new SellerConversationHealthChecker();
    }

    @Bean STMTransitionAction<SellerConversation> defaultsellerconversationSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver sellerconversationTransitionActionResolver(
    @Qualifier("defaultsellerconversationSTMTransitionAction") STMTransitionAction<SellerConversation> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector sellerconversationBodyTypeSelector(
    @Qualifier("sellerconversationActionsInfoProvider") STMActionsInfoProvider sellerconversationInfoProvider,
    @Qualifier("sellerconversationTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(sellerconversationInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<SellerConversation> sellerconversationBaseTransitionAction(
        @Qualifier("sellerconversationTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "sellerconversation" + eventId for the method name. (sellerconversation is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/sellerconversation/sellerconversation-states.xml

    @Bean ReopenSellerConversationAction
            sellerconversationReopen(){
        return new ReopenSellerConversationAction();
    }
    @Bean CustomerReplySellerConversationAction
            sellerconversationCustomerReply(){
        return new CustomerReplySellerConversationAction();
    }
    @Bean CloseSellerConversationAction
            sellerconversationClose(){
        return new CloseSellerConversationAction();
    }
    @Bean RespondSellerConversationAction
            sellerconversationRespond(){
        return new RespondSellerConversationAction();
    }
    @Bean CloseSellerConversationAction
            sellerconversationClose(){
        return new CloseSellerConversationAction();
    }
    @Bean EscalateSellerConversationAction
            sellerconversationEscalate(){
        return new EscalateSellerConversationAction();
    }
    @Bean CloseSellerConversationAction
            sellerconversationClose(){
        return new CloseSellerConversationAction();
    }
    @Bean AssignSellerConversationAction
            sellerconversationAssign(){
        return new AssignSellerConversationAction();
    }
    @Bean ResolveSellerConversationAction
            sellerconversationResolve(){
        return new ResolveSellerConversationAction();
    }
    @Bean CloseSellerConversationAction
            sellerconversationClose(){
        return new CloseSellerConversationAction();
    }


}
