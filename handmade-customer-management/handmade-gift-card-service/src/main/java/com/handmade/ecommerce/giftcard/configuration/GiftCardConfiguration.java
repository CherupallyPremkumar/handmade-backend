package com.handmade.ecommerce.giftcard.configuration;

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
import com.handmade.ecommerce.customer.model.GiftCard;
import com.handmade.ecommerce.giftcard.service.cmds.*;
import com.handmade.ecommerce.giftcard.service.healthcheck.GiftCardHealthChecker;
import com.handmade.ecommerce.giftcard.service.store.GiftCardEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class GiftCardConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/giftcard/giftcard-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "GiftCard";
    public static final String PREFIX_FOR_RESOLVER = "giftcard";

    @Bean BeanFactoryAdapter giftcardBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl giftcardFlowStore(
            @Qualifier("giftcardBeanFactoryAdapter") BeanFactoryAdapter giftcardBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(giftcardBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<GiftCard> giftcardEntityStm(@Qualifier("giftcardFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<GiftCard> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider giftcardActionsInfoProvider(@Qualifier("giftcardFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("giftcard",provider);
        return provider;
	}
	
	@Bean EntityStore<GiftCard> giftcardEntityStore() {
		return new GiftCardEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<GiftCard> _giftcardStateEntityService_(
			@Qualifier("giftcardEntityStm") STM<GiftCard> stm,
			@Qualifier("giftcardActionsInfoProvider") STMActionsInfoProvider giftcardInfoProvider,
			@Qualifier("giftcardEntityStore") EntityStore<GiftCard> entityStore){
		return new StateEntityServiceImpl<>(stm, giftcardInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<GiftCard> giftcardEntryAction(@Qualifier("giftcardEntityStore") EntityStore<GiftCard> entityStore,
			@Qualifier("giftcardActionsInfoProvider") STMActionsInfoProvider giftcardInfoProvider,
            @Qualifier("giftcardFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<GiftCard> entryAction =  new GenericEntryAction<GiftCard>(entityStore,giftcardInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<GiftCard> giftcardExitAction(@Qualifier("giftcardFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<GiftCard> exitAction = new GenericExitAction<GiftCard>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader giftcardFlowReader(@Qualifier("giftcardFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean GiftCardHealthChecker giftcardHealthChecker(){
    	return new GiftCardHealthChecker();
    }

    @Bean STMTransitionAction<GiftCard> defaultgiftcardSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver giftcardTransitionActionResolver(
    @Qualifier("defaultgiftcardSTMTransitionAction") STMTransitionAction<GiftCard> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector giftcardBodyTypeSelector(
    @Qualifier("giftcardActionsInfoProvider") STMActionsInfoProvider giftcardInfoProvider,
    @Qualifier("giftcardTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(giftcardInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<GiftCard> giftcardBaseTransitionAction(
        @Qualifier("giftcardTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "giftcard" + eventId for the method name. (giftcard is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/giftcard/giftcard-states.xml

    @Bean ActivateGiftCardAction
            giftcardActivate(){
        return new ActivateGiftCardAction();
    }
    @Bean ExpireGiftCardAction
            giftcardExpire(){
        return new ExpireGiftCardAction();
    }
    @Bean RedeemGiftCardAction
            giftcardRedeem(){
        return new RedeemGiftCardAction();
    }


}
