package com.taifex.smartorder.controller;

import com.taifex.smartorder.dto.OrderDTO;
import com.taifex.smartorder.dto.UserDTO;
import com.taifex.smartorder.service.OrderService;
import com.taifex.smartorder.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService){
        this.userService = userService;
        this.orderService = orderService;
    }

    // GET /api/users - 取得所有使用者
    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    // GET /api/users/{id} - 根據 ID 取得使用者
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        Optional<UserDTO> user = userService.getUserById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    // POST /api/users - 建立新使用者
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        if (userService.isEmailExists(userDTO.getEmail())){
            return ResponseEntity.badRequest().build();
        }
        UserDTO savedUser = userService.saveUser(userDTO);
        return ResponseEntity.ok(savedUser);
    }

    // PUT /api/users/{id} - 更新使用者
    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        Optional<UserDTO> user = userService.getUserById(id);
        if(user.isPresent()){
            Optional<UserDTO> updatesUser = userService.updateUser(id,userDTO);
            return ResponseEntity.ok(updatesUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/users/{id} - 刪除使用者
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        if (userService.getUserById(id).isPresent()){
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET  /api/users/{userId}/orders - 查詢使用者所有訂單
    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable Long userId){
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
}