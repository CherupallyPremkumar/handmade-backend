package com.handmade.ecommerce.productanswer.configuration;

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
import com.handmade.ecommerce.qa.model.ProductAnswer;
import com.handmade.ecommerce.productanswer.service.cmds.*;
import com.handmade.ecommerce.productanswer.service.healthcheck.ProductAnswerHealthChecker;
import com.handmade.ecommerce.productanswer.service.store.ProductAnswerEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class ProductAnswerConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/productanswer/productanswer-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "ProductAnswer";
    public static final String PREFIX_FOR_RESOLVER = "productanswer";

    @Bean BeanFactoryAdapter productanswerBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl productanswerFlowStore(
            @Qualifier("productanswerBeanFactoryAdapter") BeanFactoryAdapter productanswerBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(productanswerBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<ProductAnswer> productanswerEntityStm(@Qualifier("productanswerFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<ProductAnswer> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider productanswerActionsInfoProvider(@Qualifier("productanswerFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("productanswer",provider);
        return provider;
	}
	
	@Bean EntityStore<ProductAnswer> productanswerEntityStore() {
		return new ProductAnswerEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<ProductAnswer> _productanswerStateEntityService_(
			@Qualifier("productanswerEntityStm") STM<ProductAnswer> stm,
			@Qualifier("productanswerActionsInfoProvider") STMActionsInfoProvider productanswerInfoProvider,
			@Qualifier("productanswerEntityStore") EntityStore<ProductAnswer> entityStore){
		return new StateEntityServiceImpl<>(stm, productanswerInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<ProductAnswer> productanswerEntryAction(@Qualifier("productanswerEntityStore") EntityStore<ProductAnswer> entityStore,
			@Qualifier("productanswerActionsInfoProvider") STMActionsInfoProvider productanswerInfoProvider,
            @Qualifier("productanswerFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<ProductAnswer> entryAction =  new GenericEntryAction<ProductAnswer>(entityStore,productanswerInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<ProductAnswer> productanswerExitAction(@Qualifier("productanswerFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<ProductAnswer> exitAction = new GenericExitAction<ProductAnswer>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader productanswerFlowReader(@Qualifier("productanswerFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean ProductAnswerHealthChecker productanswerHealthChecker(){
    	return new ProductAnswerHealthChecker();
    }

    @Bean STMTransitionAction<ProductAnswer> defaultproductanswerSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver productanswerTransitionActionResolver(
    @Qualifier("defaultproductanswerSTMTransitionAction") STMTransitionAction<ProductAnswer> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector productanswerBodyTypeSelector(
    @Qualifier("productanswerActionsInfoProvider") STMActionsInfoProvider productanswerInfoProvider,
    @Qualifier("productanswerTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(productanswerInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<ProductAnswer> productanswerBaseTransitionAction(
        @Qualifier("productanswerTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "productanswer" + eventId for the method name. (productanswer is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/productanswer/productanswer-states.xml

    @Bean MarkHelpfulProductAnswerAction
            productanswerMarkHelpful(){
        return new MarkHelpfulProductAnswerAction();
    }
    @Bean UnpublishProductAnswerAction
            productanswerUnpublish(){
        return new UnpublishProductAnswerAction();
    }
    @Bean RepublishProductAnswerAction
            productanswerRepublish(){
        return new RepublishProductAnswerAction();
    }
    @Bean ReviewProductAnswerAction
            productanswerReview(){
        return new ReviewProductAnswerAction();
    }
    @Bean PublishProductAnswerAction
            productanswerPublish(){
        return new PublishProductAnswerAction();
    }
    @Bean UnpublishProductAnswerAction
            productanswerUnpublish(){
        return new UnpublishProductAnswerAction();
    }
    @Bean ApproveProductAnswerAction
            productanswerApprove(){
        return new ApproveProductAnswerAction();
    }
    @Bean RejectProductAnswerAction
            productanswerReject(){
        return new RejectProductAnswerAction();
    }


}
