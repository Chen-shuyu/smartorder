package com.taifex.smartorder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taifex.smartorder.dto.OrderDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // 測試 POST /api/orders
    @Test
    void testCreateOrder() throws Exception {
        OrderDTO dto = new OrderDTO();
        dto.setProductName("Test Product");
        dto.setAmount(2);
        dto.setPrice(5000.0);
        dto.setUserId(1L);

        mockMvc.perform(post("/api/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Test Product"))
                .andExpect(jsonPath("$.amount").value(2));
    }

    // 發送 GET /api/orders/1
    @Test
    void testGetOrderById() throws Exception {
        mockMvc.perform(get("/api/orders/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("iPhone 16"));
    }

    // 發送 PUT /api/orders/1 更新資料
    @Test
    void testUpdateOrder() throws Exception {

        // 先建立一筆資料For測試用
        OrderDTO testDto = new OrderDTO();
        testDto.setProductName("Original Product");
        testDto.setAmount(1);
        testDto.setPrice(5000.0);
        testDto.setUserId(1L);

        String response = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();


        OrderDTO createdOrder = objectMapper.readValue(response, OrderDTO.class);

        // 準備更新的資料
        createdOrder.setProductName("update Product");
        createdOrder.setAmount(3);
        createdOrder.setPrice(9999.0);

        mockMvc.perform(put("/api/orders/{id}", createdOrder.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createdOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("update Product"))
                .andExpect(jsonPath("$.amount").value(3));
    }

    // 發送 DELETE /api/orders/2
    @Test
    void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/api/orders/2"))
                .andExpect(status().isNoContent());
    }
}
