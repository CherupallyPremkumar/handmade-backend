package com.handmade.ecommerce.location.bdd;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@CucumberOptions(features = "src/test/resources/features",
        glue = {"classpath:com/handmade/ecommerce/location/bdd", "classpath:org/chenile/cucumber/rest",
                "classpath:org/chenile/cucumber/security/rest"},
        plugin = {"pretty"}
        )
@ActiveProfiles("unittest")

public class CukesRestTest {

}
