package com.handmade.ecommerce.pricing.configuration;

import com.handmade.ecommerce.pricing.service.PricingRuleEngine;
import com.handmade.ecommerce.pricing.service.TaxRuleEngine;
import com.handmade.ecommerce.pricing.service.rules.engine.EasyRulesPricingEngine;
import com.handmade.ecommerce.pricing.service.rules.engine.EasyRulesTaxEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Rule Engine Configuration
 * 
 * Configures which rule engine implementation to use.
 * 
 * MIGRATION GUIDE:
 * ================
 * To switch from Easy Rules to Drools:
 * 
 * 1. Create DroolsPricingEngine implementing PricingRuleEngine
 * 2. Create DroolsTaxEngine implementing TaxRuleEngine
 * 3. Change the @Primary annotation or use @Profile
 * 
 * Example:
 * 
 * @Bean
 *       @Profile("drools")
 *       public PricingRuleEngine droolsPricingEngine() {
 *       return new DroolsPricingEngine();
 *       }
 * 
 *       No other code changes needed!
 */
@Configuration
public class RuleEngineConfiguration {

    /**
     * Pricing Rule Engine Bean
     * Currently using Easy Rules - can switch to Drools by changing this bean
     */
    @Bean
    @Primary
    public PricingRuleEngine pricingRuleEngine(EasyRulesPricingEngine easyRulesEngine) {
        // TODAY: Easy Rules
        return easyRulesEngine;

        // FUTURE: Drools
        // return droolsPricingEngine;
    }

    /**
     * Tax Rule Engine Bean
     * Currently using Easy Rules - can switch to Drools by changing this bean
     */
    @Bean
    @Primary
    public TaxRuleEngine taxRuleEngine(EasyRulesTaxEngine easyRulesTaxEngine) {
        // TODAY: Easy Rules
        return easyRulesTaxEngine;

        // FUTURE: Drools
        // return droolsTaxEngine;
    }
}
