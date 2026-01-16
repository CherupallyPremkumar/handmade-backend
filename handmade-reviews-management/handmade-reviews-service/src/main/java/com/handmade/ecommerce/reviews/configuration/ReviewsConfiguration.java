package com.handmade.ecommerce.reviews.configuration;

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
import com.handmade.ecommerce.reviews.model.Reviews;
import com.handmade.ecommerce.reviews.service.cmds.*;
import com.handmade.ecommerce.reviews.service.healthcheck.ReviewsHealthChecker;
import com.handmade.ecommerce.reviews.service.store.ReviewsEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class ReviewsConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/reviews/reviews-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Reviews";
    public static final String PREFIX_FOR_RESOLVER = "reviews";

    @Bean BeanFactoryAdapter reviewsBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl reviewsFlowStore(
            @Qualifier("reviewsBeanFactoryAdapter") BeanFactoryAdapter reviewsBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(reviewsBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Reviews> reviewsEntityStm(@Qualifier("reviewsFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Reviews> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider reviewsActionsInfoProvider(@Qualifier("reviewsFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("reviews",provider);
        return provider;
	}
	
	@Bean EntityStore<Reviews> reviewsEntityStore() {
		return new ReviewsEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Reviews> _reviewsStateEntityService_(
			@Qualifier("reviewsEntityStm") STM<Reviews> stm,
			@Qualifier("reviewsActionsInfoProvider") STMActionsInfoProvider reviewsInfoProvider,
			@Qualifier("reviewsEntityStore") EntityStore<Reviews> entityStore){
		return new StateEntityServiceImpl<>(stm, reviewsInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Reviews> reviewsEntryAction(@Qualifier("reviewsEntityStore") EntityStore<Reviews> entityStore,
			@Qualifier("reviewsActionsInfoProvider") STMActionsInfoProvider reviewsInfoProvider,
            @Qualifier("reviewsFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Reviews> entryAction =  new GenericEntryAction<Reviews>(entityStore,reviewsInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Reviews> reviewsExitAction(@Qualifier("reviewsFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Reviews> exitAction = new GenericExitAction<Reviews>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader reviewsFlowReader(@Qualifier("reviewsFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean ReviewsHealthChecker reviewsHealthChecker(){
    	return new ReviewsHealthChecker();
    }

    @Bean STMTransitionAction<Reviews> defaultreviewsSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver reviewsTransitionActionResolver(
    @Qualifier("defaultreviewsSTMTransitionAction") STMTransitionAction<Reviews> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector reviewsBodyTypeSelector(
    @Qualifier("reviewsActionsInfoProvider") STMActionsInfoProvider reviewsInfoProvider,
    @Qualifier("reviewsTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(reviewsInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Reviews> reviewsBaseTransitionAction(
        @Qualifier("reviewsTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "reviews" + eventId for the method name. (reviews is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/reviews/reviews-states.xml

    @Bean ApproveReviewsAction
            reviewsApprove(){
        return new ApproveReviewsAction();
    }
    @Bean RejectReviewsAction
            reviewsReject(){
        return new RejectReviewsAction();
    }
    @Bean ArchiveReviewsAction
            reviewsArchive(){
        return new ArchiveReviewsAction();
    }
    @Bean AppealReviewsAction
            reviewsAppeal(){
        return new AppealReviewsAction();
    }
    @Bean SustainRejectionReviewsAction
            reviewsSustainRejection(){
        return new SustainRejectionReviewsAction();
    }
    @Bean ApproveReviewsAction
            reviewsApprove(){
        return new ApproveReviewsAction();
    }


}
