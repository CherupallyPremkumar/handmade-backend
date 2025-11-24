package com.handmade.ecommerce.product.bdd;

import com.handmade.ecommerce.event.EventBus;
import com.handmade.ecommerce.product.dto.OnHideProductPayload;
import cucumber.api.junit.Cucumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import com.handmade.ecommerce.product.SpringTestConfig;

import cucumber.api.java.en.Given;
import org.springframework.test.context.junit4.SpringRunner;

/**
* This "steps" file's purpose is to hook up the SpringConfig to the test case.
* It does not contain any methods currently but can be used for writing your own custom BDD steps
* if required. In most cases people don't need additional steps since cucumber-utils provides for
* most of the steps. <br/>
* This class requires a dummy method to keep Cucumber from erring out. (Cucumber needs at least
* one step in a steps file)<br/>
*/

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,classes = SpringTestConfig.class)
@ActiveProfiles("unittest")
public class CukesSteps {

	@Autowired
	EventBus eventBus;

	@Test
	public void TestEvent() {
		OnHideProductPayload payload = new OnHideProductPayload();
		eventBus.publish(payload);
	}
}
