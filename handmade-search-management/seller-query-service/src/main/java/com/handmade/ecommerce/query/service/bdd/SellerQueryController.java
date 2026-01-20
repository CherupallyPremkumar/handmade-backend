package com.handmade.ecommerce.query.service.bdd;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.chenile.query.model.SearchRequest;
import org.chenile.query.model.SearchResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Seller Query Controller - handles all seller dashboard queries
 * including seller search, dashboard metrics, catalog, orders, etc.
 */
@RestController
@ChenileController(
        value = "sellerMybatisQuery",
        serviceName = "sellerSearchService"
)
public class SellerQueryController extends ControllerSupport {

    /**
     * Main query endpoint for seller queries
     * Maps to: /seller/q/{queryName}
     */
    @PostMapping({"/seller/q/{queryName}"})
    public ResponseEntity<GenericResponse<SearchResponse>> search(
            HttpServletRequest request,
            @PathVariable String queryName,
            @RequestBody SearchRequest<Map<String, Object>> searchRequest) {
        return this.process("search", request, new Object[]{queryName, searchRequest});
    }
}
