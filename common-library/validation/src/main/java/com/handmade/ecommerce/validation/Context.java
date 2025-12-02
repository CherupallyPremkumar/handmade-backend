package com.handmade.ecommerce.validation;

public interface Context<REQUEST, RESPONSE, ENTITY> {
    void validate(REQUEST request);
    ENTITY create(REQUEST request);
    RESPONSE toResponse(ENTITY entity);
}
