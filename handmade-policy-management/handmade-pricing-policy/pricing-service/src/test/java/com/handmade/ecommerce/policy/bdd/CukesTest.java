package com.handmade.ecommerce.policy.bdd;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.test.context.ActiveProfiles;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ActiveProfiles("unittest")
public class CukesTest {
}
