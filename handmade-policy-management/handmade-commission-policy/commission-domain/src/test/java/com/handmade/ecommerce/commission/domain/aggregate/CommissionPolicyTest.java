package com.handmade.ecommerce.commission.domain.aggregate;

import com.handmade.ecommerce.commission.domain.entity.CommissionRule;
import com.handmade.ecommerce.commission.domain.valueobject.CommissionFeeType;
import org.chenile.stm.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class CommissionPolicyTest {

    private CommissionPolicy policy;

    @BeforeEach
    public void setup() {
        policy = new CommissionPolicy();
        policy.setPolicyVersion("V1");
        policy.setCountryCode("US");
        policy.setEffectiveDate(LocalDate.now());
        // Set state to DRAFT for setup
        State draftState = new State();
        draftState.setStateId("DRAFT");
        policy.setCurrentState(draftState);
    }

    @Test
    public void testAddRuleInDraftState() {
        CommissionRule rule = new CommissionRule();
        rule.setRuleName("ReferralFee");
        rule.setFeeType(CommissionFeeType.REFERRAL_FEE);
        rule.setThresholdValue(new BigDecimal("0.10"));
        rule.setRequired(true);

        policy.addRule(rule);

        assertEquals(1, policy.getRules().size());
        assertEquals(policy, rule.getPolicy());
    }

    @Test
    public void testAddRuleInActiveStateShouldFail() {
        State activeState = new State();
        activeState.setStateId("ACTIVE");
        policy.setCurrentState(activeState);

        CommissionRule rule = new CommissionRule();
        assertThrows(IllegalStateException.class, () -> policy.addRule(rule));
    }

    @Test
    public void testCalculateFees() {
        CommissionRule rule1 = new CommissionRule();
        rule1.setRuleName("ReferralFee");
        rule1.setFeeType(CommissionFeeType.REFERRAL_FEE);
        rule1.setThresholdValue(new BigDecimal("0.10"));
        rule1.setRequired(true);
        policy.addRule(rule1);

        CommissionRule rule2 = new CommissionRule();
        rule2.setRuleName("ProcessingFee");
        rule2.setFeeType(CommissionFeeType.REFERRAL_FEE);
        rule2.setThresholdValue(new BigDecimal("0.02"));
        rule2.setRequired(true);
        policy.addRule(rule2);

        BigDecimal totalFees = policy.calculateTotalFees(new BigDecimal("100.00"), CommissionFeeType.REFERRAL_FEE);

        // (100 * 0.10) + (100 * 0.02) = 10 + 2 = 12.00
        assertEquals(new BigDecimal("12.00"), totalFees);
    }

    @Test
    public void testCalculateFeesWithOptionalRule() {
        CommissionRule rule1 = new CommissionRule();
        rule1.setRuleName("RequiredFee");
        rule1.setFeeType(CommissionFeeType.REFERRAL_FEE);
        rule1.setThresholdValue(new BigDecimal("0.10"));
        rule1.setRequired(true);
        policy.addRule(rule1);

        CommissionRule rule2 = new CommissionRule();
        rule2.setRuleName("OptionalFee");
        rule2.setFeeType(CommissionFeeType.REFERRAL_FEE);
        rule2.setThresholdValue(new BigDecimal("0.05"));
        rule2.setRequired(false);
        policy.addRule(rule2);

        BigDecimal totalFees = policy.calculateTotalFees(new BigDecimal("100.00"), CommissionFeeType.REFERRAL_FEE);

        assertEquals(new BigDecimal("10.00"), totalFees);
    }
}
