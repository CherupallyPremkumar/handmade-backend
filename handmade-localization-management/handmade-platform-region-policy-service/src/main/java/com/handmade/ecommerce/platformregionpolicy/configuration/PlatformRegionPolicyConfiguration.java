package com.handmade.ecommerce.platformregionpolicy.configuration;

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
import com.handmade.ecommerce.localization.model.PlatformRegionPolicy;
import com.handmade.ecommerce.platformregionpolicy.service.cmds.*;
import com.handmade.ecommerce.platformregionpolicy.service.healthcheck.PlatformRegionPolicyHealthChecker;
import com.handmade.ecommerce.platformregionpolicy.service.store.PlatformRegionPolicyEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PlatformRegionPolicyConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/platformregionpolicy/platformregionpolicy-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "PlatformRegionPolicy";
    public static final String PREFIX_FOR_RESOLVER = "platformregionpolicy";

    @Bean BeanFactoryAdapter platformregionpolicyBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl platformregionpolicyFlowStore(
            @Qualifier("platformregionpolicyBeanFactoryAdapter") BeanFactoryAdapter platformregionpolicyBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(platformregionpolicyBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<PlatformRegionPolicy> platformregionpolicyEntityStm(@Qualifier("platformregionpolicyFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<PlatformRegionPolicy> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider platformregionpolicyActionsInfoProvider(@Qualifier("platformregionpolicyFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("platformregionpolicy",provider);
        return provider;
	}
	
	@Bean EntityStore<PlatformRegionPolicy> platformregionpolicyEntityStore() {
		return new PlatformRegionPolicyEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<PlatformRegionPolicy> _platformregionpolicyStateEntityService_(
			@Qualifier("platformregionpolicyEntityStm") STM<PlatformRegionPolicy> stm,
			@Qualifier("platformregionpolicyActionsInfoProvider") STMActionsInfoProvider platformregionpolicyInfoProvider,
			@Qualifier("platformregionpolicyEntityStore") EntityStore<PlatformRegionPolicy> entityStore){
		return new StateEntityServiceImpl<>(stm, platformregionpolicyInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<PlatformRegionPolicy> platformregionpolicyEntryAction(@Qualifier("platformregionpolicyEntityStore") EntityStore<PlatformRegionPolicy> entityStore,
			@Qualifier("platformregionpolicyActionsInfoProvider") STMActionsInfoProvider platformregionpolicyInfoProvider,
            @Qualifier("platformregionpolicyFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<PlatformRegionPolicy> entryAction =  new GenericEntryAction<PlatformRegionPolicy>(entityStore,platformregionpolicyInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<PlatformRegionPolicy> platformregionpolicyExitAction(@Qualifier("platformregionpolicyFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<PlatformRegionPolicy> exitAction = new GenericExitAction<PlatformRegionPolicy>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader platformregionpolicyFlowReader(@Qualifier("platformregionpolicyFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PlatformRegionPolicyHealthChecker platformregionpolicyHealthChecker(){
    	return new PlatformRegionPolicyHealthChecker();
    }

    @Bean STMTransitionAction<PlatformRegionPolicy> defaultplatformregionpolicySTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver platformregionpolicyTransitionActionResolver(
    @Qualifier("defaultplatformregionpolicySTMTransitionAction") STMTransitionAction<PlatformRegionPolicy> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector platformregionpolicyBodyTypeSelector(
    @Qualifier("platformregionpolicyActionsInfoProvider") STMActionsInfoProvider platformregionpolicyInfoProvider,
    @Qualifier("platformregionpolicyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(platformregionpolicyInfoProvider,stmTransitionActionResolver);
    }

    @Bean @Autowired STMTransitionAction<PlatformRegionPolicy> platformregionpolicyBaseTransitionAction(
        @Qualifier("platformregionpolicyTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "platformregionpolicy" + eventId for the method name. (platformregionpolicy is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/platformregionpolicy/platformregionpolicy-states.xml

    @Bean DeprecatePlatformRegionPolicyAction
            platformregionpolicyDeprecate(){
        return new DeprecatePlatformRegionPolicyAction();
    }
    @Bean SubmitForReviewPlatformRegionPolicyAction
            platformregionpolicySubmitForReview(){
        return new SubmitForReviewPlatformRegionPolicyAction();
    }
    @Bean ApprovePlatformRegionPolicyAction
            platformregionpolicyApprove(){
        return new ApprovePlatformRegionPolicyAction();
    }
    @Bean RejectPlatformRegionPolicyAction
            platformregionpolicyReject(){
        return new RejectPlatformRegionPolicyAction();
    }
    @Bean ActivatePlatformRegionPolicyAction
            platformregionpolicyActivate(){
        return new ActivatePlatformRegionPolicyAction();
    }


}
