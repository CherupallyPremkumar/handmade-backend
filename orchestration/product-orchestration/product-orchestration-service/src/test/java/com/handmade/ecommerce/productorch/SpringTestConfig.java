package com.handmade.ecommerce.productorch;

import com.handmade.ecommerce.product.configuration.dao.ProductRepository;
import com.handmade.ecommerce.productorch.service.ProductEntryPoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;


@Configuration
@PropertySource("classpath:com/handmade/ecommerce/productorch/TestService.properties")
@SpringBootApplication(scanBasePackages = {  "org.chenile.configuration", "com.handmade.ecommerce.productorch.configuration",
        "com.handmade.ecommerce.product.configuration"})
@ActiveProfiles("unittest")
public class SpringTestConfig extends SpringBootServletInitializer{

    @MockBean
    private ProductRepository productRepository;
}

