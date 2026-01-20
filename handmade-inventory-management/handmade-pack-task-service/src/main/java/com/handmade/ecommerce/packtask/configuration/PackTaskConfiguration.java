package com.handmade.ecommerce.packtask.configuration;

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
import com.handmade.ecommerce.inventory.model.PackTask;
import com.handmade.ecommerce.packtask.service.cmds.*;
import com.handmade.ecommerce.packtask.service.healthcheck.PackTaskHealthChecker;
import com.handmade.ecommerce.packtask.service.store.PackTaskEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PackTaskConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/packtask/packtask-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "PackTask";
    public static final String PREFIX_FOR_RESOLVER = "packtask";

    @Bean BeanFactoryAdapter packtaskBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl packtaskFlowStore(
            @Qualifier("packtaskBeanFactoryAdapter") BeanFactoryAdapter packtaskBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(packtaskBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<PackTask> packtaskEntityStm(@Qualifier("packtaskFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<PackTask> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider packtaskActionsInfoProvider(@Qualifier("packtaskFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("packtask",provider);
        return provider;
	}
	
	@Bean EntityStore<PackTask> packtaskEntityStore() {
		return new PackTaskEntityStore();
	}
	
	@Bean StateEntityServiceImpl<PackTask> _packtaskStateEntityService_(
			@Qualifier("packtaskEntityStm") STM<PackTask> stm,
			@Qualifier("packtaskActionsInfoProvider") STMActionsInfoProvider packtaskInfoProvider,
			@Qualifier("packtaskEntityStore") EntityStore<PackTask> entityStore){
		return new StateEntityServiceImpl<>(stm, packtaskInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<PackTask> packtaskEntryAction(@Qualifier("packtaskEntityStore") EntityStore<PackTask> entityStore,
			@Qualifier("packtaskActionsInfoProvider") STMActionsInfoProvider packtaskInfoProvider,
            @Qualifier("packtaskFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<PackTask> entryAction =  new GenericEntryAction<PackTask>(entityStore,packtaskInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<PackTask> packtaskExitAction(@Qualifier("packtaskFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<PackTask> exitAction = new GenericExitAction<PackTask>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader packtaskFlowReader(@Qualifier("packtaskFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PackTaskHealthChecker packtaskHealthChecker(){
    	return new PackTaskHealthChecker();
    }

    @Bean STMTransitionAction<PackTask> defaultpacktaskSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver packtaskTransitionActionResolver(
    @Qualifier("defaultpacktaskSTMTransitionAction") STMTransitionAction<PackTask> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector packtaskBodyTypeSelector(
    @Qualifier("packtaskActionsInfoProvider") STMActionsInfoProvider packtaskInfoProvider,
    @Qualifier("packtaskTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(packtaskInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<PackTask> packtaskBaseTransitionAction(
        @Qualifier("packtaskTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "packtask" + eventId for the method name. (packtask is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/packtask/packtask-states.xml

    @Bean CancelPackTaskAction
            packtaskCancel(){
        return new CancelPackTaskAction();
    }
    @Bean CompletePackTaskAction
            packtaskComplete(){
        return new CompletePackTaskAction();
    }
    @Bean StartPackTaskAction
            packtaskStart(){
        return new StartPackTaskAction();
    }


}
