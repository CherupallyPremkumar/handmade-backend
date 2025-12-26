package com.handmade.ecommerce.ledger.bdd;

import com.handmade.ecommerce.ledger.service.LedgerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import com.handmade.ecommerce.ledger.SpringTestConfig;

import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;


/**
    * This "steps" file's purpose is to hook up the SpringConfig to the test case.
    * It does not contain any methods currently but can be used for writing your own custom BDD steps
    * if required. In most cases people don't need additional steps since cucumber-utils provides for
    * most of the steps. <br/>
    * This class requires a dummy method to keep Cucumber from erring out. (Cucumber needs at least
    * one step in a steps file)<br/>
*/
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,classes = SpringTestConfig.class)

@ActiveProfiles("unittest")
@RunWith(SpringRunner.class)
public class CukesSteps {

	@Autowired
	LedgerService _ledgerService_;

	@Test
	public void testCreateDoubleEntry() {
		_ledgerService_.createDoubleEntry("order1", "buyer1", "seller1",
				new BigDecimal("100"), "USD");

		boolean isValid = _ledgerService_.verifyDoubleEntry("txn1");
		assertTrue(isValid);
	}
}
