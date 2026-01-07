package com.handmade.ecommerce.policy.domain.aggregate;

import com.handmade.ecommerce.policy.domain.entity.ListingPolicyRule;
import org.chenile.stm.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ListingPolicyTest {

    private ListingPolicy policy;

    @BeforeEach
    public void setup() {
        policy = new ListingPolicy();
        policy.setPolicyVersion("V1");
        policy.setCountryCode("USA");

        State draftState = new State();
        draftState.setStateId("DRAFT");
        policy.setCurrentState(draftState);
    }

    @Test
    public void testAddRuleInDraftState() {
        ListingPolicyRule rule = new ListingPolicyRule();
        rule.setRuleName("ImageCountCheck");

        policy.addRule(rule);

        assertEquals(1, policy.getRules().size());
        assertEquals(policy, rule.getPolicy());
    }

    @Test
    public void testAddRuleInActiveStateShouldFail() {
        State activeState = new State();
        activeState.setStateId("ACTIVE");
        policy.setCurrentState(activeState);

        ListingPolicyRule rule = new ListingPolicyRule();
        assertThrows(IllegalStateException.class, () -> policy.addRule(rule));
    }

    @Test
    public void testApproveSetsOfficer() {
        policy.approve("LISTING_OFFICER_1");

        assertEquals(LocalDate.now(), policy.getEffectiveDate());
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
