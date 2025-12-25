package com.handmade.ecommerce.platform.starter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    com.handmade.ecommerce.platform.service.impl.PlatformManagerImpl.class
})
public class InVMPlatformConfiguration {
}
