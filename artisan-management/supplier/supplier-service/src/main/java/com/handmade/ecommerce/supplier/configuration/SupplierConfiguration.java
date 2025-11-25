package com.handmade.ecommerce.supplier.configuration;

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
import com.handmade.ecommerce.supplier.model.Supplier;
import com.handmade.ecommerce.supplier.service.cmds.AssignSupplierAction;
import com.handmade.ecommerce.supplier.service.cmds.DefaultSTMTransitionAction;
import com.handmade.ecommerce.supplier.service.cmds.CloseSupplierAction;
import com.handmade.ecommerce.supplier.service.cmds.ResolveSupplierAction;
import com.handmade.ecommerce.supplier.service.healthcheck.SupplierHealthChecker;
import com.handmade.ecommerce.supplier.service.store.SupplierEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.stmcmds.StmAuthoritiesBuilder;
import java.util.function.Function;
import org.chenile.core.context.ChenileExchange;
import org.chenile.stm.State;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.activities.AreActivitiesComplete;


/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class SupplierConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/supplier/supplier-states.xml";
	
	@Bean BeanFactoryAdapter supplierBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl supplierFlowStore(@Qualifier("supplierBeanFactoryAdapter") BeanFactoryAdapter supplierBeanFactoryAdapter) throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(supplierBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<Supplier> supplierEntityStm(@Qualifier("supplierFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Supplier> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider supplierActionsInfoProvider(@Qualifier("supplierFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("supplier",provider);
        return provider;
	}
	
	@Bean EntityStore<Supplier> supplierEntityStore() {
		return new SupplierEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<Supplier> _supplierStateEntityService_(
			@Qualifier("supplierEntityStm") STM<Supplier> stm,
			@Qualifier("supplierActionsInfoProvider") STMActionsInfoProvider supplierInfoProvider,
			@Qualifier("supplierEntityStore") EntityStore<Supplier> entityStore){
		return new StateEntityServiceImpl<>(stm, supplierInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean @Autowired GenericEntryAction<Supplier> supplierEntryAction(@Qualifier("supplierEntityStore") EntityStore<Supplier> entityStore,
			@Qualifier("supplierActionsInfoProvider") STMActionsInfoProvider supplierInfoProvider){
		return new GenericEntryAction<Supplier>(entityStore,supplierInfoProvider);
	}
	
	@Bean GenericExitAction<Supplier> supplierExitAction(){
		return new GenericExitAction<Supplier>();
	}

	@Bean
	XmlFlowReader supplierFlowReader(@Qualifier("supplierFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean SupplierHealthChecker supplierHealthChecker(){
    	return new SupplierHealthChecker();
    }

    @Bean STMTransitionAction<Supplier> defaultsupplierSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver supplierTransitionActionResolver(
    @Qualifier("defaultsupplierSTMTransitionAction") STMTransitionAction<Supplier> defaultSTMTransitionAction){
        return new STMTransitionActionResolver("supplier",defaultSTMTransitionAction);
    }

    @Bean @Autowired StmBodyTypeSelector supplierBodyTypeSelector(
    @Qualifier("supplierActionsInfoProvider") STMActionsInfoProvider supplierInfoProvider,
    @Qualifier("supplierTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(supplierInfoProvider,stmTransitionActionResolver);
    }


    @Bean @Autowired STMTransitionAction<Supplier> supplierBaseTransitionAction(
    @Qualifier("supplierTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
    @Qualifier("supplierActivityChecker") ActivityChecker activityChecker){
        BaseTransitionAction<Supplier> baseTransitionAction = new BaseTransitionAction<>(stmTransitionActionResolver);
        baseTransitionAction.activityChecker = activityChecker;
        return baseTransitionAction;
    }

    @Bean ActivityChecker supplierActivityChecker(@Qualifier("supplierFlowStore") STMFlowStoreImpl stmFlowStore){
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete activitiesCompletionCheck(@Qualifier("supplierActivityChecker") ActivityChecker activityChecker){
        return new AreActivitiesComplete(activityChecker);
    }

    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "supplier" + eventId for the method name. (supplier is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/supplier/supplier-states.xml

    @Bean ResolveSupplierAction supplierResolve() {
        return new ResolveSupplierAction();
    }

    @Bean CloseSupplierAction supplierClose() {
        return new CloseSupplierAction();
    }

    @Bean AssignSupplierAction supplierAssign() {
        return new AssignSupplierAction();
    }

    @Bean ConfigProviderImpl supplierConfigProvider() {
        return new ConfigProviderImpl();
    }

    @Bean ConfigBasedEnablementStrategy supplierConfigBasedEnablementStrategy(
        @Qualifier("supplierConfigProvider") ConfigProvider configProvider) {
            return new ConfigBasedEnablementStrategy(configProvider,"Supplier");
    }

    @Bean @Autowired Function<ChenileExchange, String[]> supplierEventAuthoritiesSupplier(
        @Qualifier("supplierActionsInfoProvider") STMActionsInfoProvider supplierInfoProvider)
                    throws Exception{
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(supplierInfoProvider);
        return builder.build();
    }
}
