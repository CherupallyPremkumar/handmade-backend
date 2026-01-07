package com.handmade.ecommerce.finance.configuration;

import com.handmade.ecommerce.finance.api.PayoutManager;
import com.handmade.ecommerce.finance.api.SettlementManager;
import com.handmade.ecommerce.finance.domain.aggregate.PayoutInstruction;
import com.handmade.ecommerce.finance.domain.aggregate.SettlementAccount;
import com.handmade.ecommerce.finance.domain.entity.SettlementTransaction;
import com.handmade.ecommerce.finance.service.impl.PayoutManagerImpl;
import com.handmade.ecommerce.finance.service.impl.SettlementManagerImpl;
import com.handmade.ecommerce.finance.service.store.PayoutInstructionEntityStore;
import com.handmade.ecommerce.finance.service.store.SettlementAccountEntityStore;
import com.handmade.ecommerce.finance.service.cmds.*;
import org.chenile.stm.*;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.impl.*;
import org.chenile.stm.spring.SpringBeanFactoryAdapter;
import org.chenile.workflow.api.WorkflowRegistry;
import org.chenile.workflow.service.activities.ActivityChecker;
import org.chenile.workflow.service.stmcmds.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FinanceManagementConfiguration {
    private static final String PAYOUT_FLOW_FILE = "com/handmade/ecommerce/finance/payout-states.xml";
    private static final String SETTLEMENT_FLOW_FILE = "com/handmade/ecommerce/finance/settlement-account-states.xml";

    @Bean BeanFactoryAdapter financeBeanFactoryAdapter() {
        return new SpringBeanFactoryAdapter();
    }

    // ========== PAYOUT STM CONFIGURATION ==========

    @Bean STMFlowStoreImpl payoutFlowStore(
            @Qualifier("financeBeanFactoryAdapter") BeanFactoryAdapter beanFactoryAdapter
            ) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }

    @Bean @Autowired STM<PayoutInstruction> payoutEntityStm(
            @Qualifier("payoutFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<PayoutInstruction> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean @Autowired STMActionsInfoProvider payoutActionsInfoProvider(
            @Qualifier("payoutFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("payoutInstruction", provider);
        return provider;
    }

    @Bean @Autowired PayoutManager payoutManager(
            @Qualifier("payoutEntityStm") STM<PayoutInstruction> stm,
            @Qualifier("payoutActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("payoutInstructionEntityStore") PayoutInstructionEntityStore entityStore) {
        return new PayoutManagerImpl(stm, infoProvider, entityStore);
    }

    @Bean XmlFlowReader payoutFlowReader(@Qualifier("payoutFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(PAYOUT_FLOW_FILE);
        return flowReader;
    }

    // ========== SETTLEMENT STM CONFIGURATION ==========

    @Bean STMFlowStoreImpl settlementFlowStore(
            @Qualifier("financeBeanFactoryAdapter") BeanFactoryAdapter beanFactoryAdapter
            ) throws Exception {
        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }

    @Bean @Autowired STM<SettlementAccount> settlementEntityStm(
            @Qualifier("settlementFlowStore") STMFlowStoreImpl stmFlowStore) throws Exception {
        STMImpl<SettlementAccount> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }

    @Bean @Autowired STMActionsInfoProvider settlementActionsInfoProvider(
            @Qualifier("settlementFlowStore") STMFlowStoreImpl stmFlowStore) {
        STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);
        WorkflowRegistry.addSTMActionsInfoProvider("settlementAccount", provider);
        return provider;
    }

    @Bean @Autowired SettlementManager settlementManager(
            @Qualifier("settlementEntityStm") STM<SettlementAccount> stm,
            @Qualifier("settlementActionsInfoProvider") STMActionsInfoProvider infoProvider,
            @Qualifier("settlementAccountEntityStore") SettlementAccountEntityStore entityStore) {
        return new SettlementManagerImpl(stm, infoProvider, entityStore);
    }

    @Bean XmlFlowReader settlementFlowReader(@Qualifier("settlementFlowStore") STMFlowStoreImpl flowStore) throws Exception {
        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(SETTLEMENT_FLOW_FILE);
        return flowReader;
    }

    // ========== PAYOUT ACTIONS ==========

    @Bean InitiatePayoutAction payoutInitiateAction() {
        return new InitiatePayoutAction();
    }

    @Bean CompletePayoutAction payoutCompleteAction() {
        return new CompletePayoutAction();
    }

    @Bean PayoutFailAction payoutFailAction() {
        return new PayoutFailAction();
    }

    @Bean SettleOrderAction settleOrderAction() {
        return new SettleOrderAction();
    }

    @Bean @Autowired STMTransitionAction<PayoutInstruction> payoutBaseTransitionAction(
            @Qualifier("payoutTransitionActionResolver") STMTransitionActionResolver resolver,
            @Qualifier("payoutFlowStore") STMFlowStoreImpl stmFlowStore) {
        BaseTransitionAction<PayoutInstruction> baseTransitionAction = new BaseTransitionAction<>(resolver);
        stmFlowStore.setDefaultTransitionAction(baseTransitionAction);
        return baseTransitionAction;
    }

    @Bean STMTransitionAction<PayoutInstruction> defaultPayoutSTMTransitionAction() {
        return (p, pay, start, event, end, stm, trans) -> { };
    }

    @Bean STMTransitionActionResolver payoutTransitionActionResolver(
            @Qualifier("defaultPayoutSTMTransitionAction") STMTransitionAction<PayoutInstruction> defaultAction) {
        return new STMTransitionActionResolver("payout", defaultAction, true);
    }
}
