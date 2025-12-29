package com.handmade.ecommerce.user.configuration;

import com.handmade.ecommerce.user.service.cmds.*;
import com.handmade.ecommerce.user.service.postSaveHooks.*;
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
import com.handmade.ecommerce.user.model.User;

import com.handmade.ecommerce.user.service.healthcheck.UserHealthChecker;
import com.handmade.ecommerce.user.service.store.UserEntityStore;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.stmcmds.StmAuthoritiesBuilder;
import java.util.function.Function;
import org.chenile.core.context.ChenileExchange;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.activities.AreActivitiesComplete;

/**
 This is where you will instantiate all the required classes in Spring
*/
@Configuration
public class UserConfiguration {
	private static final String FLOW_DEFINITION_FILE = "com/handmade/ecommerce/user/user-states.xml";
	public static final String PREFIX_FOR_PROPERTIES = "User";
    public static final String PREFIX_FOR_RESOLVER = "user";

    @Bean BeanFactoryAdapter userBeanFactoryAdapter() {
		return new SpringBeanFactoryAdapter();
	}
	
	@Bean STMFlowStoreImpl userFlowStore(
            @Qualifier("userBeanFactoryAdapter") BeanFactoryAdapter userBeanFactoryAdapter
            )throws Exception{
		STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
		stmFlowStore.setBeanFactory(userBeanFactoryAdapter);
		return stmFlowStore;
	}
	
