package com.handmade.ecommerce.query.service.bdd;

import jakarta.annotation.PostConstruct;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.workflow.api.WorkflowRegistry;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

@Configuration
@SpringBootApplication(scanBasePackages = {
    "org.chenile.configuration",
    "com.handmade.ecommerce.query",
    "com.handmade.ecommerce.**.configuration"
})
@ActiveProfiles("unittest")
@EnableJpaRepositories(basePackages = { "com.handmade.ecommerce.**.configuration.dao" })
@EntityScan(basePackages = { "com.handmade.ecommerce.**.model" })
public class SpringTestConfig extends SpringBootServletInitializer {

}
