package com.handmade.ecommerce.platform.configuration;

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
import com.handmade.ecommerce.platform.model.Platform;
import com.handmade.ecommerce.platform.service.cmds.*;
import com.handmade.ecommerce.platform.service.healthcheck.PlatformHealthChecker;
import com.handmade.ecommerce.platform.service.store.PlatformEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PlatformConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/platform/platform-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Platform";
    public static final String PREFIX_FOR_RESOLVER = "platform";

    @Bean BeanFactoryAdapter platformBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl platformFlowStore(
            @Qualifier("platformBeanFactoryAdapter") BeanFactoryAdapter platformBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(platformBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<Platform> platformEntityStm(@Qualifier("platformFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Platform> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider platformActionsInfoProvider(@Qualifier("platformFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("platform",provider);
        return provider;
	}
	
	@Bean EntityStore<Platform> platformEntityStore() {
		return new PlatformEntityStore();
	}
	
	@Bean StateEntityServiceImpl<Platform> _platformStateEntityService_(
			@Qualifier("platformEntityStm") STM<Platform> stm,
			@Qualifier("platformActionsInfoProvider") STMActionsInfoProvider platformInfoProvider,
			@Qualifier("platformEntityStore") EntityStore<Platform> entityStore){
		return new StateEntityServiceImpl<>(stm, platformInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<Platform> platformEntryAction(@Qualifier("platformEntityStore") EntityStore<Platform> entityStore,
			@Qualifier("platformActionsInfoProvider") STMActionsInfoProvider platformInfoProvider,
            @Qualifier("platformFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Platform> entryAction =  new GenericEntryAction<Platform>(entityStore,platformInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Platform> platformExitAction(@Qualifier("platformFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Platform> exitAction = new GenericExitAction<Platform>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader platformFlowReader(@Qualifier("platformFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PlatformHealthChecker platformHealthChecker(){
    	return new PlatformHealthChecker();
    }

    @Bean STMTransitionAction<Platform> defaultplatformSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver platformTransitionActionResolver(
    @Qualifier("defaultplatformSTMTransitionAction") STMTransitionAction<Platform> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector platformBodyTypeSelector(
    @Qualifier("platformActionsInfoProvider") STMActionsInfoProvider platformInfoProvider,
    @Qualifier("platformTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(platformInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<Platform> platformBaseTransitionAction(
        @Qualifier("platformTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "platform" + eventId for the method name. (platform is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/platform/platform-states.xml

    @Bean SuspendPlatformAction
            suspendPlatformAction(){
        return new SuspendPlatformAction();
    }
    @Bean DeactivatePlatformAction
            deactivatePlatformAction(){
        return new DeactivatePlatformAction();
    }
    @Bean SubmitPlatformAction
            submitPlatformAction(){
        return new SubmitPlatformAction();
    }
    @Bean ArchivePlatformAction
            archivePlatformAction(){
        return new ArchivePlatformAction();
    }
    @Bean ReactivatePlatformAction
            reactivatePlatformAction(){
        return new ReactivatePlatformAction();
    }

    @Bean ApprovePlatformAction
            approvePlatformAction(){
        return new ApprovePlatformAction();
    }
    @Bean RejectPlatformAction
            rejectPlatformAction(){
        return new RejectPlatformAction();
    }
    @Bean ActivatePlatformAction
            activatePlatformAction(){
        return new ActivatePlatformAction();
    }


}
