package com.handmade.ecommerce.translation.configuration;

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
import com.handmade.ecommerce.localization.model.Translation;
import com.handmade.ecommerce.translation.service.cmds.*;
import com.handmade.ecommerce.translation.service.healthcheck.TranslationHealthChecker;
import com.handmade.ecommerce.translation.service.store.TranslationEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class TranslationConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/translation/translation-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Translation";
    public static final String PREFIX_FOR_RESOLVER = "translation";

    @Bean BeanFactoryAdapter translationBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl translationFlowStore(
            @Qualifier("translationBeanFactoryAdapter") BeanFactoryAdapter translationBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(translationBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<Translation> translationEntityStm(@Qualifier("translationFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Translation> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider translationActionsInfoProvider(@Qualifier("translationFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("translation",provider);
        return provider;
	}
	
	@Bean EntityStore<Translation> translationEntityStore() {
		return new TranslationEntityStore();
	}
	
	@Bean StateEntityServiceImpl<Translation> _translationStateEntityService_(
			@Qualifier("translationEntityStm") STM<Translation> stm,
			@Qualifier("translationActionsInfoProvider") STMActionsInfoProvider translationInfoProvider,
			@Qualifier("translationEntityStore") EntityStore<Translation> entityStore){
		return new StateEntityServiceImpl<>(stm, translationInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<Translation> translationEntryAction(@Qualifier("translationEntityStore") EntityStore<Translation> entityStore,
			@Qualifier("translationActionsInfoProvider") STMActionsInfoProvider translationInfoProvider,
            @Qualifier("translationFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Translation> entryAction =  new GenericEntryAction<Translation>(entityStore,translationInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Translation> translationExitAction(@Qualifier("translationFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Translation> exitAction = new GenericExitAction<Translation>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader translationFlowReader(@Qualifier("translationFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean TranslationHealthChecker translationHealthChecker(){
    	return new TranslationHealthChecker();
    }

    @Bean STMTransitionAction<Translation> defaulttranslationSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver translationTransitionActionResolver(
    @Qualifier("defaulttranslationSTMTransitionAction") STMTransitionAction<Translation> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector translationBodyTypeSelector(
    @Qualifier("translationActionsInfoProvider") STMActionsInfoProvider translationInfoProvider,
    @Qualifier("translationTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(translationInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<Translation> translationBaseTransitionAction(
        @Qualifier("translationTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "translation" + eventId for the method name. (translation is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/translation/translation-states.xml

    @Bean UnpublishTranslationAction
            translationUnpublish(){
        return new UnpublishTranslationAction();
    }
    @Bean ArchiveTranslationAction
            translationArchive(){
        return new ArchiveTranslationAction();
    }
    @Bean SubmitForReviewTranslationAction
            translationSubmitForReview(){
        return new SubmitForReviewTranslationAction();
    }
    @Bean ApproveTranslationAction
            translationApprove(){
        return new ApproveTranslationAction();
    }
    @Bean RejectTranslationAction
            translationReject(){
        return new RejectTranslationAction();
    }
    @Bean PublishTranslationAction
            translationPublish(){
        return new PublishTranslationAction();
    }


}
