package com.homebase.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private String id;
    private String name;
    private String email;
    private String phone;
    private Integer orderCount;
    private BigDecimal totalSpent;
    private String createdAt;
}
