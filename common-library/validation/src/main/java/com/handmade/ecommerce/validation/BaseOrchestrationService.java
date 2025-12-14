package com.handmade.ecommerce.validation;

public class BaseOrchestrationService<REQUEST, RESPONSE, ENTITY> {

    private final Context<REQUEST, RESPONSE, ENTITY> context;

    public BaseOrchestrationService(Context<REQUEST, RESPONSE, ENTITY> context) {
        this.context = context;
    }

    public RESPONSE execute(REQUEST request) {
        // 1. Validate
        context.validate(request);

        // 2. Create
        ENTITY entity = context.create(request);

        // 3. Response
        return context.toResponse(entity);
    }
}
