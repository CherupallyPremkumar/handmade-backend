package com.handmade.ecommerce.seller.account.bdd;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/rest/features",
    glue = {"classpath:com/handmade/ecommerce/seller/account/bdd",
    "classpath:org/chenile/cucumber/workflow", // Not sure if this exists but let's try
    "classpath:org/chenile/cucumber/rest"},
    plugin = {"pretty"}
)
@ActiveProfiles("unittest")
public class CukesRestTest {

}
