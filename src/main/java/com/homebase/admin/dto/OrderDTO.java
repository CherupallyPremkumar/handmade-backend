package com.homebase.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String id;
    private String customerName;
    private String customerEmail;
    private List<OrderItemDTO> items;
    private BigDecimal total;
    private String status;
    private String createdAt;
    private String updatedAt;
}
