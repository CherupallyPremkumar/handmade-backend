package com.handmade.ecommerce.vendorpo.bdd;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;


@RunWith(Cucumber.class)

@CucumberOptions(features = "src/test/resources/features",
    glue = {"classpath:com/handmade/ecommerce/vendorpo/bdd",
    "classpath:org/chenile/cucumber/rest"},

        plugin = {"pretty"}
        )
@ActiveProfiles("unittest")
public class CukesRestTest {

}
