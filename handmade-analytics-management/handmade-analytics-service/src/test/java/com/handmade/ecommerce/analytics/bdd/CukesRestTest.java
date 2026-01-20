package com.handmade.ecommerce.analytics.bdd;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(
        glue = {"classpath:com/handmade/ecommerce/analytics/bdd",
                "classpath:org/chenile/cucumber/rest"},
        features = "src/test/resources/features",
        plugin = {"pretty"}
)
public class CukesRestTest {

}
