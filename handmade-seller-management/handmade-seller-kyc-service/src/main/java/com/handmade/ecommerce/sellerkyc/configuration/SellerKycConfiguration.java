package com.handmade.ecommerce.sellerkyc.configuration;

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
import com.handmade.ecommerce.seller.model.SellerKyc;
import com.handmade.ecommerce.sellerkyc.service.cmds.*;
import com.handmade.ecommerce.sellerkyc.service.healthcheck.SellerKycHealthChecker;
import com.handmade.ecommerce.sellerkyc.service.store.SellerKycEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class SellerKycConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/sellerkyc/sellerkyc-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "SellerKyc";
    public static final String PREFIX_FOR_RESOLVER = "sellerkyc";

    @Bean BeanFactoryAdapter sellerkycBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl sellerkycFlowStore(
            @Qualifier("sellerkycBeanFactoryAdapter") BeanFactoryAdapter sellerkycBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(sellerkycBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<SellerKyc> sellerkycEntityStm(@Qualifier("sellerkycFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<SellerKyc> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider sellerkycActionsInfoProvider(@Qualifier("sellerkycFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("sellerkyc",provider);
        return provider;
	}
	
	@Bean EntityStore<SellerKyc> sellerkycEntityStore() {
		return new SellerKycEntityStore();
	}
	
	@Bean StateEntityServiceImpl<SellerKyc> _sellerkycStateEntityService_(
			@Qualifier("sellerkycEntityStm") STM<SellerKyc> stm,
			@Qualifier("sellerkycActionsInfoProvider") STMActionsInfoProvider sellerkycInfoProvider,
			@Qualifier("sellerkycEntityStore") EntityStore<SellerKyc> entityStore){
		return new StateEntityServiceImpl<>(stm, sellerkycInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<SellerKyc> sellerkycEntryAction(@Qualifier("sellerkycEntityStore") EntityStore<SellerKyc> entityStore,
			@Qualifier("sellerkycActionsInfoProvider") STMActionsInfoProvider sellerkycInfoProvider,
            @Qualifier("sellerkycFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<SellerKyc> entryAction =  new GenericEntryAction<SellerKyc>(entityStore,sellerkycInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<SellerKyc> sellerkycExitAction(@Qualifier("sellerkycFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<SellerKyc> exitAction = new GenericExitAction<SellerKyc>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader sellerkycFlowReader(@Qualifier("sellerkycFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean SellerKycHealthChecker sellerkycHealthChecker(){
    	return new SellerKycHealthChecker();
    }

    @Bean STMTransitionAction<SellerKyc> defaultsellerkycSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver sellerkycTransitionActionResolver(
    @Qualifier("defaultsellerkycSTMTransitionAction") STMTransitionAction<SellerKyc> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector sellerkycBodyTypeSelector(
    @Qualifier("sellerkycActionsInfoProvider") STMActionsInfoProvider sellerkycInfoProvider,
    @Qualifier("sellerkycTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(sellerkycInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<SellerKyc> sellerkycBaseTransitionAction(
        @Qualifier("sellerkycTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "sellerkyc" + eventId for the method name. (sellerkyc is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/sellerkyc/sellerkyc-states.xml

    @Bean ReviewSellerKycAction
            sellerkycReview(){
        return new ReviewSellerKycAction();
    }
    @Bean ResubmitSellerKycAction
            sellerkycResubmit(){
        return new ResubmitSellerKycAction();
    }
    @Bean ApproveSellerKycAction
            sellerkycApprove(){
        return new ApproveSellerKycAction();
    }
    @Bean RejectSellerKycAction
            sellerkycReject(){
        return new RejectSellerKycAction();
    }
    @Bean RequestInfoSellerKycAction
            sellerkycRequestInfo(){
        return new RequestInfoSellerKycAction();
    }


}
