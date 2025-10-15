package com.homebase.ecom;

import cucumber.api.java.en.Given;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,classes = SpringTestConfig.class,
  properties = {"spring.profiles.active=unittest"})
@AutoConfigureMockMvc
@ActiveProfiles("unittest")
public class RestQueryCukesSteps {
	@Given("dummy")
	public void dummy() {
		
	}
}
