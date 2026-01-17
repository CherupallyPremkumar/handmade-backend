package com.handmade.ecommerce.productquestion.configuration;

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
import com.handmade.ecommerce.qa.model.ProductQuestion;
import com.handmade.ecommerce.productquestion.service.cmds.*;
import com.handmade.ecommerce.productquestion.service.healthcheck.ProductQuestionHealthChecker;
import com.handmade.ecommerce.productquestion.service.store.ProductQuestionEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class ProductQuestionConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/productquestion/productquestion-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "ProductQuestion";
    public static final String PREFIX_FOR_RESOLVER = "productquestion";

    @Bean BeanFactoryAdapter productquestionBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl productquestionFlowStore(
            @Qualifier("productquestionBeanFactoryAdapter") BeanFactoryAdapter productquestionBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(productquestionBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<ProductQuestion> productquestionEntityStm(@Qualifier("productquestionFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<ProductQuestion> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider productquestionActionsInfoProvider(@Qualifier("productquestionFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("productquestion",provider);
        return provider;
	}
	
	@Bean EntityStore<ProductQuestion> productquestionEntityStore() {
		return new ProductQuestionEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<ProductQuestion> _productquestionStateEntityService_(
			@Qualifier("productquestionEntityStm") STM<ProductQuestion> stm,
			@Qualifier("productquestionActionsInfoProvider") STMActionsInfoProvider productquestionInfoProvider,
			@Qualifier("productquestionEntityStore") EntityStore<ProductQuestion> entityStore){
		return new StateEntityServiceImpl<>(stm, productquestionInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<ProductQuestion> productquestionEntryAction(@Qualifier("productquestionEntityStore") EntityStore<ProductQuestion> entityStore,
			@Qualifier("productquestionActionsInfoProvider") STMActionsInfoProvider productquestionInfoProvider,
            @Qualifier("productquestionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<ProductQuestion> entryAction =  new GenericEntryAction<ProductQuestion>(entityStore,productquestionInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<ProductQuestion> productquestionExitAction(@Qualifier("productquestionFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<ProductQuestion> exitAction = new GenericExitAction<ProductQuestion>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader productquestionFlowReader(@Qualifier("productquestionFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean ProductQuestionHealthChecker productquestionHealthChecker(){
    	return new ProductQuestionHealthChecker();
    }

    @Bean STMTransitionAction<ProductQuestion> defaultproductquestionSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver productquestionTransitionActionResolver(
    @Qualifier("defaultproductquestionSTMTransitionAction") STMTransitionAction<ProductQuestion> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector productquestionBodyTypeSelector(
    @Qualifier("productquestionActionsInfoProvider") STMActionsInfoProvider productquestionInfoProvider,
    @Qualifier("productquestionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(productquestionInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<ProductQuestion> productquestionBaseTransitionAction(
        @Qualifier("productquestionTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "productquestion" + eventId for the method name. (productquestion is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/productquestion/productquestion-states.xml

    @Bean CloseProductQuestionAction
            productquestionClose(){
        return new CloseProductQuestionAction();
    }
    @Bean AnswerProductQuestionAction
            productquestionAnswer(){
        return new AnswerProductQuestionAction();
    }
    @Bean UnpublishProductQuestionAction
            productquestionUnpublish(){
        return new UnpublishProductQuestionAction();
    }
    @Bean RepublishProductQuestionAction
            productquestionRepublish(){
        return new RepublishProductQuestionAction();
    }
    @Bean ReviewProductQuestionAction
            productquestionReview(){
        return new ReviewProductQuestionAction();
    }
    @Bean PublishProductQuestionAction
            productquestionPublish(){
        return new PublishProductQuestionAction();
    }
    @Bean ApproveProductQuestionAction
            productquestionApprove(){
        return new ApproveProductQuestionAction();
    }
    @Bean RejectProductQuestionAction
            productquestionReject(){
        return new RejectProductQuestionAction();
    }


}
