package com.handmade.ecommerce.shippinglabel.configuration;

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
import com.handmade.ecommerce.logistics.model.ShippingLabel;
import com.handmade.ecommerce.shippinglabel.service.cmds.*;
import com.handmade.ecommerce.shippinglabel.service.healthcheck.ShippingLabelHealthChecker;
import com.handmade.ecommerce.shippinglabel.service.store.ShippingLabelEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class ShippingLabelConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/shippinglabel/shippinglabel-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "ShippingLabel";
    public static final String PREFIX_FOR_RESOLVER = "shippinglabel";

    @Bean BeanFactoryAdapter shippinglabelBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl shippinglabelFlowStore(
            @Qualifier("shippinglabelBeanFactoryAdapter") BeanFactoryAdapter shippinglabelBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(shippinglabelBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<ShippingLabel> shippinglabelEntityStm(@Qualifier("shippinglabelFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<ShippingLabel> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider shippinglabelActionsInfoProvider(@Qualifier("shippinglabelFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("shippinglabel",provider);
        return provider;
	}
	
	@Bean EntityStore<ShippingLabel> shippinglabelEntityStore() {
		return new ShippingLabelEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<ShippingLabel> _shippinglabelStateEntityService_(
			@Qualifier("shippinglabelEntityStm") STM<ShippingLabel> stm,
			@Qualifier("shippinglabelActionsInfoProvider") STMActionsInfoProvider shippinglabelInfoProvider,
			@Qualifier("shippinglabelEntityStore") EntityStore<ShippingLabel> entityStore){
		return new StateEntityServiceImpl<>(stm, shippinglabelInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<ShippingLabel> shippinglabelEntryAction(@Qualifier("shippinglabelEntityStore") EntityStore<ShippingLabel> entityStore,
			@Qualifier("shippinglabelActionsInfoProvider") STMActionsInfoProvider shippinglabelInfoProvider,
            @Qualifier("shippinglabelFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<ShippingLabel> entryAction =  new GenericEntryAction<ShippingLabel>(entityStore,shippinglabelInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<ShippingLabel> shippinglabelExitAction(@Qualifier("shippinglabelFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<ShippingLabel> exitAction = new GenericExitAction<ShippingLabel>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader shippinglabelFlowReader(@Qualifier("shippinglabelFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean ShippingLabelHealthChecker shippinglabelHealthChecker(){
    	return new ShippingLabelHealthChecker();
    }

    @Bean STMTransitionAction<ShippingLabel> defaultshippinglabelSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver shippinglabelTransitionActionResolver(
    @Qualifier("defaultshippinglabelSTMTransitionAction") STMTransitionAction<ShippingLabel> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector shippinglabelBodyTypeSelector(
    @Qualifier("shippinglabelActionsInfoProvider") STMActionsInfoProvider shippinglabelInfoProvider,
    @Qualifier("shippinglabelTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(shippinglabelInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<ShippingLabel> shippinglabelBaseTransitionAction(
        @Qualifier("shippinglabelTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "shippinglabel" + eventId for the method name. (shippinglabel is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/shippinglabel/shippinglabel-states.xml

    @Bean PrintShippingLabelAction
            shippinglabelPrint(){
        return new PrintShippingLabelAction();
    }
    @Bean VoidShippingLabelAction
            shippinglabelVoid(){
        return new VoidShippingLabelAction();
    }
    @Bean VoidShippingLabelAction
            shippinglabelVoid(){
        return new VoidShippingLabelAction();
    }
    @Bean UseShippingLabelAction
            shippinglabelUse(){
        return new UseShippingLabelAction();
    }


}
