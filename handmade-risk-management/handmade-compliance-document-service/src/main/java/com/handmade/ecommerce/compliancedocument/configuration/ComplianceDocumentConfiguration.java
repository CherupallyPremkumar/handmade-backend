package com.handmade.ecommerce.compliancedocument.configuration;

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
import com.handmade.ecommerce.risk.model.ComplianceDocument;
import com.handmade.ecommerce.compliancedocument.service.cmds.*;
import com.handmade.ecommerce.compliancedocument.service.healthcheck.ComplianceDocumentHealthChecker;
import com.handmade.ecommerce.compliancedocument.service.store.ComplianceDocumentEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class ComplianceDocumentConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/compliancedocument/compliancedocument-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "ComplianceDocument";
    public static final String PREFIX_FOR_RESOLVER = "compliancedocument";

    @Bean BeanFactoryAdapter compliancedocumentBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl compliancedocumentFlowStore(
            @Qualifier("compliancedocumentBeanFactoryAdapter") BeanFactoryAdapter compliancedocumentBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(compliancedocumentBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<ComplianceDocument> compliancedocumentEntityStm(@Qualifier("compliancedocumentFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<ComplianceDocument> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider compliancedocumentActionsInfoProvider(@Qualifier("compliancedocumentFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("compliancedocument",provider);
        return provider;
	}
	
	@Bean EntityStore<ComplianceDocument> compliancedocumentEntityStore() {
		return new ComplianceDocumentEntityStore();
	}
	
	@Bean StateEntityServiceImpl<ComplianceDocument> _compliancedocumentStateEntityService_(
			@Qualifier("compliancedocumentEntityStm") STM<ComplianceDocument> stm,
			@Qualifier("compliancedocumentActionsInfoProvider") STMActionsInfoProvider compliancedocumentInfoProvider,
			@Qualifier("compliancedocumentEntityStore") EntityStore<ComplianceDocument> entityStore){
		return new StateEntityServiceImpl<>(stm, compliancedocumentInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<ComplianceDocument> compliancedocumentEntryAction(@Qualifier("compliancedocumentEntityStore") EntityStore<ComplianceDocument> entityStore,
			@Qualifier("compliancedocumentActionsInfoProvider") STMActionsInfoProvider compliancedocumentInfoProvider,
            @Qualifier("compliancedocumentFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<ComplianceDocument> entryAction =  new GenericEntryAction<ComplianceDocument>(entityStore,compliancedocumentInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<ComplianceDocument> compliancedocumentExitAction(@Qualifier("compliancedocumentFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<ComplianceDocument> exitAction = new GenericExitAction<ComplianceDocument>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader compliancedocumentFlowReader(@Qualifier("compliancedocumentFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean ComplianceDocumentHealthChecker compliancedocumentHealthChecker(){
    	return new ComplianceDocumentHealthChecker();
    }

    @Bean STMTransitionAction<ComplianceDocument> defaultcompliancedocumentSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver compliancedocumentTransitionActionResolver(
    @Qualifier("defaultcompliancedocumentSTMTransitionAction") STMTransitionAction<ComplianceDocument> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector compliancedocumentBodyTypeSelector(
    @Qualifier("compliancedocumentActionsInfoProvider") STMActionsInfoProvider compliancedocumentInfoProvider,
    @Qualifier("compliancedocumentTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(compliancedocumentInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<ComplianceDocument> compliancedocumentBaseTransitionAction(
        @Qualifier("compliancedocumentTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "compliancedocument" + eventId for the method name. (compliancedocument is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/compliancedocument/compliancedocument-states.xml

    @Bean RenewComplianceDocumentAction
            compliancedocumentRenew(){
        return new RenewComplianceDocumentAction();
    }
    @Bean ReviewComplianceDocumentAction
            compliancedocumentReview(){
        return new ReviewComplianceDocumentAction();
    }
    @Bean ExpireComplianceDocumentAction
            compliancedocumentExpire(){
        return new ExpireComplianceDocumentAction();
    }
    @Bean RevokeComplianceDocumentAction
            compliancedocumentRevoke(){
        return new RevokeComplianceDocumentAction();
    }
    @Bean ApproveComplianceDocumentAction
            compliancedocumentApprove(){
        return new ApproveComplianceDocumentAction();
    }
    @Bean RejectComplianceDocumentAction
            compliancedocumentReject(){
        return new RejectComplianceDocumentAction();
    }


}
