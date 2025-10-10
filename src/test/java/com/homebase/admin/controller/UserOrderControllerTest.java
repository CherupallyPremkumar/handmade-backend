package com.homebase.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homebase.admin.controller.user.UserOrderController;
import com.homebase.admin.dto.CreateOrderRequest;
import com.homebase.admin.dto.OrderDTO;
import com.homebase.admin.entity.Order;
import com.homebase.admin.service.UserOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test cases for UserOrderController
 * Tests order creation, retrieval, and cancellation
 */
@WebMvcTest(UserOrderController.class)
@WithMockUser
class UserOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserOrderService userOrderService;

    private OrderDTO orderDTO;
    private CreateOrderRequest createOrderRequest;

    @BeforeEach
    void setUp() {
        // Setup test order DTO
        orderDTO = new OrderDTO();
        orderDTO.setId("1");
        orderDTO.setOrderNumber("ORD20240115103000");
        orderDTO.setCustomerId(1L);
        orderDTO.setCustomerName("John Doe");
        orderDTO.setCustomerEmail("john@example.com");
        orderDTO.setCustomerPhone("+919876543210");
        orderDTO.setShippingAddress("123 Main St, Mumbai, Maharashtra, 400001");
        orderDTO.setSubtotal(new BigDecimal("6998.00"));
        orderDTO.setTax(new BigDecimal("1259.64"));
        orderDTO.setShippingCost(new BigDecimal("150.00"));
        orderDTO.setTotal(new BigDecimal("8407.64"));
        orderDTO.setStatus("PENDING");
        orderDTO.setPaymentStatus("PENDING");
        orderDTO.setItems(new ArrayList<>());

        // Setup create order request
        createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setCustomerId(1L);
        createOrderRequest.setCustomerName("John Doe");
        createOrderRequest.setCustomerEmail("john@example.com");
        createOrderRequest.setCustomerPhone("+919876543210");
        createOrderRequest.setShippingAddress("123 Main St");
        createOrderRequest.setCity("Mumbai");
        createOrderRequest.setState("Maharashtra");
        createOrderRequest.setPincode("400001");
        createOrderRequest.setPaymentMethod("PHONEPE");
    }

    @Test
    void testCreateOrder_Success() throws Exception {
        // Given
        when(userOrderService.createOrderFromCart(any(CreateOrderRequest.class), anyString()))
                .thenReturn(orderDTO);

        // When & Then
        mockMvc.perform(post("/api/user/order")
                .with(csrf())
                .param("tenantId", "default")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value("ORD20240115103000"))
                .andExpect(jsonPath("$.customerName").value("John Doe"))
                .andExpect(jsonPath("$.total").value(8407.64))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.paymentStatus").value("PENDING"));
    }

    @Test
    void testCreateOrder_EmptyCart() throws Exception {
        // Given
        when(userOrderService.createOrderFromCart(any(CreateOrderRequest.class), anyString()))
                .thenThrow(new IllegalArgumentException("Cart is empty"));

        // When & Then
        mockMvc.perform(post("/api/user/order")
                .with(csrf())
                .param("tenantId", "default")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrderRequest)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void testCreateOrder_InsufficientStock() throws Exception {
        // Given
        when(userOrderService.createOrderFromCart(any(CreateOrderRequest.class), anyString()))
                .thenThrow(new IllegalStateException("Insufficient stock for product"));

        // When & Then
        mockMvc.perform(post("/api/user/order")
                .with(csrf())
                .param("tenantId", "default")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrderRequest)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void testGetCustomerOrders_Success() throws Exception {
        // Given
        List<OrderDTO> orders = Arrays.asList(orderDTO);
        when(userOrderService.getCustomerOrders(anyLong(), anyString()))
                .thenReturn(orders);

        // When & Then
        mockMvc.perform(get("/api/user/order")
                .param("customerId", "1")
                .param("tenantId", "default"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].orderNumber").value("ORD20240115103000"));
    }

    @Test
    void testGetOrderByNumber_Success() throws Exception {
        // Given
        when(userOrderService.getOrderById(anyString(), anyString()))
                .thenReturn(orderDTO);

        // When & Then
        mockMvc.perform(get("/api/user/order/ORD20240115103000")
                .param("tenantId", "default"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value("ORD20240115103000"))
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    void testGetOrderByNumber_NotFound() throws Exception {
        // Given
        when(userOrderService.getOrderById(anyString(), anyString()))
                .thenThrow(new IllegalArgumentException("Order not found"));

        // When & Then
        mockMvc.perform(get("/api/user/order/INVALID")
                .param("tenantId", "default"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void testCancelOrder_Success() throws Exception {
        // Given
        doNothing().when(userOrderService).cancelOrder(anyString(), anyString());

        // When & Then
        mockMvc.perform(post("/api/user/order/ORD20240115103000/cancel")
                .with(csrf())
                .param("tenantId", "default"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCancelOrder_AlreadyShipped() throws Exception {
        // Given
        when(userOrderService).cancelOrder(anyString(), anyString());
        doThrow(new IllegalStateException("Cannot cancel shipped order"))
                .when(userOrderService).cancelOrder(anyString(), anyString());

        // When & Then
        mockMvc.perform(post("/api/user/order/ORD20240115103000/cancel")
                .with(csrf())
                .param("tenantId", "default"))
                .andExpect(status().is5xxServerError());
    }
}