	@Bean @Autowired STM<User> userEntityStm(@Qualifier("userFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception{
		STMImpl<User> stm = new STMImpl<>();		
		stm.setStmFlowStore(stmFlowStore);
		return stm;
	}
	
	@Bean @Autowired STMActionsInfoProvider userActionsInfoProvider(@Qualifier("userFlowStore") STMFlowStoreImpl stmFlowStore) {
		STMActionsInfoProvider provider =  new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("user",provider);
        return provider;
	}
	
	@Bean EntityStore<User> userEntityStore() {
		return new UserEntityStore();
	}
	
	@Bean @Autowired StateEntityServiceImpl<User> _userStateEntityService_(
			@Qualifier("userEntityStm") STM<User> stm,
			@Qualifier("userActionsInfoProvider") STMActionsInfoProvider userInfoProvider,
			@Qualifier("userEntityStore") EntityStore<User> entityStore){
		return new StateEntityServiceImpl<>(stm, userInfoProvider, entityStore);
	}
	
	// Now we start constructing the STM Components 


    @Bean @Autowired DefaultPostSaveHook<User> userDefaultPostSaveHook(
    @Qualifier("userTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver){
    DefaultPostSaveHook<User> postSaveHook = new DefaultPostSaveHook<>(stmTransitionActionResolver);
    return postSaveHook;
    }

    @Bean @Autowired GenericEntryAction<User> userEntryAction(@Qualifier("userEntityStore") EntityStore<User> entityStore,
    @Qualifier("userActionsInfoProvider") STMActionsInfoProvider userInfoProvider,
    @Qualifier("userFlowStore") STMFlowStoreImpl stmFlowStore,
    @Qualifier("userDefaultPostSaveHook") DefaultPostSaveHook<User> postSaveHook)  {
    GenericEntryAction<User> entryAction =  new GenericEntryAction<User>(entityStore,userInfoProvider,postSaveHook);
    stmFlowStore.setEntryAction(entryAction);
    return entryAction;
    }

    @Bean @Autowired DefaultAutomaticStateComputation<User> userDefaultAutoState(
    @Qualifier("userTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
    @Qualifier("userFlowStore") STMFlowStoreImpl stmFlowStore){
    DefaultAutomaticStateComputation<User> autoState = new DefaultAutomaticStateComputation<>(stmTransitionActionResolver);
    stmFlowStore.setDefaultAutomaticStateComputation(autoState);
    return autoState;
    }

	@Bean GenericExitAction<User> userExitAction(@Qualifier("userFlowStore") STMFlowStoreImpl stmFlowStore){
        GenericExitAction<User> exitAction = new GenericExitAction<User>();
        stmFlowStore.setExitAction(exitAction);
        return exitAction;
	}

	@Bean
	XmlFlowReader userFlowReader(@Qualifier("userFlowStore") STMFlowStoreImpl flowStore) throws Exception {
		XmlFlowReader flowReader = new XmlFlowReader(flowStore);
		flowReader.setFilename(FLOW_DEFINITION_FILE);
		return flowReader;
	}
	

	@Bean UserHealthChecker userHealthChecker(){
    	return new UserHealthChecker();
    }

    @Bean STMTransitionAction<User> defaultuserSTMTransitionAction() {
        return new DefaultSTMTransitionAction<MinimalPayload>();
    }

    @Bean
    STMTransitionActionResolver userTransitionActionResolver(
    @Qualifier("defaultuserSTMTransitionAction") STMTransitionAction<User> defaultSTMTransitionAction){
        return new STMTransitionActionResolver(PREFIX_FOR_RESOLVER,defaultSTMTransitionAction,true);
    }

    @Bean @Autowired StmBodyTypeSelector userBodyTypeSelector(
    @Qualifier("userActionsInfoProvider") STMActionsInfoProvider userInfoProvider,
    @Qualifier("userTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver) {
        return new StmBodyTypeSelector(userInfoProvider,stmTransitionActionResolver);
    }


    @Bean @Autowired STMTransitionAction<User> userBaseTransitionAction(
        @Qualifier("userTransitionActionResolver") STMTransitionActionResolver stmTransitionActionResolver,
        @Qualifier("userActivityChecker") ActivityChecker activityChecker,
        @Qualifier("userFlowStore") STMFlowStoreImpl stmFlowStore){
        BaseTransitionAction<User> baseTransitionAction = new BaseTransitionAction<>(stmTransitionActionResolver);
        baseTransitionAction.activityChecker = activityChecker;
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean ActivityChecker userActivityChecker(@Qualifier("userFlowStore") STMFlowStoreImpl stmFlowStore){
        return new ActivityChecker(stmFlowStore);
    }

    @Bean
    AreActivitiesComplete activitiesCompletionCheck(@Qualifier("userActivityChecker") ActivityChecker activityChecker){
        return new AreActivitiesComplete(activityChecker);
    }

    // Create the specific transition actions here. Make sure that these actions are inheriting from
    // AbstractSTMTransitionMachine (The sample classes provide an example of this). To automatically wire
    // them into the STM use the convention of "user" + eventId + "Action" for the method name. (user is the
    // prefix passed to the TransitionActionResolver above.)
    // This will ensure that these are detected automatically by the Workflow system.
    // The payload types will be detected as well so that there is no need to introduce an <event-information/>
    // segment in src/main/resources/com/handmade/user/user-states.xml


    @Bean SuspendUserAction
            userSuspendAction(){
        return new SuspendUserAction();
    }

    @Bean UpdateProfileUserAction
            userUpdateProfileAction(){
        return new UpdateProfileUserAction();
    }

    @Bean
    PlaceOrderUserAction
            userPlaceOrderAction(){
        return new PlaceOrderUserAction();
    }

    @Bean ReactivateUserAction
            userReactivateAction(){
        return new ReactivateUserAction();
    }

    @Bean
    AddAddressUserAction
            userAddAddressAction(){
        return new AddAddressUserAction();
    }

    @Bean
    UnsuspendUserAction
            userUnsuspendAction(){
        return new UnsuspendUserAction();
    }

    @Bean ArchiveUserAction
            userArchiveAction(){
        return new ArchiveUserAction();
    }

    @Bean DowngradeToActiveUserAction
            userDowngradeToActiveAction(){
        return new DowngradeToActiveUserAction();
    }

    @Bean VerifyEmailUserAction
            userVerifyEmailAction(){
        return new VerifyEmailUserAction();
    }

    @Bean BanUserAction
            userBanAction(){
        return new BanUserAction();
    }

    @Bean
    DeactivateUserAction
            userDeactivateAction(){
        return new DeactivateUserAction();
    }

    @Bean PromoteToVipUserAction
            userPromoteToVipAction(){
        return new PromoteToVipUserAction();
    }

    @Bean
    UnbanUserAction
            userUnbanAction(){
        return new UnbanUserAction();
    }



    @Bean @Autowired Function<ChenileExchange, String[]> userEventAuthoritiesSupplier(
        @Qualifier("userActionsInfoProvider") STMActionsInfoProvider userInfoProvider)
                    throws Exception{
        StmAuthoritiesBuilder builder = new StmAuthoritiesBuilder(userInfoProvider);
        return builder.build();
    }


    @Bean
    ACTIVEUserPostSaveHook
        userACTIVEPostSaveHook(){
            return new ACTIVEUserPostSaveHook();
    }

    @Bean
    INACTIVEUserPostSaveHook
        userINACTIVEPostSaveHook(){
            return new INACTIVEUserPostSaveHook();
    }

    @Bean SUSPENDEDUserPostSaveHook
        userSUSPENDEDPostSaveHook(){
            return new SUSPENDEDUserPostSaveHook();
    }

    @Bean
    BANNEDUserPostSaveHook
        userBANNEDPostSaveHook(){
            return new BANNEDUserPostSaveHook();
    }

    @Bean REGISTEREDUserPostSaveHook
        userREGISTEREDPostSaveHook(){
            return new REGISTEREDUserPostSaveHook();
    }

    @Bean
    ARCHIVEDUserPostSaveHook
        userARCHIVEDPostSaveHook(){
            return new ARCHIVEDUserPostSaveHook();
    }

    @Bean
    VIPUserPostSaveHook
        userVIPPostSaveHook(){
            return new VIPUserPostSaveHook();
    }

}
