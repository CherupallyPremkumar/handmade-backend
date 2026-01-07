package com.handmade.ecommerce.platform.marketplace.service;

import com.handmade.ecommerce.platform.api.CurrencyExchangeService;
import com.handmade.ecommerce.platform.domain.entity.ExchangeRate;
import com.handmade.ecommerce.platform.domain.valueobject.CurrencyPair;
import com.handmade.ecommerce.platform.domain.valueobject.Money;
import com.handmade.ecommerce.platform.marketplace.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {
    
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    
    private static final int AMOUNT_SCALE = 4;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    
    @Override
    @Transactional(readOnly = true)
    public Money convert(Money amount, String toCurrency) {
        // Same currency - no conversion needed
        if (amount.getCurrency().equalsIgnoreCase(toCurrency)) {
            return amount;
        }
        
        ExchangeRate rate = getExchangeRate(amount.getCurrency(), toCurrency);
        BigDecimal convertedAmount = amount.getAmount().multiply(rate.getRate())
                                           .setScale(AMOUNT_SCALE, ROUNDING_MODE);
        
        return new Money(convertedAmount, toCurrency);
    }
    
    @Override
    @Cacheable(value = "exchangeRates", key = "#fromCurrency + '_' + #toCurrency")
    @Transactional(readOnly = true)
    public ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) {
        return getExchangeRateAt(fromCurrency, toCurrency, LocalDateTime.now());
    }
    
    @Override
    @Transactional(readOnly = true)
    public ExchangeRate getExchangeRateAt(String fromCurrency, String toCurrency, LocalDateTime asOf) {
        CurrencyPair pair = new CurrencyPair(fromCurrency, toCurrency);
        
        // Try direct rate
        Optional<ExchangeRate> directRate = exchangeRateRepository.findLatestRate(
            pair.getBaseCurrency(), pair.getTargetCurrency(), asOf
        );
        
        if (directRate.isPresent()) {
            return directRate.get();
        }
        
        // Try inverse rate
        Optional<ExchangeRate> inverseRate = exchangeRateRepository.findLatestRate(
            pair.getTargetCurrency(), pair.getBaseCurrency(), asOf
        );
        
        if (inverseRate.isPresent()) {
            // Create virtual rate from inverse
            ExchangeRate rate = new ExchangeRate();
            rate.setBaseCurrency(pair.getBaseCurrency());
            rate.setTargetCurrency(pair.getTargetCurrency());
            rate.setRate(inverseRate.get().getInverseRate());
            rate.setEffectiveFrom(inverseRate.get().getEffectiveFrom());
            rate.setEffectiveTo(inverseRate.get().getEffectiveTo());
            rate.setSource(inverseRate.get().getSource() + "_INVERSE");
            return rate;
        }
        
        throw new ExchangeRateNotFoundException(
            "No exchange rate found for " + pair.toPairString() + " at " + asOf
        );
    }
    
    @Override
    @Transactional
    public ExchangeRate updateExchangeRate(String baseCurrency, String targetCurrency, 
                                          BigDecimal rate, String source) {
        CurrencyPair pair = new CurrencyPair(baseCurrency, targetCurrency);
        
        // Expire existing rate
        exchangeRateRepository.findCurrentRate(pair.getBaseCurrency(), pair.getTargetCurrency())
            .ifPresent(existingRate -> {
                existingRate.setEffectiveTo(LocalDateTime.now());
                exchangeRateRepository.save(existingRate);
            });
        
        // Create new rate
        ExchangeRate newRate = new ExchangeRate();
        newRate.setBaseCurrency(pair.getBaseCurrency());
        newRate.setTargetCurrency(pair.getTargetCurrency());
        newRate.setRate(rate);
        newRate.setSource(source);
        newRate.setEffectiveFrom(LocalDateTime.now());
        
        return exchangeRateRepository.save(newRate);
    }
}
