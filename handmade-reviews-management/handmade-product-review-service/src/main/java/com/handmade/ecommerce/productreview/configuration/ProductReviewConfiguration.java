package com.handmade.ecommerce.productreview.configuration;

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
import com.handmade.ecommerce.reviews.model.ProductReview;
import com.handmade.ecommerce.productreview.service.cmds.*;
import com.handmade.ecommerce.productreview.service.healthcheck.ProductReviewHealthChecker;
import com.handmade.ecommerce.productreview.service.store.ProductReviewEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class ProductReviewConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/productreview/productreview-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "ProductReview";
    public static final String PREFIX_FOR_RESOLVER = "productreview";

    @Bean BeanFactoryAdapter productreviewBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl productreviewFlowStore(
            @Qualifier("productreviewBeanFactoryAdapter") BeanFactoryAdapter productreviewBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(productreviewBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<ProductReview> productreviewEntityStm(@Qualifier("productreviewFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<ProductReview> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider productreviewActionsInfoProvider(@Qualifier("productreviewFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("productreview",provider);
        return provider;
	}
	
	@Bean EntityStore<ProductReview> productreviewEntityStore() {
		return new ProductReviewEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<ProductReview> _productreviewStateEntityService_(
			@Qualifier("productreviewEntityStm") STM<ProductReview> stm,
			@Qualifier("productreviewActionsInfoProvider") STMActionsInfoProvider productreviewInfoProvider,
			@Qualifier("productreviewEntityStore") EntityStore<ProductReview> entityStore){
		return new StateEntityServiceImpl<>(stm, productreviewInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<ProductReview> productreviewEntryAction(@Qualifier("productreviewEntityStore") EntityStore<ProductReview> entityStore,
			@Qualifier("productreviewActionsInfoProvider") STMActionsInfoProvider productreviewInfoProvider,
            @Qualifier("productreviewFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<ProductReview> entryAction =  new GenericEntryAction<ProductReview>(entityStore,productreviewInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<ProductReview> productreviewExitAction(@Qualifier("productreviewFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<ProductReview> exitAction = new GenericExitAction<ProductReview>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader productreviewFlowReader(@Qualifier("productreviewFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean ProductReviewHealthChecker productreviewHealthChecker(){
    	return new ProductReviewHealthChecker();
    }

    @Bean STMTransitionAction<ProductReview> defaultproductreviewSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver productreviewTransitionActionResolver(
    @Qualifier("defaultproductreviewSTMTransitionAction") STMTransitionAction<ProductReview> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector productreviewBodyTypeSelector(
    @Qualifier("productreviewActionsInfoProvider") STMActionsInfoProvider productreviewInfoProvider,
    @Qualifier("productreviewTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(productreviewInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<ProductReview> productreviewBaseTransitionAction(
        @Qualifier("productreviewTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "productreview" + eventId for the method name. (productreview is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/productreview/productreview-states.xml

    @Bean FlagProductReviewAction
            productreviewFlag(){
        return new FlagProductReviewAction();
    }
    @Bean UnpublishProductReviewAction
            productreviewUnpublish(){
        return new UnpublishProductReviewAction();
    }
    @Bean InvestigateProductReviewAction
            productreviewInvestigate(){
        return new InvestigateProductReviewAction();
    }
    @Bean RepublishProductReviewAction
            productreviewRepublish(){
        return new RepublishProductReviewAction();
    }
    @Bean ReviewProductReviewAction
            productreviewReview(){
        return new ReviewProductReviewAction();
    }
    @Bean PublishProductReviewAction
            productreviewPublish(){
        return new PublishProductReviewAction();
    }
    @Bean FlagProductReviewAction
            productreviewFlag(){
        return new FlagProductReviewAction();
    }
    @Bean ApproveProductReviewAction
            productreviewApprove(){
        return new ApproveProductReviewAction();
    }
    @Bean RejectProductReviewAction
            productreviewReject(){
        return new RejectProductReviewAction();
    }
    @Bean ClearProductReviewAction
            productreviewClear(){
        return new ClearProductReviewAction();
    }
    @Bean RemoveProductReviewAction
            productreviewRemove(){
        return new RemoveProductReviewAction();
    }


}
