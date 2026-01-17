package com.handmade.ecommerce.inboundshipment.configuration;

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
import com.handmade.ecommerce.inventory.model.InboundShipment;
import com.handmade.ecommerce.inboundshipment.service.cmds.*;
import com.handmade.ecommerce.inboundshipment.service.healthcheck.InboundShipmentHealthChecker;
import com.handmade.ecommerce.inboundshipment.service.store.InboundShipmentEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class InboundShipmentConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/inboundshipment/inboundshipment-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "InboundShipment";
    public static final String PREFIX_FOR_RESOLVER = "inboundshipment";

    @Bean BeanFactoryAdapter inboundshipmentBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl inboundshipmentFlowStore(
            @Qualifier("inboundshipmentBeanFactoryAdapter") BeanFactoryAdapter inboundshipmentBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(inboundshipmentBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<InboundShipment> inboundshipmentEntityStm(@Qualifier("inboundshipmentFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<InboundShipment> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider inboundshipmentActionsInfoProvider(@Qualifier("inboundshipmentFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("inboundshipment",provider);
        return provider;
	}
	
	@Bean EntityStore<InboundShipment> inboundshipmentEntityStore() {
		return new InboundShipmentEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<InboundShipment> _inboundshipmentStateEntityService_(
			@Qualifier("inboundshipmentEntityStm") STM<InboundShipment> stm,
			@Qualifier("inboundshipmentActionsInfoProvider") STMActionsInfoProvider inboundshipmentInfoProvider,
			@Qualifier("inboundshipmentEntityStore") EntityStore<InboundShipment> entityStore){
		return new StateEntityServiceImpl<>(stm, inboundshipmentInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<InboundShipment> inboundshipmentEntryAction(@Qualifier("inboundshipmentEntityStore") EntityStore<InboundShipment> entityStore,
			@Qualifier("inboundshipmentActionsInfoProvider") STMActionsInfoProvider inboundshipmentInfoProvider,
            @Qualifier("inboundshipmentFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<InboundShipment> entryAction =  new GenericEntryAction<InboundShipment>(entityStore,inboundshipmentInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<InboundShipment> inboundshipmentExitAction(@Qualifier("inboundshipmentFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<InboundShipment> exitAction = new GenericExitAction<InboundShipment>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader inboundshipmentFlowReader(@Qualifier("inboundshipmentFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean InboundShipmentHealthChecker inboundshipmentHealthChecker(){
    	return new InboundShipmentHealthChecker();
    }

    @Bean STMTransitionAction<InboundShipment> defaultinboundshipmentSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver inboundshipmentTransitionActionResolver(
    @Qualifier("defaultinboundshipmentSTMTransitionAction") STMTransitionAction<InboundShipment> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector inboundshipmentBodyTypeSelector(
    @Qualifier("inboundshipmentActionsInfoProvider") STMActionsInfoProvider inboundshipmentInfoProvider,
    @Qualifier("inboundshipmentTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(inboundshipmentInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<InboundShipment> inboundshipmentBaseTransitionAction(
        @Qualifier("inboundshipmentTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "inboundshipment" + eventId for the method name. (inboundshipment is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/inboundshipment/inboundshipment-states.xml

    @Bean CancelInboundShipmentAction
            inboundshipmentCancel(){
        return new CancelInboundShipmentAction();
    }
    @Bean CompleteInboundShipmentAction
            inboundshipmentComplete(){
        return new CompleteInboundShipmentAction();
    }
    @Bean ReceiveInboundShipmentAction
            inboundshipmentReceive(){
        return new ReceiveInboundShipmentAction();
    }


}
