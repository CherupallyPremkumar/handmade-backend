package com.handmade.ecommerce.seller.account.bdd;

import com.handmade.ecommerce.seller.account.test.SpringConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import cucumber.api.java.en.Given;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("unittest")
public class CukesSteps {
    @Given("dummy")
    public void dummy() {}
}
