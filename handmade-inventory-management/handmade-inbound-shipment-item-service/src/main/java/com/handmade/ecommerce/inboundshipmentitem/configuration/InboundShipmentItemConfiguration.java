package com.handmade.ecommerce.inboundshipmentitem.configuration;

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
import com.handmade.ecommerce.inventory.model.InboundShipmentItem;
import com.handmade.ecommerce.inboundshipmentitem.service.cmds.*;
import com.handmade.ecommerce.inboundshipmentitem.service.healthcheck.InboundShipmentItemHealthChecker;
import com.handmade.ecommerce.inboundshipmentitem.service.store.InboundShipmentItemEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class InboundShipmentItemConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/inboundshipmentitem/inboundshipmentitem-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "InboundShipmentItem";
    public static final String PREFIX_FOR_RESOLVER = "inboundshipmentitem";

    @Bean BeanFactoryAdapter inboundshipmentitemBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl inboundshipmentitemFlowStore(
            @Qualifier("inboundshipmentitemBeanFactoryAdapter") BeanFactoryAdapter inboundshipmentitemBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(inboundshipmentitemBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<InboundShipmentItem> inboundshipmentitemEntityStm(@Qualifier("inboundshipmentitemFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<InboundShipmentItem> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider inboundshipmentitemActionsInfoProvider(@Qualifier("inboundshipmentitemFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("inboundshipmentitem",provider);
        return provider;
	}
	
	@Bean EntityStore<InboundShipmentItem> inboundshipmentitemEntityStore() {
		return new InboundShipmentItemEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<InboundShipmentItem> _inboundshipmentitemStateEntityService_(
			@Qualifier("inboundshipmentitemEntityStm") STM<InboundShipmentItem> stm,
			@Qualifier("inboundshipmentitemActionsInfoProvider") STMActionsInfoProvider inboundshipmentitemInfoProvider,
			@Qualifier("inboundshipmentitemEntityStore") EntityStore<InboundShipmentItem> entityStore){
		return new StateEntityServiceImpl<>(stm, inboundshipmentitemInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<InboundShipmentItem> inboundshipmentitemEntryAction(@Qualifier("inboundshipmentitemEntityStore") EntityStore<InboundShipmentItem> entityStore,
			@Qualifier("inboundshipmentitemActionsInfoProvider") STMActionsInfoProvider inboundshipmentitemInfoProvider,
            @Qualifier("inboundshipmentitemFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<InboundShipmentItem> entryAction =  new GenericEntryAction<InboundShipmentItem>(entityStore,inboundshipmentitemInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<InboundShipmentItem> inboundshipmentitemExitAction(@Qualifier("inboundshipmentitemFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<InboundShipmentItem> exitAction = new GenericExitAction<InboundShipmentItem>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader inboundshipmentitemFlowReader(@Qualifier("inboundshipmentitemFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean InboundShipmentItemHealthChecker inboundshipmentitemHealthChecker(){
    	return new InboundShipmentItemHealthChecker();
    }

    @Bean STMTransitionAction<InboundShipmentItem> defaultinboundshipmentitemSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver inboundshipmentitemTransitionActionResolver(
    @Qualifier("defaultinboundshipmentitemSTMTransitionAction") STMTransitionAction<InboundShipmentItem> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector inboundshipmentitemBodyTypeSelector(
    @Qualifier("inboundshipmentitemActionsInfoProvider") STMActionsInfoProvider inboundshipmentitemInfoProvider,
    @Qualifier("inboundshipmentitemTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(inboundshipmentitemInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<InboundShipmentItem> inboundshipmentitemBaseTransitionAction(
        @Qualifier("inboundshipmentitemTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "inboundshipmentitem" + eventId for the method name. (inboundshipmentitem is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/inboundshipmentitem/inboundshipmentitem-states.xml

    @Bean ReceiveInboundShipmentItemAction
            inboundshipmentitemReceive(){
        return new ReceiveInboundShipmentItemAction();
    }
    @Bean DamageInboundShipmentItemAction
            inboundshipmentitemDamage(){
        return new DamageInboundShipmentItemAction();
    }


}
