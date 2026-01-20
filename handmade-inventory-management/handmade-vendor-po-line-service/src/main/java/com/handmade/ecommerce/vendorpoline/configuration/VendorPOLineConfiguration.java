package com.handmade.ecommerce.vendorpoline.configuration;

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
import com.handmade.ecommerce.inventory.model.VendorPOLine;
import com.handmade.ecommerce.vendorpoline.service.cmds.*;
import com.handmade.ecommerce.vendorpoline.service.healthcheck.VendorPOLineHealthChecker;
import com.handmade.ecommerce.vendorpoline.service.store.VendorPOLineEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class VendorPOLineConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/vendorpoline/vendorpoline-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "VendorPOLine";
    public static final String PREFIX_FOR_RESOLVER = "vendorpoline";

    @Bean BeanFactoryAdapter vendorpolineBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl vendorpolineFlowStore(
            @Qualifier("vendorpolineBeanFactoryAdapter") BeanFactoryAdapter vendorpolineBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(vendorpolineBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<VendorPOLine> vendorpolineEntityStm(@Qualifier("vendorpolineFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<VendorPOLine> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider vendorpolineActionsInfoProvider(@Qualifier("vendorpolineFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("vendorpoline",provider);
        return provider;
	}
	
	@Bean EntityStore<VendorPOLine> vendorpolineEntityStore() {
		return new VendorPOLineEntityStore();
	}
	
	@Bean StateEntityServiceImpl<VendorPOLine> _vendorpolineStateEntityService_(
			@Qualifier("vendorpolineEntityStm") STM<VendorPOLine> stm,
			@Qualifier("vendorpolineActionsInfoProvider") STMActionsInfoProvider vendorpolineInfoProvider,
			@Qualifier("vendorpolineEntityStore") EntityStore<VendorPOLine> entityStore){
		return new StateEntityServiceImpl<>(stm, vendorpolineInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<VendorPOLine> vendorpolineEntryAction(@Qualifier("vendorpolineEntityStore") EntityStore<VendorPOLine> entityStore,
			@Qualifier("vendorpolineActionsInfoProvider") STMActionsInfoProvider vendorpolineInfoProvider,
            @Qualifier("vendorpolineFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<VendorPOLine> entryAction =  new GenericEntryAction<VendorPOLine>(entityStore,vendorpolineInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<VendorPOLine> vendorpolineExitAction(@Qualifier("vendorpolineFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<VendorPOLine> exitAction = new GenericExitAction<VendorPOLine>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader vendorpolineFlowReader(@Qualifier("vendorpolineFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean VendorPOLineHealthChecker vendorpolineHealthChecker(){
    	return new VendorPOLineHealthChecker();
    }

    @Bean STMTransitionAction<VendorPOLine> defaultvendorpolineSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver vendorpolineTransitionActionResolver(
    @Qualifier("defaultvendorpolineSTMTransitionAction") STMTransitionAction<VendorPOLine> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector vendorpolineBodyTypeSelector(
    @Qualifier("vendorpolineActionsInfoProvider") STMActionsInfoProvider vendorpolineInfoProvider,
    @Qualifier("vendorpolineTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(vendorpolineInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<VendorPOLine> vendorpolineBaseTransitionAction(
        @Qualifier("vendorpolineTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "vendorpoline" + eventId for the method name. (vendorpoline is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/vendorpoline/vendorpoline-states.xml

    @Bean CancelVendorPOLineAction
            vendorpolineCancel(){
        return new CancelVendorPOLineAction();
    }
    @Bean ReceiveVendorPOLineAction
            vendorpolineReceive(){
        return new ReceiveVendorPOLineAction();
    }


}
