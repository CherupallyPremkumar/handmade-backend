package com.handmade.ecommerce.deliveryattempt.bdd;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)

@CucumberOptions(features = "src/test/resources/features",
    glue = {"classpath:com/handmade/ecommerce/deliveryattempt/bdd",
    "classpath:org/chenile/cucumber/rest"},

        plugin = {"pretty"}
        )
@ActiveProfiles("unittest")
public class CukesRestTest {

}
