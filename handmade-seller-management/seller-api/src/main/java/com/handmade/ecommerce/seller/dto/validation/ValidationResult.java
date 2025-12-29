package com.handmade.ecommerce.seller.dto.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Result of validation containing errors and warnings.
 * Supports merging multiple validation results.
 */
public class ValidationResult {

    private List<ValidationError> errors = new ArrayList<>();
    private List<ValidationWarning> warnings = new ArrayList<>();

    public boolean isValid() {
        return errors.isEmpty();
    }

    public void addError(String field, String message) {
        errors.add(new ValidationError(field, message));
    }

    public void addWarning(String field, String message) {
        warnings.add(new ValidationWarning(field, message));
    }

    public void merge(ValidationResult other) {
        this.errors.addAll(other.getErrors());
        this.warnings.addAll(other.getWarnings());
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public List<ValidationWarning> getWarnings() {
        return warnings;
    }

    public static class ValidationError {
        private String field;
        private String message;

        public ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class ValidationWarning {
        private String field;
        private String message;

        public ValidationWarning(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}
