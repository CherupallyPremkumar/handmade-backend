package com.handmade.ecommerce.picktask.configuration;

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
import com.handmade.ecommerce.inventory.model.PickTask;
import com.handmade.ecommerce.picktask.service.cmds.*;
import com.handmade.ecommerce.picktask.service.healthcheck.PickTaskHealthChecker;
import com.handmade.ecommerce.picktask.service.store.PickTaskEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PickTaskConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/picktask/picktask-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "PickTask";
    public static final String PREFIX_FOR_RESOLVER = "picktask";

    @Bean BeanFactoryAdapter picktaskBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl picktaskFlowStore(
            @Qualifier("picktaskBeanFactoryAdapter") BeanFactoryAdapter picktaskBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(picktaskBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<PickTask> picktaskEntityStm(@Qualifier("picktaskFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<PickTask> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider picktaskActionsInfoProvider(@Qualifier("picktaskFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("picktask",provider);
        return provider;
	}
	
	@Bean EntityStore<PickTask> picktaskEntityStore() {
		return new PickTaskEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<PickTask> _picktaskStateEntityService_(
			@Qualifier("picktaskEntityStm") STM<PickTask> stm,
			@Qualifier("picktaskActionsInfoProvider") STMActionsInfoProvider picktaskInfoProvider,
			@Qualifier("picktaskEntityStore") EntityStore<PickTask> entityStore){
		return new StateEntityServiceImpl<>(stm, picktaskInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<PickTask> picktaskEntryAction(@Qualifier("picktaskEntityStore") EntityStore<PickTask> entityStore,
			@Qualifier("picktaskActionsInfoProvider") STMActionsInfoProvider picktaskInfoProvider,
            @Qualifier("picktaskFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<PickTask> entryAction =  new GenericEntryAction<PickTask>(entityStore,picktaskInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<PickTask> picktaskExitAction(@Qualifier("picktaskFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<PickTask> exitAction = new GenericExitAction<PickTask>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader picktaskFlowReader(@Qualifier("picktaskFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PickTaskHealthChecker picktaskHealthChecker(){
    	return new PickTaskHealthChecker();
    }

    @Bean STMTransitionAction<PickTask> defaultpicktaskSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver picktaskTransitionActionResolver(
    @Qualifier("defaultpicktaskSTMTransitionAction") STMTransitionAction<PickTask> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector picktaskBodyTypeSelector(
    @Qualifier("picktaskActionsInfoProvider") STMActionsInfoProvider picktaskInfoProvider,
    @Qualifier("picktaskTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(picktaskInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<PickTask> picktaskBaseTransitionAction(
        @Qualifier("picktaskTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "picktask" + eventId for the method name. (picktask is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/picktask/picktask-states.xml

    @Bean CancelPickTaskAction
            picktaskCancel(){
        return new CancelPickTaskAction();
    }
    @Bean CompletePickTaskAction
            picktaskComplete(){
        return new CompletePickTaskAction();
    }
    @Bean StartPickTaskAction
            picktaskStart(){
        return new StartPickTaskAction();
    }


}
