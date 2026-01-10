package com.handmade.ecommerce.seller.onboarding.bdd;

import com.handmade.ecommerce.seller.onboarding.SpringTestConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import cucumber.api.java.en.Given;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringTestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("unittest")
public class CukesSteps {
    @Given("dummy_onboarding")
    public void dummy() {}
}
