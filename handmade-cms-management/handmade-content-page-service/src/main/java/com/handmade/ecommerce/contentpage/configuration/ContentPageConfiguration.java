package com.handmade.ecommerce.contentpage.configuration;

import com.handmade.ecommerce.cms.model.ContentPage;
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
import com.handmade.ecommerce.contentpage.service.cmds.*;
import com.handmade.ecommerce.contentpage.service.healthcheck.ContentPageHealthChecker;
import com.handmade.ecommerce.contentpage.service.store.ContentPageEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class ContentPageConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/contentpage/contentpage-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "ContentPage";
    public static final String PREFIX_FOR_RESOLVER = "contentpage";

    @Bean BeanFactoryAdapter contentpageBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl contentpageFlowStore(
            @Qualifier("contentpageBeanFactoryAdapter") BeanFactoryAdapter contentpageBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(contentpageBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<ContentPage> contentpageEntityStm(@Qualifier("contentpageFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<ContentPage> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider contentpageActionsInfoProvider(@Qualifier("contentpageFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("contentpage",provider);
        return provider;
	}
	
	@Bean EntityStore<ContentPage> contentpageEntityStore() {
		return new ContentPageEntityStore();
	}
	
	@Bean StateEntityServiceImpl<ContentPage> _contentpageStateEntityService_(
			@Qualifier("contentpageEntityStm") STM<ContentPage> stm,
			@Qualifier("contentpageActionsInfoProvider") STMActionsInfoProvider contentpageInfoProvider,
			@Qualifier("contentpageEntityStore") EntityStore<ContentPage> entityStore){
		return new StateEntityServiceImpl<>(stm, contentpageInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<ContentPage> contentpageEntryAction(@Qualifier("contentpageEntityStore") EntityStore<ContentPage> entityStore,
			@Qualifier("contentpageActionsInfoProvider") STMActionsInfoProvider contentpageInfoProvider,
            @Qualifier("contentpageFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<ContentPage> entryAction =  new GenericEntryAction<ContentPage>(entityStore,contentpageInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<ContentPage> contentpageExitAction(@Qualifier("contentpageFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<ContentPage> exitAction = new GenericExitAction<ContentPage>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader contentpageFlowReader(@Qualifier("contentpageFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean ContentPageHealthChecker contentpageHealthChecker(){
    	return new ContentPageHealthChecker();
    }

    @Bean STMTransitionAction<ContentPage> defaultcontentpageSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver contentpageTransitionActionResolver(
    @Qualifier("defaultcontentpageSTMTransitionAction") STMTransitionAction<ContentPage> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector contentpageBodyTypeSelector(
    @Qualifier("contentpageActionsInfoProvider") STMActionsInfoProvider contentpageInfoProvider,
    @Qualifier("contentpageTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(contentpageInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<ContentPage> contentpageBaseTransitionAction(
        @Qualifier("contentpageTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "contentpage" + eventId for the method name. (contentpage is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/contentpage/contentpage-states.xml

    @Bean UnpublishContentPageAction
            unpublishContentPageAction(){
        return new UnpublishContentPageAction();
    }
    @Bean ArchiveContentPageAction
            archiveContentPageAction(){
        return new ArchiveContentPageAction();
    }
    @Bean PublishContentPageAction
            publishContentPageAction(){
        return new PublishContentPageAction();
    }
    @Bean RestoreContentPageAction
            restoreContentPageAction(){
        return new RestoreContentPageAction();
    }


}
