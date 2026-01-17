package com.handmade.ecommerce.promotion.configuration;

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
import com.handmade.ecommerce.promotion.model.Promotion;
import com.handmade.ecommerce.promotion.service.cmds.*;
import com.handmade.ecommerce.promotion.service.healthcheck.PromotionHealthChecker;
import com.handmade.ecommerce.promotion.service.store.PromotionEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PromotionConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/promotion/promotion-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Promotion";
    public static final String PREFIX_FOR_RESOLVER = "promotion";

    @Bean BeanFactoryAdapter promotionBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl promotionFlowStore(
            @Qualifier("promotionBeanFactoryAdapter") BeanFactoryAdapter promotionBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(promotionBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Promotion> promotionEntityStm(@Qualifier("promotionFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Promotion> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider promotionActionsInfoProvider(@Qualifier("promotionFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("promotion",provider);
        return provider;
	}
	
	@Bean EntityStore<Promotion> promotionEntityStore() {
		return new PromotionEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Promotion> _promotionStateEntityService_(
			@Qualifier("promotionEntityStm") STM<Promotion> stm,
			@Qualifier("promotionActionsInfoProvider") STMActionsInfoProvider promotionInfoProvider,
			@Qualifier("promotionEntityStore") EntityStore<Promotion> entityStore){
		return new StateEntityServiceImpl<>(stm, promotionInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Promotion> promotionEntryAction(@Qualifier("promotionEntityStore") EntityStore<Promotion> entityStore,
			@Qualifier("promotionActionsInfoProvider") STMActionsInfoProvider promotionInfoProvider,
            @Qualifier("promotionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Promotion> entryAction =  new GenericEntryAction<Promotion>(entityStore,promotionInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Promotion> promotionExitAction(@Qualifier("promotionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Promotion> exitAction = new GenericExitAction<Promotion>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader promotionFlowReader(@Qualifier("promotionFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PromotionHealthChecker promotionHealthChecker(){
    	return new PromotionHealthChecker();
    }

    @Bean STMTransitionAction<Promotion> defaultpromotionSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver promotionTransitionActionResolver(
    @Qualifier("defaultpromotionSTMTransitionAction") STMTransitionAction<Promotion> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector promotionBodyTypeSelector(
    @Qualifier("promotionActionsInfoProvider") STMActionsInfoProvider promotionInfoProvider,
    @Qualifier("promotionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(promotionInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Promotion> promotionBaseTransitionAction(
        @Qualifier("promotionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "promotion" + eventId for the method name. (promotion is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/promotion/promotion-states.xml

    @Bean ResumePromotionAction
            promotionResume(){
        return new ResumePromotionAction();
    }
    @Bean CancelPromotionAction
            promotionCancel(){
        return new CancelPromotionAction();
    }
    @Bean CancelPromotionAction
            promotionCancel(){
        return new CancelPromotionAction();
    }
    @Bean ExpirePromotionAction
            promotionExpire(){
        return new ExpirePromotionAction();
    }
    @Bean PausePromotionAction
            promotionPause(){
        return new PausePromotionAction();
    }
    @Bean SubmitPromotionAction
            promotionSubmit(){
        return new SubmitPromotionAction();
    }
    @Bean ApprovePromotionAction
            promotionApprove(){
        return new ApprovePromotionAction();
    }
    @Bean RejectPromotionAction
            promotionReject(){
        return new RejectPromotionAction();
    }
    @Bean SchedulePromotionAction
            promotionSchedule(){
        return new SchedulePromotionAction();
    }
    @Bean ActivatePromotionAction
            promotionActivate(){
        return new ActivatePromotionAction();
    }
    @Bean CancelPromotionAction
            promotionCancel(){
        return new CancelPromotionAction();
    }
    @Bean ActivatePromotionAction
            promotionActivate(){
        return new ActivatePromotionAction();
    }


}
