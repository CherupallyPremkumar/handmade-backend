package com.handmade.ecommerce.shipment.configuration;

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
import com.handmade.ecommerce.shipping.model.Shipment;
import com.handmade.ecommerce.shipment.service.cmds.*;
import com.handmade.ecommerce.shipment.service.healthcheck.ShipmentHealthChecker;
import com.handmade.ecommerce.shipment.service.store.ShipmentEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class ShipmentConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/shipment/shipment-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Shipment";
    public static final String PREFIX_FOR_RESOLVER = "shipment";

    @Bean BeanFactoryAdapter shipmentBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl shipmentFlowStore(
            @Qualifier("shipmentBeanFactoryAdapter") BeanFactoryAdapter shipmentBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(shipmentBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Shipment> shipmentEntityStm(@Qualifier("shipmentFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Shipment> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider shipmentActionsInfoProvider(@Qualifier("shipmentFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("shipment",provider);
        return provider;
	}
	
	@Bean EntityStore<Shipment> shipmentEntityStore() {
		return new ShipmentEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Shipment> _shipmentStateEntityService_(
			@Qualifier("shipmentEntityStm") STM<Shipment> stm,
			@Qualifier("shipmentActionsInfoProvider") STMActionsInfoProvider shipmentInfoProvider,
			@Qualifier("shipmentEntityStore") EntityStore<Shipment> entityStore){
		return new StateEntityServiceImpl<>(stm, shipmentInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Shipment> shipmentEntryAction(@Qualifier("shipmentEntityStore") EntityStore<Shipment> entityStore,
			@Qualifier("shipmentActionsInfoProvider") STMActionsInfoProvider shipmentInfoProvider,
            @Qualifier("shipmentFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Shipment> entryAction =  new GenericEntryAction<Shipment>(entityStore,shipmentInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Shipment> shipmentExitAction(@Qualifier("shipmentFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Shipment> exitAction = new GenericExitAction<Shipment>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader shipmentFlowReader(@Qualifier("shipmentFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean ShipmentHealthChecker shipmentHealthChecker(){
    	return new ShipmentHealthChecker();
    }

    @Bean STMTransitionAction<Shipment> defaultshipmentSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver shipmentTransitionActionResolver(
    @Qualifier("defaultshipmentSTMTransitionAction") STMTransitionAction<Shipment> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector shipmentBodyTypeSelector(
    @Qualifier("shipmentActionsInfoProvider") STMActionsInfoProvider shipmentInfoProvider,
    @Qualifier("shipmentTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(shipmentInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<Shipment> shipmentBaseTransitionAction(
        @Qualifier("shipmentTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "shipment" + eventId for the method name. (shipment is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/shipment/shipment-states.xml

    @Bean BookShipmentAction
            shipmentBook(){
        return new BookShipmentAction();
    }
    @Bean ExceptionShipmentAction
            shipmentException(){
        return new ExceptionShipmentAction();
    }
    @Bean OutForDeliveryShipmentAction
            shipmentOutForDelivery(){
        return new OutForDeliveryShipmentAction();
    }
    @Bean CancelShipmentAction
            shipmentCancel(){
        return new CancelShipmentAction();
    }
    @Bean PickupShipmentAction
            shipmentPickup(){
        return new PickupShipmentAction();
    }
    @Bean TransitShipmentAction
            shipmentTransit(){
        return new TransitShipmentAction();
    }
    @Bean ReturnToSenderShipmentAction
            shipmentReturnToSender(){
        return new ReturnToSenderShipmentAction();
    }
    @Bean RetryShipmentAction
            shipmentRetry(){
        return new RetryShipmentAction();
    }
    @Bean ExceptionShipmentAction
            shipmentException(){
        return new ExceptionShipmentAction();
    }
    @Bean DeliverShipmentAction
            shipmentDeliver(){
        return new DeliverShipmentAction();
    }


}
