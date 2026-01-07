package com.handmade.ecommerce.policy.domain.aggregate;

import com.handmade.ecommerce.policy.domain.entity.PricingPolicyRule;
import org.chenile.stm.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class PricingPolicyTest {

    private PricingPolicy policy;

    @BeforeEach
    public void setup() {
        policy = new PricingPolicy();
        policy.setPolicyVersion("2024.1-US-ALL");
        policy.setCountryCode("US");
        policy.setEffectiveDate(LocalDate.now());

        State draftState = new State();
        draftState.setStateId("DRAFT");
        policy.setCurrentState(draftState);
    }

    @Test
    public void testIsActiveInDraftStateShouldBeFalse() {
        assertFalse(policy.isActive());
    }

    @Test
    public void testIsActiveInActiveStateWithTodayEffectiveDate() {
        State activeState = new State();
        activeState.setStateId("ACTIVE");
        policy.setCurrentState(activeState);
        policy.setEffectiveDate(LocalDate.now());

        assertTrue(policy.isActive());
    }

    @Test
    public void testIsActiveInActiveStateWithFutureEffectiveDateShouldBeFalse() {
        State activeState = new State();
        activeState.setStateId("ACTIVE");
        policy.setCurrentState(activeState);
        policy.setEffectiveDate(LocalDate.now().plusDays(1));

        assertFalse(policy.isActive());
    }

    @Test
    public void testAddRuleInDraftState() {
        PricingPolicyRule rule = new PricingPolicyRule();
        rule.setRuleName("MinPriceCheck");

        policy.addRule(rule);

        assertEquals(1, policy.getRules().size());
        assertEquals(policy, rule.getPolicy());
    }

    @Test
    public void testAddRuleInActiveStateShouldFail() {
        State activeState = new State();
        activeState.setStateId("ACTIVE");
        policy.setCurrentState(activeState);

        PricingPolicyRule rule = new PricingPolicyRule();
        assertThrows(IllegalStateException.class, () -> policy.addRule(rule));
    }

    @Test
    public void testApproveSetsOfficer() {
        policy.approve("OFFICER_123");

        assertEquals("OFFICER_123", policy.getApprovedBy());
        assertNotNull(policy.getApprovedAt());
    }

    @Test
    public void testDeprecateSetsDate() {
        State activeState = new State();
        activeState.setStateId("ACTIVE");
        policy.setCurrentState(activeState);

        policy.deprecate();

        assertEquals(LocalDate.now(), policy.getDeprecatedDate());
    }
}
