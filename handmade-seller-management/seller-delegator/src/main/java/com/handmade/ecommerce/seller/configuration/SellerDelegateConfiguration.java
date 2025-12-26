package com.handmade.ecommerce.seller.configuration;

// TODO: Uncomment when SellerAccountService is created
// import com.handmade.ecommerce.seller.api.SellerAccountService;
import com.handmade.ecommerce.seller.delegate.SellerManagerClient;
import com.handmade.ecommerce.seller.delegate.SellerManagerClientImpl;
import org.chenile.proxy.builder.ProxyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Seller Delegate Configuration
 * Configures Chenile proxy for remote seller service calls
 * Follows the same pattern as PlatformDelegateConfiguration
 */
@Configuration
public class SellerDelegateConfiguration {
    
    @Autowired
    ProxyBuilder proxyBuilder;
    
    @Value("${seller.manager.base-url:http://localhost:8080}")
    String baseUrl;

    /**
     * Creates the SellerManagerClient implementation
     */
    @Bean
    public SellerManagerClient sellerManagerClient() {
        return new SellerManagerClientImpl();
    }

    /**
     * Creates Chenile proxy for SellerAccountService
     * This proxy will handle remote service calls via HTTP
     * 
     * TODO: Uncomment when SellerAccountService interface is created
     */
    // @Bean(name = "sellerAccountServiceProxy")
    // public SellerAccountService sellerAccountServiceProxy() {
    //     return proxyBuilder.buildProxy(
    //         SellerAccountService.class,
    //         "sellerAccountService",
    //         null,  
    //         baseUrl
    //     );
    // }

    /**
     * Creates Chenile proxy for SellerService (store operations)
     * 
     * TODO: Create when SellerService interface is implemented
     */
    // @Bean(name = "sellerServiceProxy")
    // public SellerService sellerServiceProxy() {
    //     return proxyBuilder.buildProxy(
    //         SellerService.class,
    //         "sellerService",
    //         null,
    //         baseUrl
    //     );
    // }
}
