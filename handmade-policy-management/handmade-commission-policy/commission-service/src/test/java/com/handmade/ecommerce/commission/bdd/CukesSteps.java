package com.handmade.ecommerce.commission.bdd;

import org.chenile.cucumber.CukesContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.handmade.ecommerce.commission.SpringTestConfig;

import cucumber.api.java.Before;

@SpringBootTest(classes = SpringTestConfig.class)
@ActiveProfiles("unittest")
public class CukesSteps {

    private CukesContext context = CukesContext.CONTEXT;

    @Before
    public void before() {
        context.reset();
    }
}
