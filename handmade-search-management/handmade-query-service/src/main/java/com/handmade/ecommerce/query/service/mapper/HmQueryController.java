package com.handmade.ecommerce.query.service.mapper;

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

@RestController
@ChenileController(
        value = "chenileMybatisQuery",
        serviceName = "searchService"
)
public class HmQueryController extends ControllerSupport {
    public HmQueryController() {
    }

    @PostMapping({"/q1/{queryName}"})
    public ResponseEntity<GenericResponse<SearchResponse>> search(HttpServletRequest request, @PathVariable String queryName, @RequestBody SearchRequest<Map<String, Object>> searchRequest) {
        return this.process("search", request, new Object[]{queryName, searchRequest});
    }
}

