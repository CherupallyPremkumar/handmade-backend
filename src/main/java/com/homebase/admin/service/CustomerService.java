package com.homebase.admin.service;

import com.homebase.admin.dto.CustomerDTO;
import com.homebase.admin.entity.Customer;
import com.homebase.admin.entity.TenantContext;
import com.homebase.admin.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<CustomerDTO> getAllCustomers() {
        String tenantId = TenantContext.getCurrentTenant();
        return customerRepository.findByTenantId(tenantId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(String.valueOf(customer.getId()));
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setOrderCount(customer.getOrderCount());
        dto.setTotalSpent(customer.getTotalSpent());
        dto.setCreatedAt(customer.getCreatedAt() != null ? customer.getCreatedAt().toString() : null);
        return dto;
    }
}
