package com.handmade.ecommerce.query.service.bdd;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import io.cucumber.java.en.Given;
import io.cucumber.spring.CucumberContextConfiguration;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringTestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("unittest")
@CucumberContextConfiguration
public class RestQueryCukesSteps {
	// Create a dummy method so that Cucumber thinks of this as a steps
	// implementation.
	@Given("dummy")
	public void dummy() {

	}

}
