package com.handmade.ecommerce.cmsentry.configuration;

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
import com.handmade.ecommerce.cms.model.CMSEntry;
import com.handmade.ecommerce.cmsentry.service.cmds.*;
import com.handmade.ecommerce.cmsentry.service.healthcheck.CMSEntryHealthChecker;
import com.handmade.ecommerce.cmsentry.service.store.CMSEntryEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class CMSEntryConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/cmsentry/cmsentry-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "CMSEntry";
    public static final String PREFIX_FOR_RESOLVER = "cmsentry";

    @Bean BeanFactoryAdapter cmsentryBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl cmsentryFlowStore(
            @Qualifier("cmsentryBeanFactoryAdapter") BeanFactoryAdapter cmsentryBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(cmsentryBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<CMSEntry> cmsentryEntityStm(@Qualifier("cmsentryFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<CMSEntry> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider cmsentryActionsInfoProvider(@Qualifier("cmsentryFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("cmsentry",provider);
        return provider;
	}
	
	@Bean EntityStore<CMSEntry> cmsentryEntityStore() {
		return new CMSEntryEntityStore();
	}
	
	@Bean StateEntityServiceImpl<CMSEntry> _cmsentryStateEntityService_(
			@Qualifier("cmsentryEntityStm") STM<CMSEntry> stm,
			@Qualifier("cmsentryActionsInfoProvider") STMActionsInfoProvider cmsentryInfoProvider,
			@Qualifier("cmsentryEntityStore") EntityStore<CMSEntry> entityStore){
		return new StateEntityServiceImpl<>(stm, cmsentryInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<CMSEntry> cmsentryEntryAction(@Qualifier("cmsentryEntityStore") EntityStore<CMSEntry> entityStore,
			@Qualifier("cmsentryActionsInfoProvider") STMActionsInfoProvider cmsentryInfoProvider,
            @Qualifier("cmsentryFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<CMSEntry> entryAction =  new GenericEntryAction<CMSEntry>(entityStore,cmsentryInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<CMSEntry> cmsentryExitAction(@Qualifier("cmsentryFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<CMSEntry> exitAction = new GenericExitAction<CMSEntry>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader cmsentryFlowReader(@Qualifier("cmsentryFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean CMSEntryHealthChecker cmsentryHealthChecker(){
    	return new CMSEntryHealthChecker();
    }

    @Bean STMTransitionAction<CMSEntry> defaultcmsentrySTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver cmsentryTransitionActionResolver(
    @Qualifier("defaultcmsentrySTMTransitionAction") STMTransitionAction<CMSEntry> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector cmsentryBodyTypeSelector(
    @Qualifier("cmsentryActionsInfoProvider") STMActionsInfoProvider cmsentryInfoProvider,
    @Qualifier("cmsentryTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(cmsentryInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<CMSEntry> cmsentryBaseTransitionAction(
        @Qualifier("cmsentryTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "cmsentry" + eventId for the method name. (cmsentry is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/cmsentry/cmsentry-states.xml

    @Bean UnpublishCMSEntryAction
            cmsentryUnpublish(){
        return new UnpublishCMSEntryAction();
    }
    @Bean ArchiveCMSEntryAction
            cmsentryArchive(){
        return new ArchiveCMSEntryAction();
    }
    @Bean PublishCMSEntryAction
            cmsentryPublish(){
        return new PublishCMSEntryAction();
    }


}
