package com.taifex.smartorder.controller;


import com.taifex.smartorder.dto.UserDTO;
import com.taifex.smartorder.entity.User;
import com.taifex.smartorder.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // GET /api/users - 取得所有使用者
    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers().stream().map(user -> toDTO(user)).toList();
    }

    // GET /api/users/{id} - 根據 ID 取得使用者
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        Optional<User> user = userService.getUserById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(toDTO(user.get()));
        }
        return ResponseEntity.notFound().build();
    }

    // POST /api/users - 建立新使用者
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        if (userService.isEmailExists(userDTO.getEmail())){
            return ResponseEntity.badRequest().build();
        }
        User savedUser = userService.saveUser(userDTO);
        return ResponseEntity.ok(toDTO(savedUser));
    }

    // PUT /api/users/{id} - 更新使用者
    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        Optional<User> user = userService.getUserById(id);
        if(user.isPresent()){
            User updatesUser = userService.updateUser(id,userDTO);
            return ResponseEntity.ok(toDTO(updatesUser));
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


    // --- DTO 轉換 ---
    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());
        return dto;
    }
}
