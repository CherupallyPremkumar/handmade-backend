package com.handmade.ecommerce.paymentorder.configuration;

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
import com.handmade.ecommerce.paymentorder.model.Paymentorder;
import com.handmade.ecommerce.paymentorder.service.cmds.*;
import com.handmade.ecommerce.paymentorder.service.healthcheck.PaymentorderHealthChecker;
import com.handmade.ecommerce.paymentorder.service.store.PaymentorderEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.stmcmds.StmAuthoritiesBuilder;
import java.util.function.Function;
import org.chenile.core.context.ChenileExchange;
import org.chenile.stm.State;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.activities.AreActivitiesComplete;
import com.handmade.ecommerce.paymentorder.service.postSaveHooks.*;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class PaymentorderConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/paymentorder/paymentorder-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Paymentorder";
    public static final String PREFIX_FOR_RESOLVER = "paymentorder";

    @Bean BeanFactoryAdapter paymentorderBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl paymentorderFlowStore(
            @Qualifier("paymentorderBeanFactoryAdapter") BeanFactoryAdapter paymentorderBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(paymentorderBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Paymentorder> paymentorderEntityStm(@Qualifier("paymentorderFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Paymentorder> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider paymentorderActionsInfoProvider(@Qualifier("paymentorderFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("paymentorder",provider);
        return provider;
	}
	
	@Bean EntityStore<Paymentorder> paymentorderEntityStore() {
		return new PaymentorderEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Paymentorder> _paymentorderStateEntityService_(
			@Qualifier("paymentorderEntityStm") STM<Paymentorder> stm,
			@Qualifier("paymentorderActionsInfoProvider") STMActionsInfoProvider paymentorderInfoProvider,
			@Qualifier("paymentorderEntityStore") EntityStore<Paymentorder> entityStore){
		return new StateEntityServiceImpl<>(stm, paymentorderInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 


    @Bean @Autowired DefaultPostSaveHook<Paymentorder> paymentorderDefaultPostSaveHook(
    @Qualifier("paymentorderTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
    DefaultPostSaveHook<Paymentorder> postSaveHook = new DefaultPostSaveHook<>(stmTransitionActionResolver);
    return postSaveHook;
    }

    @Bean @Autowired GenericEntryAction<Paymentorder> paymentorderEntryAction(@Qualifier("paymentorderEntityStore") EntityStore<Paymentorder> entityStore,
    @Qualifier("paymentorderActionsInfoProvider") STMActionsInfoProvider paymentorderInfoProvider,
    @Qualifier("paymentorderFlowStore") STMFlowStoreImpl stmFlowStore,
    @Qualifier("paymentorderDefaultPostSaveHook") DefaultPostSaveHook<Paymentorder> postSaveHook)  {
    GenericEntryAction<Paymentorder> entryAction =  new GenericEntryAction<Paymentorder>(entityStore,paymentorderInfoProvider,postSaveHook);
    stmFlowStore.setEntryAction(entryAction);
    return entryAction;
    }

    @Bean @Autowired DefaultAutomaticStateComputation<Paymentorder> paymentorderDefaultAutoState(
    @Qualifier("paymentorderTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
    @Qualifier("paymentorderFlowStore") STMFlowStoreImpl stmFlowStore){
    DefaultAutomaticStateComputation<Paymentorder> autoState = new DefaultAutomaticStateComputation<>(stmTransitionActionResolver);
    stmFlowStore.setDefaultAutomaticStateComputation(autoState);
    return autoState;
    }

	@Bean GenericExitAction<Paymentorder> paymentorderExitAction(@Qualifier("paymentorderFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Paymentorder> exitAction = new GenericExitAction<Paymentorder>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader paymentorderFlowReader(@Qualifier("paymentorderFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean PaymentorderHealthChecker paymentorderHealthChecker(){
    	return new PaymentorderHealthChecker();
    }

    @Bean STMTransitionAction<Paymentorder> defaultpaymentorderSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver paymentorderTransitionActionResolver(
    @Qualifier("defaultpaymentorderSTMTransitionAction") STMTransitionAction<Paymentorder> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction,true);
    }

    @Bean @Autowired StmBodyTypeSelector paymentorderBodyTypeSelector(
    @Qualifier("paymentorderActionsInfoProvider") STMActionsInfoProvider paymentorderInfoProvider,
    @Qualifier("paymentorderTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(paymentorderInfoProvider,stmTransitionActionResolver);
    }


    @Bean @Autowired STMTransitionAction<Paymentorder> paymentorderBaseTransitionAction(
        @Qualifier("paymentorderTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
        @Qualifier("paymentorderActivityChecker") ActivityChecker activityChecker,
        @Qualifier("paymentorderFlowStore") STMFlowStoreImpl stmFlowStore){
        BaseTransitionAction<Paymentorder> baseTransitionAction = new BaseTransitionAction<>(stmTransitionActionResolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker paymentorderActivityChecker(@Qualifier("paymentorderFlowStore") STMFlowStoreImpl stmFlowStore){
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete activitiesCompletionCheck(@Qualifier("paymentorderActivityChecker") ActivityChecker activityChecker){
        return new AreActivitiesComplete(activityChecker);
    }

    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "paymentorder" + eventId + "Action" for the method name. (paymentorder is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/paymentorder/paymentorder-states.xml


    @Bean NoPaymentorderAction
            paymentorderNoAction(){
        return new NoPaymentorderAction();
    }

    @Bean CaptureObservationsPaymentorderAction
            paymentorderCaptureObservationsAction(){
        return new CaptureObservationsPaymentorderAction();
    }

    @Bean DoRetrospectivePaymentorderAction
            paymentorderDoRetrospectiveAction(){
        return new DoRetrospectivePaymentorderAction();
    }

    @Bean ResolvePaymentorderAction
            paymentorderResolveAction(){
        return new ResolvePaymentorderAction();
    }

    @Bean YesPaymentorderAction
            paymentorderYesAction(){
        return new YesPaymentorderAction();
    }

    @Bean DoPerfTestingPaymentorderAction
            paymentorderDoPerfTestingAction(){
        return new DoPerfTestingPaymentorderAction();
    }

    @Bean ClosePaymentorderAction
            paymentorderCloseAction(){
        return new ClosePaymentorderAction();
    }

    @Bean AssignPaymentorderAction
            paymentorderAssignAction(){
        return new AssignPaymentorderAction();
    }


    @Bean ConfigProviderImpl paymentorderConfigProvider() {
        return new ConfigProviderImpl();
    }

    @Bean ConfigBasedEnablementStrategy paymentorderConfigBasedEnablementStrategy(
        @Qualifier("paymentorderConfigProvider") ConfigProvider configProvider,
        @Qualifier("paymentorderFlowStore") STMFlowStoreImpl stmFlowStore) {
        ConfigBasedEnablementStrategy enablementStrategy = new ConfigBasedEnablementStrategy(configProvider,PREFIX_FOR_PROPERTIES);
        stmFlowStore.setEnablementStrategy(enablementStrategy);
        return enablementStrategy;
    }


    @Bean @Autowired Function<ChenileExchange, String[]> paymentorderEventAuthoritiesSupplier(
        @Qualifier("paymentorderActionsInfoProvider") STMActionsInfoProvider paymentorderInfoProvider)
                    throws Exception{
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(paymentorderInfoProvider);
        return builder.build();
    }


    @Bean CLOSEDPaymentorderPostSaveHook
        paymentorderCLOSEDPostSaveHook(){
            return new CLOSEDPaymentorderPostSaveHook();
    }

    @Bean RESOLVEDPaymentorderPostSaveHook
        paymentorderRESOLVEDPostSaveHook(){
            return new RESOLVEDPaymentorderPostSaveHook();
    }

    @Bean ASSIGNEDPaymentorderPostSaveHook
        paymentorderASSIGNEDPostSaveHook(){
            return new ASSIGNEDPaymentorderPostSaveHook();
    }

    @Bean ARCHIVEDPaymentorderPostSaveHook
        paymentorderARCHIVEDPostSaveHook(){
            return new ARCHIVEDPaymentorderPostSaveHook();
    }

    @Bean OPENEDPaymentorderPostSaveHook
        paymentorderOPENEDPostSaveHook(){
            return new OPENEDPaymentorderPostSaveHook();
    }

}
