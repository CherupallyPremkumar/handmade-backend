package com.homebase.ecom;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/rest/features",
        glue = {"classpath:org/chenile/cucumber/rest",
                "classpath:com/homebase/ecom",
                "classpath:org/chenile/cucumber/security/rest"},
        plugin = {"pretty"}
)
@ActiveProfiles("unittest")
public class CukesRestTest {

}
