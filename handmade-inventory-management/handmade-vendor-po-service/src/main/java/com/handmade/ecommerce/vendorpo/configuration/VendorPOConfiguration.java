package com.handmade.ecommerce.vendorpo.configuration;

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
import com.handmade.ecommerce.inventory.model.VendorPO;
import com.handmade.ecommerce.vendorpo.service.cmds.*;
import com.handmade.ecommerce.vendorpo.service.healthcheck.VendorPOHealthChecker;
import com.handmade.ecommerce.vendorpo.service.store.VendorPOEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class VendorPOConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/vendorpo/vendorpo-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "VendorPO";
    public static final String PREFIX_FOR_RESOLVER = "vendorpo";

    @Bean BeanFactoryAdapter vendorpoBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl vendorpoFlowStore(
            @Qualifier("vendorpoBeanFactoryAdapter") BeanFactoryAdapter vendorpoBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(vendorpoBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<VendorPO> vendorpoEntityStm(@Qualifier("vendorpoFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<VendorPO> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider vendorpoActionsInfoProvider(@Qualifier("vendorpoFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("vendorpo",provider);
        return provider;
	}
	
	@Bean EntityStore<VendorPO> vendorpoEntityStore() {
		return new VendorPOEntityStore();
	}
	
	@Bean StateEntityServiceImpl<VendorPO> _vendorpoStateEntityService_(
			@Qualifier("vendorpoEntityStm") STM<VendorPO> stm,
			@Qualifier("vendorpoActionsInfoProvider") STMActionsInfoProvider vendorpoInfoProvider,
			@Qualifier("vendorpoEntityStore") EntityStore<VendorPO> entityStore){
		return new StateEntityServiceImpl<>(stm, vendorpoInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<VendorPO> vendorpoEntryAction(@Qualifier("vendorpoEntityStore") EntityStore<VendorPO> entityStore,
			@Qualifier("vendorpoActionsInfoProvider") STMActionsInfoProvider vendorpoInfoProvider,
            @Qualifier("vendorpoFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<VendorPO> entryAction =  new GenericEntryAction<VendorPO>(entityStore,vendorpoInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<VendorPO> vendorpoExitAction(@Qualifier("vendorpoFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<VendorPO> exitAction = new GenericExitAction<VendorPO>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader vendorpoFlowReader(@Qualifier("vendorpoFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean VendorPOHealthChecker vendorpoHealthChecker(){
    	return new VendorPOHealthChecker();
    }

    @Bean STMTransitionAction<VendorPO> defaultvendorpoSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver vendorpoTransitionActionResolver(
    @Qualifier("defaultvendorpoSTMTransitionAction") STMTransitionAction<VendorPO> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector vendorpoBodyTypeSelector(
    @Qualifier("vendorpoActionsInfoProvider") STMActionsInfoProvider vendorpoInfoProvider,
    @Qualifier("vendorpoTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(vendorpoInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<VendorPO> vendorpoBaseTransitionAction(
        @Qualifier("vendorpoTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "vendorpo" + eventId for the method name. (vendorpo is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/vendorpo/vendorpo-states.xml

    @Bean SubmitVendorPOAction
            vendorpoSubmit(){
        return new SubmitVendorPOAction();
    }
    @Bean ApproveVendorPOAction
            vendorpoApprove(){
        return new ApproveVendorPOAction();
    }
    @Bean RejectVendorPOAction
            vendorpoReject(){
        return new RejectVendorPOAction();
    }
    @Bean ReceiveVendorPOAction
            vendorpoReceive(){
        return new ReceiveVendorPOAction();
    }


}
