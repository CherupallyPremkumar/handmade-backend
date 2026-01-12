package com.handmade.ecommerce.seller.onboarding.dto;

import org.chenile.workflow.param.MinimalPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Payload for document submission during onboarding.
 */
@Data
public class SubmitDocsPayload  extends MinimalPayload{
    private List<DocumentInfo> documents;

    @Data
    public static class DocumentInfo {
        private String documentType;
        private String documentUrl;
        private String referenceId;
    }
}
