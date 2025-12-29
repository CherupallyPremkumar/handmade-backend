package com.handmade.ecommerce.orchestration.product.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.handmade.ecommerce.orchestration.product.dto.ProductOrchestrationRequest;
import com.handmade.ecommerce.product.dto.ProductResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.chenile.owiz.impl.ChainContext;
import org.chenile.owiz.impl.ChainContextContainer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * OWIZ workflow context for product orchestration.
 */
@Getter
@Setter
@ToString
public class ProductOrchContext extends HashMap<String, Object> implements ChainContextContainer<ProductOrchContext>, Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private ChainContext<ProductOrchContext> chainContext;

    private ProductOrchestrationRequest request;
    private ProductResponse productResponse;
    private Map<String, Object> results = new HashMap<>();

    @Override
    public void setChainContext(ChainContext<ProductOrchContext> chainContext) {
        this.chainContext = chainContext;
    }

    @Override
    public ChainContext<ProductOrchContext> getChainContext() {
        return this.chainContext;
    }

    public void addResult(String key, Object value) {
        this.results.put(key, value);
    }
}
