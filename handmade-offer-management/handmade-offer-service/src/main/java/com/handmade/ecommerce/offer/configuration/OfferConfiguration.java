package com.handmade.ecommerce.offer.configuration;

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
import com.handmade.ecommerce.offer.model.Offer;
import com.handmade.ecommerce.offer.service.cmds.*;
import com.handmade.ecommerce.offer.service.healthcheck.OfferHealthChecker;
import com.handmade.ecommerce.offer.service.store.OfferEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class OfferConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/offer/offer-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Offer";
    public static final String PREFIX_FOR_RESOLVER = "offer";

    @Bean BeanFactoryAdapter offerBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl offerFlowStore(
            @Qualifier("offerBeanFactoryAdapter") BeanFactoryAdapter offerBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(offerBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Offer> offerEntityStm(@Qualifier("offerFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Offer> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider offerActionsInfoProvider(@Qualifier("offerFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("offer",provider);
        return provider;
	}
	
	@Bean EntityStore<Offer> offerEntityStore() {
		return new OfferEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Offer> _offerStateEntityService_(
			@Qualifier("offerEntityStm") STM<Offer> stm,
			@Qualifier("offerActionsInfoProvider") STMActionsInfoProvider offerInfoProvider,
			@Qualifier("offerEntityStore") EntityStore<Offer> entityStore){
		return new StateEntityServiceImpl<>(stm, offerInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Offer> offerEntryAction(@Qualifier("offerEntityStore") EntityStore<Offer> entityStore,
			@Qualifier("offerActionsInfoProvider") STMActionsInfoProvider offerInfoProvider,
            @Qualifier("offerFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Offer> entryAction =  new GenericEntryAction<Offer>(entityStore,offerInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Offer> offerExitAction(@Qualifier("offerFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Offer> exitAction = new GenericExitAction<Offer>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader offerFlowReader(@Qualifier("offerFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean OfferHealthChecker offerHealthChecker(){
    	return new OfferHealthChecker();
    }

    @Bean STMTransitionAction<Offer> defaultofferSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver offerTransitionActionResolver(
    @Qualifier("defaultofferSTMTransitionAction") STMTransitionAction<Offer> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector offerBodyTypeSelector(
    @Qualifier("offerActionsInfoProvider") STMActionsInfoProvider offerInfoProvider,
    @Qualifier("offerTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(offerInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Offer> offerBaseTransitionAction(
        @Qualifier("offerTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "offer" + eventId for the method name. (offer is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/offer/offer-states.xml

    @Bean ResumeOfferAction
            offerResume(){
        return new ResumeOfferAction();
    }
    @Bean CancelOfferAction
            offerCancel(){
        return new CancelOfferAction();
    }
    @Bean CancelOfferAction
            offerCancel(){
        return new CancelOfferAction();
    }
    @Bean ExpireOfferAction
            offerExpire(){
        return new ExpireOfferAction();
    }
    @Bean PauseOfferAction
            offerPause(){
        return new PauseOfferAction();
    }
    @Bean SubmitOfferAction
            offerSubmit(){
        return new SubmitOfferAction();
    }
    @Bean ApproveOfferAction
            offerApprove(){
        return new ApproveOfferAction();
    }
    @Bean RejectOfferAction
            offerReject(){
        return new RejectOfferAction();
    }
    @Bean ScheduleOfferAction
            offerSchedule(){
        return new ScheduleOfferAction();
    }
    @Bean ActivateOfferAction
            offerActivate(){
        return new ActivateOfferAction();
    }
    @Bean CancelOfferAction
            offerCancel(){
        return new CancelOfferAction();
    }
    @Bean ActivateOfferAction
            offerActivate(){
        return new ActivateOfferAction();
    }


}
