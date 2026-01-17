package com.handmade.ecommerce.shippinglabel.bdd;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)

@CucumberOptions(features = "src/test/resources/features",
    glue = {"classpath:com/handmade/ecommerce/shippinglabel/bdd",
    "classpath:org/chenile/cucumber/rest"},

        plugin = {"pretty"}
        )
@ActiveProfiles("unittest")
public class CukesRestTest {

}
