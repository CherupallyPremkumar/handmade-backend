package com.handmade.ecommerce.productorch.bdd;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import static org.junit.Assert.assertEquals;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        glue = {"classpath:com/handmade/ecommerce/productorch/bdd", "classpath:org/chenile/cucumber/rest",
                "classpath:org/chenile/cucumber/security/rest"},
        plugin = {"pretty"}
        )
@ActiveProfiles("unittest")
public class CukesRestTest {



}
