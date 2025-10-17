package com.homebase.ecom.configuration.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.InterceptedBy;
import org.chenile.http.handler.ControllerSupport;
import org.chenile.query.model.SearchRequest;
import org.chenile.query.model.SearchResponse;
import org.chenile.security.model.SecurityConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ChenileController(
        value = "handmade",
        serviceName = "searchService"
)
public class QueryServiceController extends ControllerSupport {
    public QueryServiceController() {
    }

    @PostMapping({"/q1/{queryName}"})
    @InterceptedBy("securityInterceptor")
    @SecurityConfig(authorities = {"test.premium", "test.normal"})
    public ResponseEntity<GenericResponse<SearchResponse>> search(HttpServletRequest request, @PathVariable String queryName,
                                                                  @RequestBody SearchRequest<Map<String, Object>> searchRequest) {
        return this.process("search", request, new Object[]{queryName, searchRequest});
    }
}

