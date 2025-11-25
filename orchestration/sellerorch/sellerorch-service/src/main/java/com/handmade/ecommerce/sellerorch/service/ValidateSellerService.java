package com.handmade.ecommerce.sellerorch.service;

import com.handmade.ecommerce.command.CreateSellerCommand;
import org.springframework.stereotype.Service;

@Service
public class ValidateSellerService extends BaseSellerOrchService {

    @Override
    protected void doProcess(SellerContext sellerContext) {
        CreateSellerCommand command = sellerContext.getRequestSeller();
        
        // Validate required fields
        validateRequiredFields(command);
        
        // Validate business rules
        validateBusinessRules(command);
        
        // Validate data formats
        validateDataFormats(command);
        
        // Check for duplicates
        checkDuplicates(command);
        
        System.out.println("Seller validation completed successfully");
        super.doProcess(sellerContext);
    }
    
    private void validateRequiredFields(CreateSellerCommand command) {
        if (command.getSellerName() == null || command.getSellerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Seller name is required");
        }
        
        if (command.getEmail() == null || command.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        
        if (command.getBusinessName() == null || command.getBusinessName().trim().isEmpty()) {
            throw new IllegalArgumentException("Business name is required");
        }
        
        if (command.getTaxId() == null || command.getTaxId().trim().isEmpty()) {
            throw new IllegalArgumentException("Tax ID is required");
        }
    }
    
    private void validateBusinessRules(CreateSellerCommand command) {
        // Validate seller name length
        if (command.getSellerName().length() < 3) {
            throw new IllegalArgumentException("Seller name must be at least 3 characters long");
        }
        
        if (command.getSellerName().length() > 100) {
            throw new IllegalArgumentException("Seller name must not exceed 100 characters");
        }
        
        // Validate business name
        if (command.getBusinessName().length() < 3) {
            throw new IllegalArgumentException("Business name must be at least 3 characters long");
        }
    }
    
    private void validateDataFormats(CreateSellerCommand command) {
        // Validate email format
        if (!isValidEmail(command.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        // Validate tax ID format (basic validation)
        if (!isValidTaxId(command.getTaxId())) {
            throw new IllegalArgumentException("Invalid tax ID format");
        }
        
        // Validate bank account number if provided
        if (command.getBankAccountNumber() != null && !command.getBankAccountNumber().trim().isEmpty()) {
            if (!isValidBankAccount(command.getBankAccountNumber())) {
                throw new IllegalArgumentException("Invalid bank account number format");
            }
        }
    }
    
    private void checkDuplicates(CreateSellerCommand command) {
        // TODO: Check if seller with same email already exists
        // if (sellerService.existsByEmail(command.getEmail())) {
        //     throw new IllegalArgumentException("Seller with this email already exists");
        // }
        
        // TODO: Check if seller with same tax ID already exists
        // if (sellerService.existsByTaxId(command.getTaxId())) {
        //     throw new IllegalArgumentException("Seller with this tax ID already exists");
        // }
    }
    
    private boolean isValidEmail(String email) {
        // Basic email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
    
    private boolean isValidTaxId(String taxId) {
        // Basic tax ID validation - adjust based on your requirements
        // This is a simple check for alphanumeric characters
        return taxId.matches("^[A-Z0-9]{5,20}$");
    }
    
    private boolean isValidBankAccount(String bankAccount) {
        // Basic bank account validation - adjust based on your requirements
        return bankAccount.matches("^[0-9]{8,20}$");
    }
}
