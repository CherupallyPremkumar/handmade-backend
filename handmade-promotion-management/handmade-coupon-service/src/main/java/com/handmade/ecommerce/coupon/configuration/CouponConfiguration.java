package com.handmade.ecommerce.coupon.configuration;

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
import com.handmade.ecommerce.promotion.model.Coupon;
import com.handmade.ecommerce.coupon.service.cmds.*;
import com.handmade.ecommerce.coupon.service.healthcheck.CouponHealthChecker;
import com.handmade.ecommerce.coupon.service.store.CouponEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class CouponConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/coupon/coupon-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "Coupon";
    public static final String PREFIX_FOR_RESOLVER = "coupon";

    @Bean BeanFactoryAdapter couponBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl couponFlowStore(
            @Qualifier("couponBeanFactoryAdapter") BeanFactoryAdapter couponBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(couponBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean STM<Coupon> couponEntityStm(@Qualifier("couponFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<Coupon> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean STMActionsInfoProvider couponActionsInfoProvider(@Qualifier("couponFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("coupon",provider);
        return provider;
	}
	
	@Bean EntityStore<Coupon> couponEntityStore() {
		return new CouponEntityStore();
	}
	
	@Bean StateEntityServiceImpl<Coupon> _couponStateEntityService_(
			@Qualifier("couponEntityStm") STM<Coupon> stm,
			@Qualifier("couponActionsInfoProvider") STMActionsInfoProvider couponInfoProvider,
			@Qualifier("couponEntityStore") EntityStore<Coupon> entityStore){
		return new StateEntityServiceImpl<>(stm, couponInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 
	
	@Bean GenericEntryAction<Coupon> couponEntryAction(@Qualifier("couponEntityStore") EntityStore<Coupon> entityStore,
			@Qualifier("couponActionsInfoProvider") STMActionsInfoProvider couponInfoProvider,
            @Qualifier("couponFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericEntryAction<Coupon> entryAction =  new GenericEntryAction<Coupon>(entityStore,couponInfoProvider);
        stmFlowStore.setEntryAction(entryAction);
        return entryAction;
	}
	
	@Bean GenericExitAction<Coupon> couponExitAction(@Qualifier("couponFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<Coupon> exitAction = new GenericExitAction<Coupon>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader couponFlowReader(@Qualifier("couponFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean CouponHealthChecker couponHealthChecker(){
    	return new CouponHealthChecker();
    }

    @Bean STMTransitionAction<Coupon> defaultcouponSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver couponTransitionActionResolver(
    @Qualifier("defaultcouponSTMTransitionAction") STMTransitionAction<Coupon> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction);
    }

    @Bean StmBodyTypeSelector couponBodyTypeSelector(
    @Qualifier("couponActionsInfoProvider") STMActionsInfoProvider couponInfoProvider,
    @Qualifier("couponTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(couponInfoProvider,stmTransitionActionResolver);
    }

    @Bean STMTransitionAction<Coupon> couponBaseTransitionAction(
        @Qualifier("couponTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
            return new BaseTransitionAction<>(stmTransitionActionResolver);
    }


    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "coupon" + eventId for the method name. (coupon is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/coupon/coupon-states.xml

    @Bean ResumeCouponAction
            resumeCouponAction(){
        return new ResumeCouponAction();
    }

    @Bean ExpireCouponAction
            expireCouponAction(){
        return new ExpireCouponAction();
    }
    @Bean PauseCouponAction
            pauseCouponAction(){
        return new PauseCouponAction();
    }
    @Bean DeactivateCouponAction
            deactivateCouponAction(){
        return new DeactivateCouponAction();
    }
    @Bean SubmitCouponAction
            submitCouponAction(){
        return new SubmitCouponAction();
    }
    @Bean ApproveCouponAction
            approveCouponAction(){
        return new ApproveCouponAction();
    }
    @Bean RejectCouponAction
            rejectCouponAction(){
        return new RejectCouponAction();
    }
    @Bean ActivateCouponAction
            activateCouponAction(){
        return new ActivateCouponAction();
    }


}
