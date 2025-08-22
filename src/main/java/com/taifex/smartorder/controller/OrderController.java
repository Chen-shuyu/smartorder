package com.taifex.smartorder.controller;

import com.taifex.smartorder.dto.OrderDTO;
import com.taifex.smartorder.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    // POST /api/orders - 建立新訂單
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO){
        if(orderService.getOrderById(id).isPresent()){
            return ResponseEntity.badRequest().build();
        }
        OrderDTO createdDTO = orderService.saveOrder(orderDTO);
        return ResponseEntity.ok(createdDTO);
    }

    // GET /api/orders/{id} - 根據 ID 取得訂單
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id){
        Optional<OrderDTO> orderDTO = orderService.getOrderById(id);
        if(orderDTO.isPresent()){
            return ResponseEntity.ok(orderDTO.get());
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/orders - 取得所有訂單
    @GetMapping
    public List<OrderDTO> getAllOrders(){
        return orderService.getAllOrders().stream().toList();
    }

    // PUT /api/orders/{id} - 更新訂單
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO){
        if(orderService.getOrderById(id).isPresent()){
            Optional<OrderDTO> updated = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(updated.get());
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/orders/{id} - 刪除訂單
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        if(orderService.getOrderById(id).isPresent()){
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}