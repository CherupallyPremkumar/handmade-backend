package com.handmade.ecommerce.tax.bdd;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.test.context.ActiveProfiles;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.handmade.ecommerce.tax.bdd,org.chenile.cucumber.rest,org.chenile.cucumber.security.rest")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ActiveProfiles("unittest")

public class CukesRestTest {

}
